package com.comerzzia.pos.util.config;

import static com.comerzzia.pos.util.config.AppConfig.getOptValue;

import org.apache.log4j.Logger;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;

public class KeyCodesInfo {
	
	private static Logger log = Logger.getLogger(KeyCodesInfo.class);
	
	protected String keyCodeTablaAccion;
	protected String keyCodeTablaNegar;
	protected String keyCodeTablaEliminar;
	protected String keyCodeTablaAccionGeneral;
	protected String keyCodeTablaNegarGeneral;
	protected String keyCodeTablaEliminarGeneral;
	protected String keyCodeTablaPrimeroGeneral;
	protected String keyCodeTablaSiguienteGeneral;
	protected String keyCodeTablaAnteriorGeneral;
	protected String keyCodeTablaUltimoGeneral;
	
	/**Parsea el arbol de <KeyCodes>
	 * */
	public static KeyCodesInfo parse(XMLDocumentNode nodeRoot) {
		KeyCodesInfo keyCodesInfo = new KeyCodesInfo();
    	
    	try {
			XMLDocumentNode nodeKeyCodes = nodeRoot.getNodo("KeyCodes", true);
			if (nodeKeyCodes != null) {
				XMLDocumentNode nodeKeyCodesTabla = nodeKeyCodes.getNodo("Tablas", true);
				if (nodeKeyCodesTabla != null) {
					keyCodesInfo.keyCodeTablaAccion = getOptValue(nodeKeyCodesTabla, "Accion");
					keyCodesInfo.keyCodeTablaNegar = getOptValue(nodeKeyCodesTabla, "Negar");             
					keyCodesInfo.keyCodeTablaEliminar = getOptValue(nodeKeyCodesTabla, "Eliminar");          
					keyCodesInfo.keyCodeTablaAccionGeneral = getOptValue(nodeKeyCodesTabla, "AccionGeneral");     
					keyCodesInfo.keyCodeTablaNegarGeneral = getOptValue(nodeKeyCodesTabla, "NegarGeneral");      
					keyCodesInfo.keyCodeTablaEliminarGeneral = getOptValue(nodeKeyCodesTabla, "EliminarGeneral");   
					keyCodesInfo.keyCodeTablaPrimeroGeneral = getOptValue(nodeKeyCodesTabla, "PrimeroGeneral");    
					keyCodesInfo.keyCodeTablaSiguienteGeneral = getOptValue(nodeKeyCodesTabla, "SiguienteGeneral");  
					keyCodesInfo.keyCodeTablaAnteriorGeneral = getOptValue(nodeKeyCodesTabla, "AnteriorGeneral");   
					keyCodesInfo.keyCodeTablaUltimoGeneral = getOptValue(nodeKeyCodesTabla, "UltimoGeneral");     
				}
			}
		} catch (Exception e) {
			log.error("parse() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
		}
		return keyCodesInfo;
	}

	public String getKeyCodeTablaAccion() {
		return keyCodeTablaAccion;
	}

	public String getKeyCodeTablaNegar() {
		return keyCodeTablaNegar;
	}

	public String getKeyCodeTablaEliminar() {
		return keyCodeTablaEliminar;
	}

	public String getKeyCodeTablaAccionGeneral() {
		return keyCodeTablaAccionGeneral;
	}

	public String getKeyCodeTablaNegarGeneral() {
		return keyCodeTablaNegarGeneral;
	}

	public String getKeyCodeTablaEliminarGeneral() {
		return keyCodeTablaEliminarGeneral;
	}

	public String getKeyCodeTablaPrimeroGeneral() {
		return keyCodeTablaPrimeroGeneral;
	}

	public String getKeyCodeTablaSiguienteGeneral() {
		return keyCodeTablaSiguienteGeneral;
	}

	public String getKeyCodeTablaAnteriorGeneral() {
		return keyCodeTablaAnteriorGeneral;
	}

	public String getKeyCodeTablaUltimoGeneral() {
		return keyCodeTablaUltimoGeneral;
	}
	
}
