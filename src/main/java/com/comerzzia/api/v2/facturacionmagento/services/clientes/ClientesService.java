package com.comerzzia.api.v2.facturacionmagento.services.clientes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.clientes.ClientesMapper;
import com.comerzzia.omnichannel.model.documents.sales.ticket.cabecera.ClienteBean;

@Component
public class ClientesService {

	protected static final Logger log = Logger.getLogger(ClientesService.class);

	@Autowired
	protected ClientesMapper clientesMapper;

	public ClienteBean consultarPorCif(String uidActividad, String cif) {
		log.debug("consultarPorCif() - Consultando cliente con documento: " + cif);

		ClienteBean cliente = clientesMapper.selectByCif(uidActividad, cif);

		if (cliente == null) {
			String msg = "Error consultando cliente con Documento = " + cif;
			log.error("consultarPorCif() - " + msg);
			return null;
		}

		return cliente;
	}

	public ClienteBean consultarPorCod(String uidActividad, String codCli) throws Exception {
		log.debug("consultarPorCod() - Consultando cliente con código: " + codCli);

		ClienteBean cliente = clientesMapper.selectByCod(uidActividad, codCli);

		if (cliente == null) {
			String msg = "Error consultando cliente con codCli = " + codCli;
			log.error("consultarPorCod() - " + msg);
			throw new Exception(msg);
		}

		return cliente;
	}

	public int consultarGrupoImpuestos(String uidActividad) throws Exception {
		log.debug("consultarGrupoImpuestos() - Consultando grupo de impuestos activo");
		
		Integer grupoImpuestos = clientesMapper.selectTaxGroup(uidActividad);
		
		if(grupoImpuestos == null) {
			String msg = "Error consultando grupo de impuestos activo";
			log.error("consultarGrupoImpuestos() - " + msg);
			throw new Exception(msg);
		}

		return grupoImpuestos.intValue();
	}

}
