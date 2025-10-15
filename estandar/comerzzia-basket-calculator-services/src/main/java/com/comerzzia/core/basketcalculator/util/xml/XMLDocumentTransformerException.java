package com.comerzzia.core.basketcalculator.util.xml;

public class XMLDocumentTransformerException extends Exception {
    
	private static final long serialVersionUID = 1L;

    public XMLDocumentTransformerException() {
    }
        
    public XMLDocumentTransformerException(String msg) {
        super(msg);
    }

    public XMLDocumentTransformerException(String msg,Throwable e) {
        super(msg,e);
    }


    public XMLDocumentTransformerException(Throwable e) {
        super(e);
    }
}