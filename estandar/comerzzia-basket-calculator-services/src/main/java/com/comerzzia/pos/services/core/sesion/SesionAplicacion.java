package com.comerzzia.pos.services.core.sesion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.codBarrasEspeciales.CodigoBarrasEspecialBean;
import com.comerzzia.pos.persistence.core.empresas.EmpresaBean;
import com.comerzzia.pos.persistence.core.tiendas.cajas.TiendaCajaBean;
import com.comerzzia.pos.persistence.core.variables.VariableBean;
import com.comerzzia.pos.persistence.paises.PaisBean;
import com.comerzzia.pos.services.cajas.conceptos.CajaConceptosServices;
import com.comerzzia.pos.services.clientes.ClientesService;
import com.comerzzia.pos.services.codBarrasEsp.CodBarrasEspecialesService;
import com.comerzzia.pos.services.core.documentos.Documentos;
import com.comerzzia.pos.services.core.empresas.EmpresasService;
import com.comerzzia.pos.services.core.paises.PaisService;
import com.comerzzia.pos.services.core.tiendas.Tienda;
import com.comerzzia.pos.services.core.tiendas.TiendasService;
import com.comerzzia.pos.services.core.tiendas.cajas.TiendaCajaService;
import com.comerzzia.pos.services.core.tiendas.cajas.TiendaCajaServiceException;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.mediospagos.MediosPagosService;
import com.comerzzia.pos.util.config.AppConfig;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SesionAplicacion {
    protected final Logger log = Logger.getLogger(getClass());
    
    protected String codAlm;
    protected String codCaja;
    protected Tienda tienda;
    protected boolean desglose1Activo;
    protected boolean desglose2Activo;
    protected EmpresaBean empresa;
    protected TiendaCajaBean tiendaCaja;
    protected Map<String, VariableBean> variablesCache = new HashMap<>();
    
    @Autowired
    protected MediosPagosService mediosPagosService;
    @Autowired
    protected VariablesServices variablesServices;
    @Autowired
    protected ClientesService clientesService;
    @Autowired
    protected CodBarrasEspecialesService codBarrasEspecialesService;
    @Autowired
    protected TiendasService tiendasService;
    @Autowired
    protected CajaConceptosServices cajaConceptosServices;
    @Autowired
    protected EmpresasService empresasService;
    
    @Autowired
    protected PaisService paisService;
            
    @Autowired
    protected TiendaCajaService tiendaCajaService;
    
    //Documentos  de la aplicación
    @Autowired
    protected Documentos documentos;
    
    @Autowired
	StoreTillSessionCache storeTillSessionCache;    
        
    protected List<CodigoBarrasEspecialBean> specialBarcodes = new ArrayList<>();
    
    protected String storeLanguageCode;
    
    protected StoreTillSessionKey storeTillSessionKey;

    protected SesionImpuestos sesionImpuestos;
            
    public void init(StoreTillSessionKey storeTillSessionKey) throws SesionInitException {
       this.storeTillSessionKey = storeTillSessionKey;
              
        try{
        	this.sesionImpuestos = storeTillSessionCache.getTaxes(storeTillSessionKey);
        	
            log.info("init() - UID_ACTIVIDAD: " + storeTillSessionKey.getUidActividad() + 
            		         " STORE: " + storeTillSessionKey.getStoreId() +
            		         " TILL: " + storeTillSessionKey.getTillId());

            // Consultamos caja
            TiendaCajaBean tiendaCaja = tiendaCajaService.consultarCaja(storeTillSessionKey.getUidActividad(), storeTillSessionKey.getStoreId(), storeTillSessionKey.getTillId());
            
            this.codAlm = tiendaCaja.getCodAlmacen();
            this.codCaja = tiendaCaja.getCodcaja();
            this.tiendaCaja = tiendaCaja;

            // Consultamos tienda
            Tienda tienda = tiendasService.consultarTienda(storeTillSessionKey.getUidActividad(), this.codAlm);
            ClienteBean clienteTienda = clientesService.consultarCliente(storeTillSessionKey.getUidActividad(), tienda.getAlmacenBean().getCodCliente());
            
            // Actualizamos cliente de la tienda con su grupo de impuestos
            clienteTienda.setIdGrupoImpuestos(sesionImpuestos.getGrupoImpuestos().getIdGrupoImpuestos());
            
            tienda.setCliente(clienteTienda);
            this.tienda = tienda;
            
            // Consultamos empresa
            EmpresaBean empresa = empresasService.consultarEmpresa(storeTillSessionKey.getUidActividad(), tienda.getAlmacenBean().getCodEmpresa());
            this.empresa = empresa;

            // Consultamos los documentos para el país determinado e uid_instancia 
            documentos.inicializar(storeTillSessionKey.getUidActividad(), tienda.getCliente().getCodpais());

            //codBarrasEspecialesServices.cargarCodigosBarrasEspeciales(sesion, specialBarcodes);
            updateSpecialBarcodes();
            
            // Cargamos variables del sistema
            updateVariablesCache();
            
            // Inicializamos variables de desgloses
            desglose1Activo = true;
            desglose2Activo = true;
            String desglose1 = getVariableAsString(VariablesServices.ARTICULO_DESGLOSE1_TITULO);
            String desglose2 = getVariableAsString(VariablesServices.ARTICULO_DESGLOSE2_TITULO);
            if (desglose1 == null || desglose1.isEmpty()){
                desglose1Activo = false;
            }
            if (desglose2 == null || desglose2.isEmpty()){
                desglose2Activo = false;
            }
        }
        catch (Exception ex) {
            log.error("init() - " + ex.getMessage());
            throw new SesionInitException(ex.getMessage(), ex);
        }
    }
    
    public void updateVariablesCache() {
    	variablesCache = variablesServices.consultarVariables(storeTillSessionKey.getUidActividad());
    }
        
    public void updateSpecialBarcodes() {
    	specialBarcodes.clear();
    	specialBarcodes.addAll(codBarrasEspecialesService.obtenerCodigosBarras(storeTillSessionKey.getUidActividad()));
    }
    
    public String getVariableValue(String variableId) {
    	VariableBean variable = variablesCache.get(variableId);
    	if (variable == null) {
    		throw new RuntimeException("Variable not found: " + variableId);
    	}
    	
    	return variable.getValor();    	
    }
    // Esta funcion se utiliza para obtener el valor directamente de la cache.
    // Es necesario porque el Bean de Sesion aún se está creando, luego los 
    // servicios que dependen de el no lo tienen disponible.
    protected String getVariableAsString(String idVariable) {       
       String valor = getVariableValue(idVariable);
       return valor == null ? "" : valor;
     }
 
    public void actualizarUidPos(String newUidPos) throws TiendaCajaServiceException{
    	tiendaCajaService.actualizarUidPOS(tiendaCaja, newUidPos);
    	tiendaCaja.setUidTpv(newUidPos);
    }
    
    public String getCodCaja() {
        return codCaja;
    }
    
    public String getTerminalId() {
    	return tiendaCaja.getUidCaja();
    }

    public Tienda getTienda() {
        return tienda;
    }

    public EmpresaBean getEmpresa() {
        return empresa;
    }
    
    public String getCodAlmacen(){
        return codAlm;
    }

    public boolean isDesglose1Activo() {
        return desglose1Activo;
    }

    public boolean isDesglose2Activo() {
        return desglose2Activo;
    }
    
    public boolean isDesglosesActivos() {
        return desglose1Activo || desglose2Activo;
    }
    
    public void setTiendaCaja(TiendaCajaBean tiendaCaja){
        this.tiendaCaja = tiendaCaja;
    }
    
    public TiendaCajaBean getTiendaCaja(){
        return tiendaCaja;
    }
    
    public Documentos getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Documentos documentos) {
        this.documentos = documentos;
    }
	
	public String getStoreLanguageCode() {
		if(storeLanguageCode == null) {
			storeLanguageCode = getTienda().getCliente().getCodlengua();
	        if(StringUtils.isBlank(storeLanguageCode)) {
	        	String countryCode = getTienda().getCliente().getCodpais();
	        	try {
					PaisBean country = paisService.consultarCodPais(countryCode);
					storeLanguageCode = country.getCodLengua();
					if(StringUtils.isBlank(storeLanguageCode)) {
						storeLanguageCode = AppConfig.idioma;
					}
				} catch (Exception e) {
					 log.error("getStoreLanguageCode() - Error while query store country: " + e.getMessage(), e);
					 storeLanguageCode = AppConfig.idioma;
				}
	        }
		}
        return storeLanguageCode;
	}

	public List<CodigoBarrasEspecialBean> getSpecialBarcodes() {
		return specialBarcodes;
	}
	
	public String getUidActividad() {
		return storeTillSessionKey.getUidActividad();
	}
 
	
}