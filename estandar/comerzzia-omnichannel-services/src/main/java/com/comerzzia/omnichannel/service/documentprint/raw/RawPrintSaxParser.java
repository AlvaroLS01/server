package com.comerzzia.omnichannel.service.documentprint.raw;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.comerzzia.pos.util.i18n.I18N;

@SuppressWarnings("unused")
public class RawPrintSaxParser extends DefaultHandler {
    private static final Logger log = Logger.getLogger(RawPrintSaxParser.class.getName());

    private static SAXParser parseador = null;
    
    private RawPrinter rawPrinter;

    private int defaultLineCols = 40;
    private int currentLineCols = 40;

    private StringBuffer texto = null;

    //PLANTILLA    
	private int leftMargin;
    private String bottomMargin;

    //ATRIBUTOS DE LINEA
    private Integer lineSize; //Tamaño linea
    private String comandoEntradaLinea;
    private String comandoSalidaLinea;

    //ATRIBUTOS DE TEXTO    
    private String align; // alineación del texto. Para poder alinear el texto, deberá estar definido size para la linea
    private Integer size; // longitud del texto;    
    private Integer style; //Estilo del texto
    private String comandoEntradaTexto;
    private String comandoSalidaTexto;
    private String fontName;
    private Integer fontSize;

    //ATRIBUTOS DE CODIGODE BARRAS
    private String type; //Tipo del código de barras.
    private String comandoEntradaCodBar;
    private String comandoSalidaCodBar;
    private String alignCodBar; //alineamiento del código de barras
    private int tipoLeyendaNumericaCodBar; //posición de números en código de barras
    private int height; //altura

    private Map<String,Object> parametros;

    /**
     * Imprime un documento
     *
     * @param plantilla procesada por la aplicación
     * @throws com.comerzzia.omnichannel.service.printer.raw.dispositivo.impresora.parser.PrintParserException
     */
    public void print(String plantilla, RawPrinter rawPrinter) throws PrintParserException {
        this.rawPrinter = rawPrinter;
        
        print(new StringReader(plantilla.replace("&", "&amp;").replaceAll("(?<=[^!])[-][-](?=[^>])", "&#45;&#45;"))); //Reemplazamos & por &amp; porque el parseador necesita htmlentities y contemplamos que contenga 2 o mas guiones y los reemplazamos por su codigo ASCII.
    }
    
    /**
     * Imprime un documento
     *
     * @param in
     */
    private void print(Reader in) throws PrintParserException {
        try {
            if (parseador == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                parseador = spf.newSAXParser();
            }
            parseador.parse(new InputSource(in), this);

        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("print() - Error imprimiendo documento : "+e.getMessage(), e);
            throw new PrintParserException(e);
        }
    }

