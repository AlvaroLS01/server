package com.comerzzia.omnichannel.service.documentprint.raw;

import java.util.Map;


public interface RawPrinter {
    public static final int SIZE_0 = 0;
    public static final int SIZE_1 = 1;
    public static final int SIZE_2 = 2;
    public static final int SIZE_3 = 3;    
    public static final int SIZE_CONDENSED = 4;
    
    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_UNDERLINE = 2; 
    
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_CENTER = 2;
    
    public static final String BARCODE_EAN13 = "EAN13";
    public static final String BARCODE_CODE128 = "CODE128";
    
    public static final String POSITION_BOTTOM = "bottom";
    public static final String POSITION_NONE = "none";
    
    public static String ALIGN_LEFT_TEXT = "left";
    public static String ALIGN_CENTER_TEXT = "center";
    public static String ALIGN_RIGHT_TEXT = "right";

    public void inicializar();

    public void cerrar();
    public void empezarLinea(int size, int lineCols);
    public void terminarLinea();

    public void empezarDocumento(Map<String,Object> parametros);

    public boolean terminarDocumento();

    public void imprimirTexto(String texto,Integer size, String align, Integer style, String fontName, int fontSize);    
   
    public void imprimirCodigoBarras(String codigoBarras, String tipo, String alineacion, int tipoAlineacionTexto, int height);
    
    public void abrirCajon();
    
    public void cortarPapel();
    
    public void imprimirLogo();
    
    public void comandoEntradaPlantilla(String comandoEntradaTexto, int leftMargin);

    public void comandoSalidaPlantilla(String comandoSalidaTexto);

    public void comandoEntradaCodigoBarras(String comandoEntradaCodBar);

    public void comandoSalidaCodBar(String comandoSalidaTexto);

    public void comandoEntradaLinea(String comandoEntradaLinea);

    public void comandoEntradaTexto(String comandoEntradaTexto);

    public void comandoSalidaTexto(String comandoSalidaTexto);

    public void comandoSalidaLinea(String comandoSalidaTexto);
}
