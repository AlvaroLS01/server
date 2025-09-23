package com.comerzzia.api.v2.facturacionmagento.persistence.cajas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaAcumuladoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.lineas.CajaLineaRecuentoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.CajaMovimientoBean;
import com.comerzzia.api.v2.facturacionmagento.persistence.cajas.movimientos.Totales;
import com.comerzzia.api.v2.facturacionmagento.services.mediospagos.MediosPagosService;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.pos.util.format.FormatUtil;

@XmlRootElement(name = "caja")
@XmlAccessorType(XmlAccessType.NONE)
public class Caja {

	protected static final Logger log = Logger.getLogger(Caja.class);
	@XmlElement(name = "cabecera")
	protected final CajaBean cajaBean;
	@XmlElement(name = "totales")
	protected Totales totales;
	protected Integer numTicketsEntrada;
	protected Integer numTicketsSalida;
	protected List<CajaMovimientoBean> movimientosVenta;
	protected List<CajaMovimientoBean> movimientosApuntes;
	@XmlElementWrapper(name = "recuentos")
	@XmlElement(name = "recuento")
	protected List<CajaLineaRecuentoBean> lineasRecuento;
	protected final Map<String, CajaLineaAcumuladoBean> acumulados;
	
	public Caja() {
		this(new CajaBean());
	}

	public Caja(CajaBean caja) {
		cajaBean = caja;
		acumulados = new HashMap<>();
		totales = new Totales();
		numTicketsEntrada = 0;
		numTicketsSalida = 0;
		movimientosVenta = new LinkedList<>();
		movimientosApuntes = new LinkedList<>();
	}

	public void recalcularTotales(DatosSesionBean datosSesion) throws Exception {
		acumulados.clear();
		for (CajaMovimientoBean movimiento : movimientosVenta) {
			CajaLineaAcumuladoBean acumulado = acumulados.get(movimiento.getCodMedioPago());
			if (acumulado == null) {
				acumulado = new CajaLineaAcumuladoBean(MediosPagosService.get().getMedioPago(datosSesion, movimiento.getCodMedioPago()));
				acumulados.put(movimiento.getCodMedioPago(), acumulado);
			}
			acumulado.addMovimiento(movimiento);
		}
		for (CajaMovimientoBean movimiento : movimientosApuntes) {
			CajaLineaAcumuladoBean acumulado = acumulados.get(movimiento.getCodMedioPago());
			if (acumulado == null) {
				acumulado = new CajaLineaAcumuladoBean(MediosPagosService.get().getMedioPago(datosSesion, movimiento.getCodMedioPago()));
				acumulados.put(movimiento.getCodMedioPago(), acumulado);
			}
			acumulado.addMovimiento(movimiento);
		}
	}

	public void recalcularTotalesRecuento(DatosSesionBean datosSesion) throws Exception {
		totales.setTotalRecuento(BigDecimal.ZERO);

		for (CajaLineaAcumuladoBean cajaLinea : acumulados.values()) {
			cajaLinea.limpiarRecuento();
		}
		for (CajaLineaRecuentoBean linea : lineasRecuento) {
			totales.setTotalRecuento(totales.getTotalRecuento().add(linea.getTotal()));
			CajaLineaAcumuladoBean acumulado = acumulados.get(linea.getCodMedioPago());
			if (acumulado == null) {
				acumulado = new CajaLineaAcumuladoBean(MediosPagosService.get().getMedioPago(datosSesion, linea.getCodMedioPago()));
				acumulados.put(linea.getCodMedioPago(), acumulado);
			}
			acumulado.addLineaRecuento(linea);
		}
	}

	public List<CajaMovimientoBean> getMovimientosVenta() {
		return movimientosVenta;
	}

	public List<CajaMovimientoBean> getMovimientosApuntes() {
		return movimientosApuntes;
	}

	@XmlElementWrapper(name = "movimientos")
	@XmlElement(name = "movimiento")
	public List<CajaMovimientoBean> getMovimientos() {
		LinkedList<CajaMovimientoBean> movimientos = new LinkedList<>();
		movimientos.addAll(movimientosApuntes);
		movimientos.addAll(movimientosVenta);
		return movimientos;
	}

	public void addLineaRecuento(CajaLineaRecuentoBean lineaRecuento) {
		log.debug("addLineaRecuento() - Añadiendo nueva linea recuento");
		
		if (lineasRecuento == null) {
			lineasRecuento = new ArrayList<CajaLineaRecuentoBean>();
		}
		lineasRecuento.add(lineaRecuento);
		
		totales.setTotalRecuento(totales.getTotalRecuento().add(lineaRecuento.getTotal()));
	}

	public void setMovimientosVenta(List<CajaMovimientoBean> movimientosVenta) {
		this.movimientosVenta = movimientosVenta;
	}

