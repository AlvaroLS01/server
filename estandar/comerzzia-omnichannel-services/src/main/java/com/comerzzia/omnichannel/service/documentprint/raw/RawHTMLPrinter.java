package com.comerzzia.omnichannel.service.documentprint.raw;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.core.io.ClassPathResource;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class RawHTMLPrinter implements RawPrinter {    
    private static final Logger log = Logger.getLogger(RawHTMLPrinter.class.getName());
    
    private String resultadoImpresion;
    
    private Map<String,Object> parametros;


    @Override
    public void inicializar() {
        resultadoImpresion = "<html>\n";
        resultadoImpresion += "<meta charset=\"UTF-8\"/>\n";
        resultadoImpresion += "<style>\n";
        resultadoImpresion += "body {\r\n" + 
        		"    font: normal 16px Lucida Console, Courier, monospace;\r\n" +
        		"    line-height: 8px;\r\n" +       
        		"    width: auto;\r\n" +        		
        		"}\n";
        resultadoImpresion += "p.doubleHeight {\r\n" + 
        		"    -webkit-transform: scaleY(2);\r\n" + 
        		"    -moz-transform: scaleY(2);\r\n" + 
        		"}\n";
        
        
        resultadoImpresion += ".center {\r\n" + 
		        "  display: block;\r\n" + 
		        "  margin-left: auto;\r\n" + 
		        "  margin-right: auto;\r\n" + 
		        "  max-width: auto;\r\n" + 
		        "}\n";
        resultadoImpresion += "</style>\n";
        resultadoImpresion += "<body>\n";
    }

    @Override
    public void empezarLinea(int size, int lineCols) {
        log.trace("empezarLinea()" );
        
        if (size == 0) {
           resultadoImpresion += "<p>\n";
        } else if (size == 1) {
        	resultadoImpresion += "<p class=\"doubleHeight\">\n";        	
        }
    }

    @Override
    public boolean terminarDocumento() {
    	if(parametros != null) {
    		if(parametros.get("procesador")!=null && parametros.get("procesador").toString().toUpperCase().equals("JASPER")){
        		log.debug("Visualizando DocumentoJasper");
        		//TODO
       			//ImpresionJasper.visualizarFacturaJasper(parametros.get("fichero").toString(), parametros);
        	}
        	else{
    	        log.trace("terminarDocumento()" );
    	        resultadoImpresion +="</body></html>\n";
        	}
    	}
    	// Se resetea los parámetros para contemplar los casos en los que se accede desde el cajón o directamente desde la impresión
    	parametros = null;
    	return true;
    }

    @Override
    public void imprimirTexto(String texto ,Integer size, String align, Integer style, String fontName, int fontSize) {
        log.trace("imprimirTexto()" );
        // Si esta definido size y align debemos de tratar la cadena de salida
        String textoProcesado =null;
        String estilo="";
        
    	if(size != null && align != null) {
    		switch (align) {
                case ALIGN_CENTER_TEXT: {
                	textoProcesado = StringUtils.center(texto, size);
                	break;
                }
                case ALIGN_RIGHT_TEXT: {
                	textoProcesado = StringUtils.leftPad(texto, size);
                	break;
                }
                default:
                	textoProcesado = StringUtils.rightPad(texto, size);
                	break;
    		}
    	}else{
    		//Alineación normal por comando UPOS
    		if (align != null && !align.isEmpty()) {
    			switch (align) {
        			case ALIGN_CENTER_TEXT: {
    		            estilo=" text-align: center;";
        				break;
        			}
        			case ALIGN_RIGHT_TEXT: {
    		            estilo=" text-align: right;";
        				break;
        			}
        			default:
        				break;
    			}
    		}
    	}
    	       
       	if (fontSize == SIZE_CONDENSED) {
        	estilo+=" font-size:11px;";
        }
        
        if (textoProcesado == null){ // En caso de que no venga el parametro size o la longitud del texto sea igual a size.
            textoProcesado = texto;
        }
        if (textoProcesado.trim().isEmpty()) {
           textoProcesado = "&nbsp;";	
        } else {
        	textoProcesado = textoProcesado.replace(" ", "&nbsp;");
        }
        
        if (!estilo.isEmpty()) {
           estilo = " style=\"" + estilo + "\"";
        }
        
        if ((style & RawPrinter.STYLE_BOLD) != 0) {
     	   textoProcesado = "<b>" + textoProcesado + "</b>";
        }
        
        resultadoImpresion += "<span" + estilo + ">" + textoProcesado + "</span>";
    }
    
    @Override
    public void terminarLinea() {
    	resultadoImpresion += "</p>\n";
    }     

    @Override
    public void empezarDocumento(Map<String,Object> datos) {
    	parametros = datos;
    }

    public String getPrevisualizacion() {
        return resultadoImpresion;
    }
    
    @Override
    public void cerrar() {
    }

    @Override
    public void imprimirCodigoBarras(String codigoBarras, String tipo, String alineacion, int tipoLeyendaNumericaCodBar, int height) {
    	final int dpi = 200;
    	
    	if ("QR".equals(tipo)) {
    		 QRCodeWriter barcodeWriter = new QRCodeWriter();
    		    try {
					BitMatrix bitMatrix = 
					  barcodeWriter.encode(codigoBarras, BarcodeFormat.QR_CODE, 200, 200);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
					
					String imgString = Base64.encodeBase64String(out.toByteArray());
		    	    
		    	    resultadoImpresion += "<img\r\n" + 
		    	    		"draggable=\"false\"\r\n" +
		    	    		"src=\"data:image/png;base64," + imgString+ "\"\r\n" + 
		    	    		"alt=\"" + codigoBarras + "\"\r\n" + 
		    	    		"class=\"center\"\r\n/>";	
		    	    
				} catch (Exception e) {
					e.printStackTrace();
				}
    	} else if ("13".equals(tipo)) {
    		EAN13Bean eanBean = new EAN13Bean();
    		
	    	try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	    	    //Set up the canvas provider for monochrome PNG output 
	    	    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
	    	            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	
	    	    //Generate the barcode
	    	    eanBean.generateBarcode(canvas, codigoBarras);
	
	    	    //Signal end of generation
	    	    canvas.finish();
	    	    
	    	    String imgString = Base64.encodeBase64String(out.toByteArray());
	    	    
	    	    resultadoImpresion += "<img\r\n" + 
	    	    		"draggable=\"false\"\r\n" +
	    	    		"src=\"data:image/x-png;base64," + imgString+ "\"\r\n" + 
	    	    		"alt=\"" + codigoBarras + "\"\r\n" + 
	    	    		"class=\"center\"\r\n/>";
	    	    
	    	} catch (IOException e) {
				e.printStackTrace();
	    	}    		
    	} else {    		
	    	Code128Bean eanBean = new Code128Bean();
	    	eanBean.setCodeset(Code128Constants.CODESET_B);
	    	
	    	try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	    	    //Set up the canvas provider for monochrome PNG output 
	    	    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
	    	            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	
	    	    //Generate the barcode
	    	    eanBean.generateBarcode(canvas, codigoBarras);
	
	    	    //Signal end of generation
	    	    canvas.finish();
	    	    
	    	    String imgString = Base64.encodeBase64String(out.toByteArray());
	    	    
	    	    resultadoImpresion += "<img\r\n" + 
	    	    		"draggable=\"false\"\r\n" +
	    	    		"src=\"data:image/png;base64," + imgString+ "\"\r\n" + 
	    	    		"alt=\"" + codigoBarras + "\"\r\n" + 
	    	    		"class=\"center\"\r\n/>";
	    	    
	    	} catch (IOException e) {
				e.printStackTrace();
	    	}
    	}
    }

    @Override
    public void abrirCajon() {
    }

    @Override
    public void imprimirLogo() {
    	// find logo.png in working directory
    	File file = new File("logo.png");
    	
    	// logo.png not in working directory
    	if (!file.exists()) {
    		// find logo.png in classpath
    		ClassPathResource resource = new ClassPathResource("/plantillas/logo.png");
    		
    		if (!resource.exists()) {
    			// no logo
    			return;
    		}
    		
    		try {
				file = resource.getFile();
			} catch (IOException e) {
			}
    	}
    	
    	try (FileInputStream input = new FileInputStream(file)) {
			byte fileContent[] = new byte[(int)file.length()];
            
            // Reads up to certain bytes of data from this input stream into an array of bytes.
			input.read(fileContent);
			
    	    String imgString = Base64.encodeBase64String(fileContent);
    	    
    	    resultadoImpresion += "<img\r\n" + 
    	    		"draggable=\"false\"\r\n" +
    	    		"src=\"data:image/png;base64," + imgString+ "\"\r\n" + 
    	    		"class=\"center\"\r\n/>";
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void comandoEntradaPlantilla(String comandoEntradaTexto, int leftMargin) {
       
    }

    @Override
    public void comandoSalidaPlantilla(String comandoSalidaTexto) {
       
    }

    @Override
    public void comandoEntradaCodigoBarras(String comandoEntradaCodBar) {
        
    }

    @Override
    public void comandoSalidaCodBar(String comandoSalidaTexto) {
        
    }

    @Override
    public void comandoEntradaLinea(String comandoEntradaLinea) {
        
    }

    @Override
    public void comandoEntradaTexto(String comandoEntradaTexto) {
        
    }

    @Override
    public void comandoSalidaTexto(String comandoSalidaTexto) {
        
    }

    @Override
    public void comandoSalidaLinea(String comandoSalidaTexto) {
        
    }

    @Override
    public void cortarPapel() {
        resultadoImpresion +="<hr/>\n";
    }


}