    /**
     * Procesa el inicio de los tags
     *
     * @param uri
     * @param localName
     * @param tag
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
        switch (tag) {
            case "image":
            case "imagen":
            	log.trace("startElement() - case: imagen");
                break;
            case "barcode":
            case "codbar":
            	log.trace("startElement() - case: codbar");
                texto = new StringBuffer();
                type = attributes.getValue("type");
                String attSizeCodbar = attributes.getValue("size");
                if (attSizeCodbar != null && !attSizeCodbar.isEmpty()) {
                    this.size = Integer.parseInt(attSizeCodbar);
                }
                String numberPositionStr = attributes.getValue("tipo-leyenda");
                if (numberPositionStr != null && !numberPositionStr.isEmpty()) {
                	this.tipoLeyendaNumericaCodBar = Integer.parseInt(numberPositionStr);
                }
                else {
                	this.tipoLeyendaNumericaCodBar = 0;
                }
                String height = attributes.getValue("altura");
                if (height != null && !height.isEmpty()) {
                    this.height = Integer.parseInt(height);
                }
                else {
                    this.height = 0;
                }

                alignCodBar = attributes.getValue("align");
                comandoEntradaCodBar = attributes.getValue("pre-command");
                comandoSalidaCodBar = attributes.getValue("post-command");

                rawPrinter.comandoEntradaCodigoBarras(comandoEntradaCodBar);

                break;
            case "line":                
            case "linea":
            	log.trace("startElement() - case: linea");
                String attSizeLinea = attributes.getValue("line-size");
                if (attSizeLinea != null && !attSizeLinea.isEmpty()) {
                    this.lineSize = Integer.parseInt(attSizeLinea);
                }
                else {
                    this.lineSize = 0;
                }
                currentLineCols = defaultLineCols;
                String valLineCols = attributes.getValue("line-cols");
                if(valLineCols != null && !valLineCols.isEmpty()) {
                	try {
						currentLineCols = Integer.parseInt(valLineCols);
					} catch (NumberFormatException e) {
					}
                }
                	
                comandoEntradaLinea = attributes.getValue("pre-command");
                comandoSalidaLinea = attributes.getValue("post-command");
                rawPrinter.comandoEntradaLinea(comandoEntradaLinea);
                rawPrinter.empezarLinea(lineSize, currentLineCols);
                break;
            case "text":                
            case "texto":
            	log.trace("startElement() - case: texto");
                texto = new StringBuffer();
                this.size = null;
                this.align = null;

                String attSize = attributes.getValue("size");
                if (attSize != null && !attSize.isEmpty()) {
                    this.size = Integer.parseInt(attSize);
                }
                String attAlign = attributes.getValue("align");
                if (attAlign != null && !attAlign.isEmpty()) {
                    this.align = attAlign;
                }
                String attStyle = attributes.getValue("style");
                if (attStyle != null && !attStyle.isEmpty()) {
                    this.style = Integer.parseInt(attStyle);
                }
                else {
                    style = 0;
                }

                // leemos tamaño
                String fontSizeString = attributes.getValue("fontsize");
                if (fontSizeString != null && !fontSizeString.isEmpty()) {
                    fontSize = Integer.parseInt(fontSizeString);
                }
                else {
                    fontSize = lineSize;
                }

                // leemos fuente
                fontName = attributes.getValue("fontname");

                comandoEntradaTexto = attributes.getValue("pre-command");
                comandoSalidaTexto = attributes.getValue("post-command");
                rawPrinter.comandoEntradaTexto(comandoEntradaTexto);

                break;
            case "template":
            case "plantilla":
            	log.trace("startElement() - case: plantilla");
                String leftMarginString = attributes.getValue("margin-left");
                if (leftMarginString != null && !leftMarginString.isEmpty()) {
                    leftMargin = new Integer(leftMarginString);
                }
                else {
                    leftMargin = 0;
                }
                break;
            case "document":
            case "documento":
            	log.debug("startElement() - case: documento");
            	String impresoraDocumento = attributes.getValue("salida");               
                
                Map<String,Object> datos = new HashMap<String, Object>();
                datos.putAll(parametros);

                for(int i=0; i< attributes.getLength(); i++){
                	String valor = attributes.getValue(i);
                	String nombre = attributes.getQName(i);
                	
                	if(valor!=null && !valor.isEmpty() && nombre!=null && !nombre.isEmpty()){
                		datos.put(nombre, valor);
                	}
                }
                
                // Establecer el numero de columnas por defecto si llega a nivel de documento
                if(datos.containsKey("line-cols")) {
                	defaultLineCols = Integer.parseInt(datos.get("line-cols").toString()); 
                }
                
                datos.put("line-cols", defaultLineCols + "");
                datos.put("charset", attributes.getValue("charset"));

                rawPrinter.empezarDocumento(datos);
                break;
            case "cut":
            case "corte":
            	log.trace("startElement() - case: corte");
                // Ejecutamos al cerrar etiqueta
                break;
            case "drawer-open":
            case "apertura-cajon":
            	log.trace("startElement() - case: apertura-cajon");
                // Ejecutamos al cerrar etiqueta
                break;

        }
    }

    /**
     * Procesa el final de los tags
     *
     * @param uri
     * @param localName
     * @param tag
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String tag) throws SAXException {

        switch (tag) {
            case "barcode":
            case "codbar":
            	log.trace("endElement() - case: codbar");
                rawPrinter.imprimirCodigoBarras(texto.toString(), type, alignCodBar, tipoLeyendaNumericaCodBar, height);
                rawPrinter.comandoSalidaCodBar(comandoSalidaTexto);
                texto = null;
                type = null;
                size = null;
                break;
            case "text":
            case "texto":
            	log.trace("endElement() - case: texto");
                rawPrinter.imprimirTexto(StringEscapeUtils.unescapeXml(texto.toString()), size, align, style, fontName, fontSize);
                rawPrinter.comandoSalidaTexto(comandoSalidaTexto);
                texto = null;
                size = null;
                align = null;
                break;
            case "line":
            case "linea":
            	log.trace("endElement() - case: linea");
                // Imprimimos salto de línea
                rawPrinter.terminarLinea();
                rawPrinter.comandoSalidaLinea(comandoSalidaTexto);
                break;
            case "template":
            case "plantilla":
            	log.trace("endElement() - case: plantilla");
            	//Si la impresora seleccionada es nula, es que la plantilla está vacía
            	if(rawPrinter != null) {
            		rawPrinter.comandoSalidaPlantilla(comandoSalidaTexto);
            	}                
                break;
            case "image":
            case "imagen":
            	log.trace("endElement() - case: imagen");
                break;            
            case "logo":
            	log.trace("endElement() - case: logo");
                rawPrinter.imprimirLogo();
                break;
            case "document":
            case "documento":
            	log.trace("endElement() - case: documento");
                if(!rawPrinter.terminarDocumento()) {
                	String textoError = I18N.getTexto("La impresión no se ha podido completar correctamente.") + System.lineSeparator();
                	
                	throw new SAXException(textoError);
                }
                log.debug("endDocument() - XML de documento se ha parseado por completo y enviado a la impresora.");
                break;
            case "cut":
            case "corte":
            	log.debug("endElement() - case: corte");
                rawPrinter.cortarPapel();
                break;
            case "drawer-open":
            case "apertura-cajon":
            	log.trace("endElement() - case: apertura-cajon");
                openCashDrawer();
                break;
        }
    }

    /**
     * Procesa los caracteres interiores de los tags de la plantilla
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (texto != null) {
            texto.append(ch, start, length);
        }
    }

    public void openCashDrawer() {        
    	 rawPrinter.abrirCajon();
    }

    
	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

}