	public void setMovimientosApuntes(List<CajaMovimientoBean> movimientosApuntes) {
		this.movimientosApuntes = movimientosApuntes;
	}

	public List<CajaLineaRecuentoBean> getLineasRecuento() {
		return lineasRecuento;
	}

	public void setLineasRecuento(List<CajaLineaRecuentoBean> lineasRecuento) {
		this.lineasRecuento = lineasRecuento;
	}

	public void setTotales(Totales totales) {
		this.totales = totales;
	}

	public BigDecimal getTotalVentasEntrada() {
		return totales.getTotalVentasEntrada();
	}

	public BigDecimal getTotalVentasSalida() {
		return totales.getTotalVentasSalida();
	}

	public BigDecimal getTotalApuntesEntrada() {
		return totales.getTotalApuntesEntrada();
	}

	public BigDecimal getTotalApuntesSalida() {
		return totales.getTotalApuntesSalida();
	}

	public BigDecimal getTotalRecuento() {
		return totales.getTotalRecuento();
	}

	public BigDecimal getTotalEntradas() {
		return totales.getTotalVentasEntrada().add(totales.getTotalApuntesEntrada());
	}

	public BigDecimal getTotalSalidas() {
		return totales.getTotalVentasSalida().add(totales.getTotalApuntesSalida());
	}

	public BigDecimal getTotal() {
		return getTotalEntradas().subtract(getTotalSalidas());
	}

	public BigDecimal getDescuadre() {
		return getTotalRecuento().subtract(getTotal());
	}

	public Integer getNumTicketsEntrada() {
		return numTicketsEntrada;
	}

	public Integer getNumTicketsSalida() {
		return numTicketsSalida;
	}

	public String getUidDiarioCaja() {
		return cajaBean.getUidDiarioCaja();
	}

	public Date getFechaApertura() {
		return cajaBean.getFechaApertura();
	}

	public Map<String, CajaLineaAcumuladoBean> getAcumulados() {
		return acumulados;
	}

	public void setFechaCierre(Date fechaCierre) {
		cajaBean.setFechaCierre(fechaCierre);
	}

	public Date getFechaCierre() {
		return cajaBean.getFechaCierre();
	}

	public void setFechaEnvio(Date fechaEnvio) {
		cajaBean.setFechaEnvio(fechaEnvio);
	}

	public Date getFechaEnvio() {
		return cajaBean.getFechaEnvio();
	}

	public String getCodAlm() {
		return cajaBean.getCodAlmacen();
	}

	public String getCodCaja() {
		return cajaBean.getCodCaja();
	}

	public int getTotalTickets() {
		return numTicketsEntrada + numTicketsSalida;
	}

	public String getCodAlmAsString() {
		return cajaBean.getCodAlmacen();
	}

	public String getCodCajaAsString() {
		return cajaBean.getCodCaja();
	}

	public String getUsuario() {
		return cajaBean.getUsuario();
	}

	public String getUsuarioCierre() {
		return cajaBean.getUsuarioCierre();
	}

	public void setUsuarioCierre(String usuarioCierre) {
		cajaBean.setUsuarioCierre(usuarioCierre);
	}

	public void setFechaContable(Date fechaContable) {
		cajaBean.setFechaContable(fechaContable);
	}

	public Date getFechaContable() {
		return cajaBean.getFechaContable();
	}

	public int getTotalTicketsAsString() {
		return numTicketsEntrada + numTicketsSalida;
	}

	public String getTotalApuntesEntradaAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalApuntesEntrada());
	}

	public String getTotalVentasEntradaAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalVentasEntrada());
	}

	public String getTotalEntradasAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalVentasEntrada().add(totales.getTotalApuntesEntrada()));
	}

	public String getTotalApuntesSalidaAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalApuntesSalida());
	}

	public String getTotalVentasSalidaAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalVentasSalida());
	}

	public String getTotalSalidasAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalVentasSalida().add(totales.getTotalApuntesSalida()));
	}

	public String getTotalAsString() {
		return FormatUtil.getInstance().formateaImporte(getTotalEntradas().subtract(getTotalSalidas()));
	}

	public String getTotalRecuentoAsString() {
		return FormatUtil.getInstance().formateaImporte(totales.getTotalRecuento());
	}

	public String getDescuadreAsString() {
		return FormatUtil.getInstance().formateaImporte(getTotalRecuento().subtract(getTotal()));
	}

	public CajaBean getCajaBean() {
		return cajaBean;
	}

	public void setNumTicketsEntrada(Integer numTicketsEntrada) {
		this.numTicketsEntrada = numTicketsEntrada;
	}

	public void setNumTicketsSalida(Integer numTicketsSalida) {
		this.numTicketsSalida = numTicketsSalida;
	}

}
