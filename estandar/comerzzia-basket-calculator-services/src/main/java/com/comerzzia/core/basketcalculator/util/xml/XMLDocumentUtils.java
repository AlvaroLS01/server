/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.core.basketcalculator.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * Funciones para trabajar con documentos XML
 */
public final  class XMLDocumentUtils extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6038925587015641537L;

	protected static InheritableThreadLocal builderThreadLocal = new InheritableThreadLocal() {
		@Override
		protected Object initialValue() {
			try {
				return createDocumentBuilder();
			} catch (XMLDocumentException e) {
				throw new RuntimeException(e);
			}
		}
	};

	private XMLDocumentUtils() {
    }
    
    
    /**
     * Crea un nuevo documento xml vacio
     * @return 
     */
    public static Document createDocument() throws XMLDocumentException {
        return getDocumentBuilder().newDocument();
    }

	private static DocumentBuilder getDocumentBuilder() throws XMLDocumentException {
		try {
			return (DocumentBuilder) builderThreadLocal.get();
		}
		catch (Exception e) {
			String msg = "Error al crear DocumentBuilder: " + e.getMessage();
			throw new XMLDocumentException(msg, e);
		}
	}

    /**
     * Obtiene un DocumentBuilder
     * @return 
     */
    public static DocumentBuilder createDocumentBuilder() throws XMLDocumentException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					throw exception;
					
				}
				
				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
					
				}
				
				@Override
				public void error(SAXParseException exception) throws SAXException {
					throw exception;
					
				}
			});
      
            return builder;
        } 
        catch (Exception e) {
            throw new XMLDocumentException("Error al crear documento XML: " + e.getMessage());
        }
    }
    
    
    /**
     * Crea un nuevo documento xml a partir del contenido de una url
     * @param url
     * @return 
     */
    public static Document createDocumentFormURL(URL url) throws XMLDocumentException {
        Document document = null;
        
        try {
            InputSource is = new InputSource(url.openStream());
            document = getDocumentBuilder().parse(is);
        }
        catch (IOException e) {
            throw new XMLDocumentException("Error al crear documento XML: " + e.getMessage());
        }
        catch (SAXException e) {
            throw new XMLDocumentException("Error al crear documento XML: " + e.getMessage());
        }
        
        return document;
    }
    
    /**
     * Crea un nuevo documento xml a partir de un string
     * @param string
     * @return 
     */
    public static Document createDocumentFormString(String string) throws XMLDocumentException {
        Document document = null;
        
        try {
        	InputStream is = new ByteArrayInputStream(string.getBytes("UTF-8"));
            document = getDocumentBuilder().parse(is);
        }
        catch (IOException e) {
            throw new XMLDocumentException("Error al crear documento XML: " + e.getMessage());
        }
        catch (SAXException e) {
            throw new XMLDocumentException("Error al crear documento XML: " + e.getMessage());
        }
        
        return document;
    }
    

    /**
     * Obtiene una cadena a partir de un Document; además esta cadena ya está identada.
     * @return String
     * @throws XMLDocumentException
     */
    public static String DocumentToString(Document document) throws XMLDocumentException {
		return DocumentToString(document, null);
    }
    
    /**
     * Obtiene una cadena a partir de un Document; además esta cadena ya está identada.
     * 
     * @param document
     * @param encoding
     * @return
     * @throws XMLDocumentException
     */
    public static String DocumentToString(Document document, String encoding) throws XMLDocumentException {
    	return DocumentToString(document, encoding, false);
    }
    
    public static String DocumentToString(Document document, String encoding, boolean indentar) throws XMLDocumentException {
    	try{
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			Result result = new StreamResult(output);
			DOMSource source = new DOMSource(document);
			TransformerFactory tFactory = TransformerFactory.newInstance();
//			tFactory.setAttribute("indent-number", 2);
			Transformer transformer = tFactory.newTransformer();
			if (encoding != null) {
				transformer.setOutputProperty("encoding", encoding);
			}
			if (indentar){
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			}
			transformer.transform(source, result);
			if (encoding != null){
				return new String(output.toByteArray(), encoding);
			}
			return new String(output.toByteArray());
		}
		catch(Exception e){
			String msg = "Error al aplicar transformación a XML para volcado a fichero: " + e.getMessage();
			throw new XMLDocumentException(msg);
		}
    }
    
    /**
     * Devuelve un nuevo elemento
     * 
     * @param document
     * @param namespaceURI
     * @param name
     * @param value
     * @return
     */
    public static Element createElement(Document document, String namespaceURI, String name, String value) {
    	Element element = null;
    	
    	if(namespaceURI != null && !namespaceURI.isEmpty()) {
    		element = (Element) document.createElementNS(namespaceURI, name);
    	}
    	else{
    		element = (Element) document.createElement(name);
    	}
    	element.appendChild(document.createTextNode(value != null ? value : ""));
        return element;
    }
    
    public static Element createElement(Document document, String name, String value) {
    	return createElement(document, null, name, value);
    }
    
    public static Element createElement(Document document, String namespaceURI, String name, long value) {
        return createElement(document, namespaceURI, name, Long.toString(value));
    }
    
    public static Element createElement(Document document, String name, long value) {
        return createElement(document, null, name, Long.toString(value));
    }
    
    public static Element createElement(Document document, String namespaceURI, String name, float value) {
        return createElement(document, namespaceURI, name, Float.toString(value));
    }
    
    public static Element createElement(Document document, String name, float value) {
        return createElement(document, null, name, Float.toString(value));
    }
    
    public static Element createElement(Document document, String namespaceURI, String name, Element child) {
    	Element element = null;
    	
    	if(namespaceURI != null && !namespaceURI.isEmpty()) {
    		element = (Element) document.createElementNS(namespaceURI, name);
    	}
    	else{
    		element = (Element) document.createElement(name);
    	}
        element.appendChild(child);
        return element;
    }
    
    public static Element createElement(Document document, String name, Element child) {
        return createElement(document, null, name, child);
    }
    
    /**
     * Añade un nuevo nodo al nodo root
     * 
     * @param document
     * @param namespaceURI
     * @param root
     * @param name
     * @param value
     */
    public static void appendChild(Document document, String namespaceURI, Node root, String name, String value) {
    	Node node = null;
    	if(namespaceURI != null && !namespaceURI.isEmpty()) {
    		node = document.createElementNS(namespaceURI, name);
    	}
    	else{
    		node = document.createElement(name);
    	}
        node.appendChild(document.createTextNode(value != null ? value : ""));
        root.appendChild(node);
        return;
    }
    
    /**
     * Añade un nuevo nodo al nodo root
     * 
     * @param document
     * @param root
     * @param name
     * @param value
     */
    public static void appendChild(Document document, Node root, String name, String value) {
    	appendChild(document, null, root, name, value);
        return;
    }
    
    public static void appendChild(Document document, Node root, String name, float value) {
        appendChild(document, null, root, name, Float.toString(value));
        return;
    }
    
    public static void appendChild(Document document, String namespaceURI, Node root, String name, float value) {
        appendChild(document, namespaceURI, root, name, Float.toString(value));
        return;
    }
    
    public static void appendChild(Document document, Node root, String name, long value) {
        appendChild(document, root, name, Long.toString(value));
        return;
    }
    
    public static void appendChild(Document document, String namespaceURI, Node root, String name, long value) {
        appendChild(document, namespaceURI, root, name, Long.toString(value));
        return;
    }
    
    
    public static void appendChild(Node root, String name, String value) {
        appendChild(root.getOwnerDocument(), null, root, name, value);
        return;
    }
    
    public static void appendChild(Node root, String namespaceURI, String name, String value) {
        appendChild(root.getOwnerDocument(), namespaceURI, root, name, value);
        return;
    }
    
    
    public static void appendChild(Document document, Node root, String name, double value) {
        appendChild(document, root, name, Double.toString(value));
        return;
    }
    
    public static void appendChild(Document document, String namespaceURI, Node root, String name, double value) {
        appendChild(document, namespaceURI, root, name, Double.toString(value));
        return;
    }
    
    /**
     * Obtiene en valor de un tag del xml
     * @param element Elemento xml
     * @param tagName Nombre del tag
     * @param optional Indica si el tag os opcional o no en el elemento
     * @return String con el valor del primer tag con nombre tagName en root
     *  o cadena vacia si no se encuentra y no era opcional
     */
    public static String getTagValue(Element element, String tagName, boolean optional) 
            throws XMLDocumentException {
        String returnString = "";
        
        NodeList nodos = element.getElementsByTagName(tagName);
        for (int i = 0; i < nodos.getLength(); i++) {
            Node node = nodos.item(i);
            if (node != null) {
                Node child = node.getFirstChild();
                if ((child != null) && child.getNodeValue() != null) {
                    return child.getNodeValue();
                }
            }
        }
        
        if (!optional) {
            throw new XMLDocumentException("El elemento " + element.getTagName() + 
                " no contiene el tag " + tagName);
        }
        
        return returnString;
    }
    
    public static String getTagValueAsString(Element element, String tagName, boolean optional) 
            throws XMLDocumentException {
        return getTagValue(element, tagName, optional);
    }
    
    public static int getTagValueAsInt(Element element, String tagName, boolean optional)
            throws XMLDocumentException {
        
        String entero = getTagValue(element, tagName, optional);
        if (entero.length() == 0) {
            return 0;
        }
        
        return Integer.parseInt(getTagValue(element, tagName, optional));
    }
    
    public static Long getTagValueAsLong(Element element, String tagName, boolean optional)
		    throws XMLDocumentException {
		
		String entero = getTagValue(element, tagName, optional);
		if (entero.length() == 0) {
		    return 0L;
		}
		
		return Long.parseLong(getTagValue(element, tagName, optional));
    }

    public static Double getTagValueAsDouble(Element element, String tagName, boolean optional)
		    throws XMLDocumentException {
		
		String entero = getTagValue(element, tagName, optional);
		if (entero.length() == 0) {
		    return 0.0;
		}
		
		return Double.parseDouble(getTagValue(element, tagName, optional));
	}

    public static boolean getTagValueAsBoolean(Element element, String tagName, boolean optional)
            throws XMLDocumentException {
        
        return Boolean.valueOf(getTagValue(element, tagName, optional)).booleanValue();
    }
    
    
    
    /**
     * Devuelve un nodo del elemento
     * @param element Elemento
     * @param name Nombre del nodo
     * @param optional 
     * @return Node o null si no se encuentra y era opcional
     */
    public static Node getNode(Element element, String name, boolean optional)
            throws XMLDocumentException {
        Node node = null;
        
        for (node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (((Element) node).getTagName().equals(name)) {
                    return node;
                }
            }
        }
        
        if (!optional) {
            throw new XMLDocumentException("El elemento " + element.getTagName() + 
                                           " no contiene el nodo " + name);
        }
        return node;
    }
    
    
    /**
     * Devuelve una lista que contiene todos los nodos de un elemento
     * @param element Elemento
     * @return lista de nodos
     */
    public static List<Element> getChildElements(Element element)throws XMLDocumentException {
        List<Element> lstNodos = new ArrayList<Element>();
    	Node node = null;
        for (node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	lstNodos.add((Element)node);
            }
        }
        
        return lstNodos;
    }

    
    public static Element getElement(Element element, String name, boolean optional)
            throws XMLDocumentException {
        return (Element) getNode(element, name, optional);
    }
    
    
    /**
     * Obtiene el atributo de un elemento
     * @param element
     * @param name
     * @param optional
     * @return 
     */
    public static String getAttribute(Element element, String name, boolean optional)
            throws XMLDocumentException {
        String value = element.getAttribute(name);
        
        if (value == null || value.length() == 0) {
            if (!optional) {
                throw new XMLDocumentException("El elemento " + element.getTagName() + 
                                               " no contiene el atributo " + name);
            }
            else {
                value = "";
            }
        }
        
        return value;
    }
    
    
    
    /**
     * Serializa un documento xml
     * @param document
     * @param out
     */
    public static void serialize(Document document, PrintWriter out) throws XMLDocumentException {
        try {
            DOMSource source = new DOMSource(document);
            StreamResult sr = new StreamResult(out);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.transform(source, sr);
        }
        catch (Exception e) {
            throw new XMLDocumentException("Error al serializar documento xml: " + e.getMessage());
        }
    }
    
    
    /**
     * Serializa un documento xml con encoding ISO-8859-1
     * @param document
     * @param out
     */
    public static void serializeISO(Document document, PrintWriter out) throws XMLDocumentException {
        try {
            DOMSource source = new DOMSource(document);
            StreamResult sr = new StreamResult(out);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.transform(source, sr);
        }
        catch (Exception e) {
            throw new XMLDocumentException("Error al serializar documento xml: " + e.getMessage());
        }
    }
}
