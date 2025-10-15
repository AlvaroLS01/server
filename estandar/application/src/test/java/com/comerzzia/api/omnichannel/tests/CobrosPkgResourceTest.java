package com.comerzzia.api.omnichannel.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.comerzzia.api.omnichannel.OmnichannelApplication;
import com.comerzzia.api.omnichannel.web.model.basket.BasketCalculateRequest;
import com.comerzzia.api.omnichannel.web.model.basket.BasketItemRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OmnichannelApplication.class})
@AutoConfigureMockMvc
public class CobrosPkgResourceTest {

    private final Logger log = LoggerFactory.getLogger(CobrosPkgResourceTest.class);

    @Inject
    private OAuth2TokenMockUtil tokenUtil;

    @Autowired
    private MockMvc restMockMvc;

    @Inject
    private WebApplicationContext context;

    @PostConstruct
    public void setup() {
//    	log.info("Contexto: " + context.getServletContext().getContextPath());
//        this.restMockMvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();
    }

    @Test
    @Transactional
    public void createAltaAvisoCobro() throws Exception {
    	BasketCalculateRequest basketCalculateRequest = new BasketCalculateRequest();
    	
    	BasketItemRequest itemRequest = new BasketItemRequest();
    	itemRequest.setUpc("1");
    	
    	List<BasketItemRequest> items = new ArrayList<>();
    	items.add(itemRequest);
		basketCalculateRequest.setItems(items);
    			
    	ResultActions resultActions = restMockMvc.perform(post("/basket/0001/00/calculate")
              .with(tokenUtil.createAuthentication("administrador", Sets.newSet("some-scope"), Sets.newSet("ROLE_USER")))
              .contentType(TestUtil.APPLICATION_JSON_UTF8)
              .content(TestUtil.convertObjectToJsonBytes(basketCalculateRequest)))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    			
    	
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = sdf.parse("14/12/2017");
//        BigDecimal bigDecimal = new BigDecimal(0.0);
//
//        CobrosPkgAltaAvisoCobroInDTO altaAvisoCobro = new CobrosPkgAltaAvisoCobroInDTO();
//        altaAvisoCobro.setAnticipo("");
//        altaAvisoCobro.setAsiento(bigDecimal);
//        altaAvisoCobro.setBancoRemesa("");
//        altaAvisoCobro.setCodcli("43000000006");
//        altaAvisoCobro.setCodcueCobro("");
//        altaAvisoCobro.setCoddivisa("EUR");
//        altaAvisoCobro.setCodemp("0100");
//        altaAvisoCobro.setCodriesgo("NA");
//        altaAvisoCobro.setCodserie("00");
//        altaAvisoCobro.setCodtipoefec("01");
//        altaAvisoCobro.setCtaGastosGestion("");
//        altaAvisoCobro.setDescontable("N");
//        altaAvisoCobro.setDocumento("");
//        altaAvisoCobro.setEstado(bigDecimal);
//        altaAvisoCobro.setFechaAceptacion(date);
//        altaAvisoCobro.setFechaContabilizacion(date);
//        altaAvisoCobro.setFechaFactura(date);
//        altaAvisoCobro.setFechaVencimiento(date);
//        altaAvisoCobro.setGastosGestion(bigDecimal);
//        altaAvisoCobro.setImporte(bigDecimal);
//        altaAvisoCobro.setInformacionAdicional("");
//        altaAvisoCobro.setNumeroEfecto(bigDecimal);
//        altaAvisoCobro.setNumproy("");
//        altaAvisoCobro.setObservaciones("");
//        altaAvisoCobro.setRenovacion("N");
//        altaAvisoCobro.setSegundaVuelta("N");
//
//        // Create the AltaAvisoCobro
//        ResultActions resultActions = restMockMvc.perform(post("/api/cobrosPkg/altaAvisoCobro")
//            .with(tokenUtil.oauth2Authentication("mockuser@example.com", Sets.newSet("some-scope"), Sets.newSet("ROLE_USER")))
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(altaAvisoCobro)))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
//
//        String result = resultActions.andReturn().getResponse().getContentAsString();
//
//        log.info("Created: " + result);
    }
}
