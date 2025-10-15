package com.comerzzia.core.basketcalculator.util.xml;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLDocumentNode {

	private static Logger log = Logger.getLogger(XMLDocumentNode.class);
	
	private Node nodo;
	private Document document;
	
	public XMLDocumentNode(XMLDocument document, String nombre){
		this.nodo = document.getDocument().createElement(nombre);
		this.document = document.getDocument();
	}

	public XMLDocumentNode(XMLDocument document, String nombre, String valor){
		this.nodo = document.getDocument().createElement(nombre);
		this.document = document.getDocument();
		this.nodo.setTextContent(valor);
	}

	protected XMLDocumentNode(Document document, Node nodo){
		this.nodo = nodo;
		this.document = document;
	}
	
	/** Devuelve un nuevo nodo importado desde otro nodo de un xml diferente. 
	 * @param document - XML actual donde será importado el nodo
	 * @param nodoOrigen - Nodo origen de un xml diferente
	 * @return XMLDocumentNode - Nuevo nodo
	 */
	public static XMLDocumentNode importarNodo(XMLDocument document, Node nodoOrigen){
		Node nodo = document.getDocument().importNode(nodoOrigen, true);
		return new XMLDocumentNode(document.getDocument(), nodo);
	}

	/** Añade una sección de CDATA al nodo xml.*/
	public void añadirSeccionCData(String cdata){
		CDATASection cdataSection = this.document.createCDATASection(cdata);
		this.nodo.appendChild(cdataSection);
	}

	
	/** Añade al nodo un nuevo nodo hijo. */
	public void añadirHijo(XMLDocumentNode nodo){
		this.nodo.appendChild(nodo.getNode());
	}

	/** Añade al nodo un nuevo nodo hijo con el nombre y el valor indicados. */
	public void añadirHijo(String nombre, String valor){
		Node nodo = document.createElement(nombre);
		nodo.setTextContent(valor);
		this.nodo.appendChild(nodo);
	}

	/** Añade al nodo un atributo con el nombre y el valor indicados. */
	public void añadirAtributo(String nombre, String valor){
		Attr a = document.createAttribute(nombre);
		a.setNodeValue(valor);
		nodo.getAttributes().setNamedItem(a);
	}

	/** Devuelve el objeto Node del nodo. */
	public Node getNode(){
		return this.nodo;
	}
	
	/** Busca un nodo con un nombre determinado a partir del nodo actual en todo su árbol.
	 * Si no lo encuentra lanza un XMLDocumentNodeNotFoundException. Este método es equivalente
	 * a getNodo(String nombre, boolean opcional) con opcional = false
	 * @param nombre - Indica el nombre del nodo buscado
	 * @param opcional - Indica si el nodo es opcional o no
	 * @return XMLDocumentNode - Nodo buscado. Si no se encuentra y opcional es true será null
	 * @throws XMLDocumentNodeNotFoundException - Si el nodo no se encuentra y opcional es false
	 */
	public XMLDocumentNode getNodo(String nombre) throws XMLDocumentNodeNotFoundException{
        return getNodo(nombre, false);
	}

	/** Busca un nodo con un nombre determinado a partir del nodo actual en todo su árbol.
	 * Si no lo encuentra y el parámetro opcional es falso, lanza un XMLDocumentNodeNotFoundException
	 * @param nombre - Indica el nombre del nodo buscado
	 * @param opcional - Indica si el nodo es opcional o no
	 * @return XMLDocumentNode - Nodo buscado. Si no se encuentra y opcional es true será null
	 * @throws XMLDocumentNodeNotFoundException - Si el nodo no se encuentra y opcional es false
	 */
	public XMLDocumentNode getNodo(String nombre, boolean opcional) throws XMLDocumentNodeNotFoundException{
        Node node = null;
        for (node = this.nodo.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (((Element) node).getTagName().equals(nombre)) {
                    return new XMLDocumentNode(document,node);
                }
            }
        }
        if (!opcional) {
            throw new XMLDocumentNodeNotFoundException("El nodo " + this.nodo.getNodeName() + " no contiene el nodo " + nombre);
        }
        return null;
	}

	/** Devuelve la lista de hijos de un nodo. Si no tiene ningún hijo, devolverá una lista vacía.
	 * @return List<XMLDocumentNode> - Lista de hijos del nodo
	 */
	public List<XMLDocumentNode> getHijos(){
		NodeList nodeList = nodo.getChildNodes();
		List<XMLDocumentNode> xmlNodeList = new ArrayList<XMLDocumentNode>();
		for (int i=0; i<nodeList.getLength();i++){
			Node nodo = nodeList.item(i);
			if (nodo instanceof Element){
				xmlNodeList.add(new XMLDocumentNode(document, nodo));
			}
		}
		return xmlNodeList;
	}
	
	public boolean tieneHijos(){
		NodeList nodeList = nodo.getChildNodes();
		for (int i=0; i<nodeList.getLength();i++){
			Node nodoHijo = nodeList.item(i);
			if (nodoHijo.getNodeType()  == Node.ELEMENT_NODE){
				return true;
			}
		}
		return false;
	}

	/** Devuelve la lista de hijos con el nombre indicado de un nodo. Si no tiene ningún hijo con este nombre, 
	 * devolverá una lista vacía.
	 * @return List<XMLDocumentNode> - Lista de hijos del nodo
	 */
	public List<XMLDocumentNode> getHijos(String nombre){
		NodeList nodeList = nodo.getChildNodes();
		List<XMLDocumentNode> xmlNodeList = new ArrayList<XMLDocumentNode>();
		for (int i=0; i<nodeList.getLength();i++){
			Node nodo = nodeList.item(i);
			if (nodo instanceof Element){
                if (((Element) nodo).getTagName().equals(nombre)) {
                	xmlNodeList.add(new XMLDocumentNode(document, nodo));
                }
			}
		}
		return xmlNodeList;
	}
	
	public String getAtributoValue(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        String value = ((Element)nodo).getAttribute(atributo);
        if ((value == null || value.isEmpty()) && !opcional) {
            throw new XMLDocumentNodeNotFoundException("El nodo " + this.nodo.getNodeName() + " no contiene el atributo " + atributo);
        }
        return value;
	}
	public Long getAtributoValueAsLong(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        return Long.parseLong(getAtributoValue(atributo, opcional));
	}
	public Integer getAtributoValueAsInteger(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        return Integer.parseInt(getAtributoValue(atributo, opcional));
	}
	public Double getAtributoValueAsDouble(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        return Double.parseDouble(getAtributoValue(atributo, opcional));
	}
	public BigDecimal getAtributoValueAsBigDecimal(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        return new BigDecimal(getAtributoValueAsDouble(atributo, opcional));
	}
	public Date getdAtributoValueAsDouble(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        String fecha = getAtributoValue(atributo, opcional);
        return getFecha(fecha);
	}

	private Date getFecha(String fecha) {
		if (fecha != null && fecha.length()> 0){
	        try {
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	            df.setLenient(false);
	            return df.parse(fecha);
	        }
	        catch (ParseException e) {
	        }
        }
        return null;
	}
	public boolean getAtributoValueAsBoolean(String atributo, boolean opcional) throws XMLDocumentNodeNotFoundException{
        return Boolean.valueOf(getAtributoValue(atributo, opcional));
	}
	
	/** Devuelve el nombre del nodo.
	 * @return String
	 */
	public String getNombre(){
		return this.nodo.getNodeName();
	}
	
	/** Devuelve el valor del nodo como cadena.
	 * @return String
	 */
	public String getValue(){
		return this.nodo.getTextContent();
	}

	/** Devuelve el valor del nodo como Long.
	 * @return Long
	 */
	public Long getValueAsLong(){
		return Long.parseLong(this.nodo.getTextContent());
	}
	
	/** Devuelve el valor del nodo como Integer.
	 * @return Integer
	 */
	public Integer getValueAsInteger(){
		return Integer.parseInt(this.nodo.getTextContent());
	}
	
	/** Devuelve el valor del nodo como Double.
	 * @return Double
	 */
	public Double getValueAsDouble(){
		return Double.parseDouble(this.nodo.getTextContent());
	}

	/** Devuelve el valor del nodo como BigDecimal.
	 * @return Double
	 */
	public BigDecimal getValueAsBigDecimal(){
		return new BigDecimal(getValueAsDouble());
	}

	/** Devuelve el valor del nodo como Date o null.
	 * @return Double
	 */
	public Date getValueAsFecha(){
		return getFecha(getValue());
	}
	
	/** Devuelve el valor del nodo como Boolean.
	 * @return Boolean
	 */
	public boolean getValueAsBoolean(){
		return Boolean.valueOf(this.nodo.getTextContent());
	}
	
	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value String
	 */
	public void setValue(String value){
		if (value != null){
			this.nodo.setTextContent(value);
		}
	}

	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value Long
	 */
	public void setValue(Long value){
		if (value != null){
			this.nodo.setTextContent(value.toString());
		}
	}

	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value Integer
	 */
	public void setValue(Integer value){
		if (value != null){
			this.nodo.setTextContent(value.toString());
		}
	}

	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value Double
	 */
	public void setValue(Double value){
		if (value != null){
			this.nodo.setTextContent(value.toString());
		}
	}

	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value BigDecimal
	 */
	public void setValue(BigDecimal value){
		if (value != null){
			this.nodo.setTextContent(value.toString());
		}
	}

	/** Cambia el valor del nodo estableciendo el indicado por parámetro
	 * @param value Boolean
	 */
	public void setValue(Boolean value){
		if (value != null){
			this.nodo.setTextContent(value.toString());
		}
	}
	
	/** Metodo toString sobrecargado de clase Object. Si se produce algún error en la serialización, 
	 * devuelve la cadena ## ERROR ## */
	public String toString() {
		try {
			StringWriter sw = new StringWriter();
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource(nodo), new StreamResult(sw));
			return sw.toString();
		}
		catch(TransformerException e) {
			log.error("toString() - " + e.getMessage());
			return "## ERROR ##";
		}
	}

	/** Elimina del nodo el atributo con el nombre indicado. */
	public void eliminarAtributo(String nombre){
		nodo.getAttributes().removeNamedItem(nombre);
	}
}
