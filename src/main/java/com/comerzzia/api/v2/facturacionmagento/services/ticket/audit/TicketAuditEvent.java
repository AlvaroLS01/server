package com.comerzzia.api.v2.facturacionmagento.services.ticket.audit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.util.i18n.I18N;

/**
 * 
 * @author jbn@tier1.es
 *
 */
@Component
@Scope("prototype")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "evento")
@XmlType(propOrder = { "uidActividad", "uidTicketVenta", "codAlmacen", "moment", "type", "resolution","successful",
		"idUsuario", "desUsuario",  "idUsuarioSupervisor", "desUsuarioSupervisor", "codArticulo",
		"desArticulo", "cantidad", "precioSinDtoOriginal", "precioSinDtoAplicado" })
public class TicketAuditEvent {

	public enum Type {
		CAMBIO_PRECIO, ANULACION_TICKET, ANULACION_LINEA, DEVOLUCION, GESTO_COMERCIAL, MODIFICACION_CLIENTE
	}
	
	public static Map<Type,String> Types = new HashMap<>();
	static {
		Types.put(Type.CAMBIO_PRECIO, I18N.getTexto("cambio de precio"));
		Types.put(Type.ANULACION_TICKET, I18N.getTexto("anulación ticket"));
		Types.put(Type.ANULACION_LINEA, I18N.getTexto("anulación linea"));
		Types.put(Type.DEVOLUCION, I18N.getTexto("devolución"));
		Types.put(Type.GESTO_COMERCIAL, I18N.getTexto("gesto comercial"));
		Types.put(Type.MODIFICACION_CLIENTE, I18N.getTexto("modificacion cliente"));
	}

	@XmlElement(name = "tipo")
	protected Type type;
	// actividad
	@XmlElement(name = "uid_actividad")
	private String uidActividad;
	// ticket stuff
	@XmlElement(name = "uid_ticket_venta")
	private String uidTicketVenta;

	// user stuff
	@XmlElement(name = "id_usuario")
	private Long idUsuario;

	@XmlElement(name = "des_usuario")
	private String desUsuario;
	// supervisor stuff
	@XmlElement(name = "id_supervisor")
	protected Long idUsuarioSupervisor;

	@XmlElement(name = "des_supervisor")
	protected String desUsuarioSupervisor;
	// alamcen
	@XmlElement(name = "cod_almacen")
	private String codAlmacen;
	// fecha
	@XmlElement(name = "fecha")
	protected Date moment;
	// (OPTIONAL) ticket line stuff
	@XmlElement(name = "cod_articulo")
	protected String codArticulo;
	@XmlElement(name = "des_articulo")
	protected String desArticulo;
	@XmlElement(name = "can_articulo")
	protected BigDecimal cantidad;
	@XmlElement(name = "precio_articulo_original")
	protected BigDecimal precioSinDtoOriginal;
	@XmlElement(name = "precio_articulo_aplicado")
	protected BigDecimal precioSinDtoAplicado;
	@XmlElement(name = "procede")
	protected Boolean successful;

	@XmlElement(name = "des_evento")
	protected String resolution;

	@SuppressWarnings("unused")
	private TicketAuditEvent(Type type) {
		super();
		this.type = type;
		this.moment = new Date();
	}

	public TicketAuditEvent() {
		super();
	}

	
	public static Map<Type, String> getTypes() {
		return Types;
	}

	
	public static void setTypes(Map<Type, String> types) {
		Types = types;
	}

	
	public Type getType() {
		return type;
	}

	
	public void setType(Type type) {
		this.type = type;
	}

	
	public String getUidActividad() {
		return uidActividad;
	}

	
	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}

	
	public String getUidTicketVenta() {
		return uidTicketVenta;
	}

	
	public void setUidTicketVenta(String uidTicketVenta) {
		this.uidTicketVenta = uidTicketVenta;
	}

	
	public Long getIdUsuario() {
		return idUsuario;
	}

	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public String getDesUsuario() {
		return desUsuario;
	}

	
	public void setDesUsuario(String desUsuario) {
		this.desUsuario = desUsuario;
	}

	
	public Long getIdUsuarioSupervisor() {
		return idUsuarioSupervisor;
	}

	
	public void setIdUsuarioSupervisor(Long idUsuarioSupervisor) {
		this.idUsuarioSupervisor = idUsuarioSupervisor;
	}

	
	public String getDesUsuarioSupervisor() {
		return desUsuarioSupervisor;
	}

	
	public void setDesUsuarioSupervisor(String desUsuarioSupervisor) {
		this.desUsuarioSupervisor = desUsuarioSupervisor;
	}

	
	public String getCodAlmacen() {
		return codAlmacen;
	}

	
	public void setCodAlmacen(String codAlmacen) {
		this.codAlmacen = codAlmacen;
	}

	
	public Date getMoment() {
		return moment;
	}

	
	public void setMoment(Date moment) {
		this.moment = moment;
	}

	
	public String getCodArticulo() {
		return codArticulo;
	}

	
	public void setCodArticulo(String codArticulo) {
		this.codArticulo = codArticulo;
	}

	
	public String getDesArticulo() {
		return desArticulo;
	}

	
	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
	}

	
	public BigDecimal getCantidad() {
		return cantidad;
	}

	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	
	public BigDecimal getPrecioSinDtoOriginal() {
		return precioSinDtoOriginal;
	}

	
	public void setPrecioSinDtoOriginal(BigDecimal precioSinDtoOriginal) {
		this.precioSinDtoOriginal = precioSinDtoOriginal;
	}

	
	public BigDecimal getPrecioSinDtoAplicado() {
		return precioSinDtoAplicado;
	}

	
	public void setPrecioSinDtoAplicado(BigDecimal precioSinDtoAplicado) {
		this.precioSinDtoAplicado = precioSinDtoAplicado;
	}

	
	public Boolean getSuccessful() {
		return successful;
	}

	
	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	
	public String getResolution() {
		return resolution;
	}

	
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	

}
