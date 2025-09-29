package com.comerzzia.bricodepot.api.omnichannel.api.web.salesdocument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.SalesDocumentPrintService;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintRequest;
import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.SalesDocumentPrintResult;

@RestController
public class SalesDocumentPrintController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDocumentPrintController.class);

    private final SalesDocumentPrintService service;
    private final ObjectMapper objectMapper;

    public SalesDocumentPrintController(SalesDocumentPrintService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/salesdocument/{documentUid}/print")
    public ResponseEntity<SalesDocumentPrintResponse> print(@PathVariable("documentUid") String documentUid,
            @RequestParam(name = "copy", required = false, defaultValue = "false") boolean copy,
            @RequestParam(name = "printTemplate", required = false) String printTemplate,
            @RequestParam(name = "outputDocumentName", required = false) String outputDocumentName,
            @RequestParam MultiValueMap<String, String> queryParams) {

        Map<String, Object> customParams = extractCustomParams(queryParams);
        SalesDocumentPrintRequest request = SalesDocumentPrintRequest.builder()
                .copy(copy)
                .templateOverride(printTemplate)
                .outputDocumentName(outputDocumentName)
                .customParams(customParams)
                .build();

        try {
            Optional<SalesDocumentPrintResult> result = service.print(documentUid, request);
            if (!result.isPresent()) {
                return ResponseEntity.ok().body(null);
            }

            SalesDocumentPrintResult value = result.get();
            SalesDocumentPrintResponse response = new SalesDocumentPrintResponse(value.getFileName(),
                    value.getMimeType(), value.getPdfBase64());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(value.getFileName()).build());

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error generating invoice for {}", documentUid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Map<String, Object> extractCustomParams(MultiValueMap<String, String> queryParams) {
        Map<String, Object> customParams = new HashMap<>();

        queryParams.forEach((key, values) -> {
            if (key.startsWith("customParams[")) {
                String innerKey = key.substring("customParams[".length(), key.length() - 1);
                if (!values.isEmpty()) {
                    customParams.put(innerKey, values.get(values.size() - 1));
                }
            } else if ("customParams".equals(key) && !values.isEmpty()) {
                String json = values.get(values.size() - 1);
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> parsed = objectMapper.readValue(json, Map.class);
                    customParams.putAll(parsed);
                } catch (JsonProcessingException e) {
                    LOGGER.warn("Unable to parse customParams JSON", e);
                }
            }
        });

        customParams.remove("copy");
        customParams.remove("printTemplate");
        customParams.remove("outputDocumentName");
        return customParams;
    }
}

