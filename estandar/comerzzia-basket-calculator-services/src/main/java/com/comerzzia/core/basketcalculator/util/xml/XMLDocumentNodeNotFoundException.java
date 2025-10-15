package com.comerzzia.core.basketcalculator.util.xml;

public class XMLDocumentNodeNotFoundException extends XMLDocumentException {
    

	private static final long serialVersionUID = 4615396995965018731L;


	public XMLDocumentNodeNotFoundException() {
    }
        
    public XMLDocumentNodeNotFoundException(String msg) {
        super(msg);
    }

    public XMLDocumentNodeNotFoundException(String msg,Throwable e) {
        super(msg,e);
    }

}