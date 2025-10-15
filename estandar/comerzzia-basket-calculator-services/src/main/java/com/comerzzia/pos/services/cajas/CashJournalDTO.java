/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.cajas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.comerzzia.pos.persistence.cajas.CashJournalHdr;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalLine;
import com.comerzzia.pos.persistence.cajas.movimientos.CashJournalSummaryByPaymentCodeDTO;
import com.comerzzia.pos.persistence.cajas.recuentos.CashJournalCountLine;
import com.comerzzia.pos.services.mediospagos.MediosPagosService;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

public class CashJournalDTO implements Serializable {
	private static final long serialVersionUID = -3306061849786908599L;

	protected final CashJournalHdr cashJournalHdr;
	
	protected CashJournalTotalsDTO cashJournalTotals;
	protected Integer inputDocumentsCount;
	protected Integer outputDocumentsCount;
	protected List<CashJournalLine> salesMovements;
	protected List<CashJournalLine> manualMovements;
	
	protected List<CashJournalCountLine> cashJournalCountLines;
	protected final Map<String, CashJournalSummaryByPaymentCodeDTO> cashJournalSummaryByPaymentCode;

	protected MediosPagosService mediosPagosService = SpringContext.getBean(MediosPagosService.class);

	public CashJournalDTO() {
		this(new CashJournalHdr());
	}

	public CashJournalDTO(CashJournalHdr cashJournalHdr) {
		this.cashJournalHdr = cashJournalHdr;
		cashJournalSummaryByPaymentCode = new HashMap<>();
		cashJournalTotals = new CashJournalTotalsDTO();
		inputDocumentsCount = 0;
		outputDocumentsCount = 0;
		salesMovements = new LinkedList<>();
		manualMovements = new LinkedList<>();
	}

	public void recalculateTotals() {
		cashJournalSummaryByPaymentCode.clear();
		for (CashJournalLine movimiento : salesMovements) {
			CashJournalSummaryByPaymentCodeDTO acumulado = cashJournalSummaryByPaymentCode.get(movimiento.getPaymentMethodCode());
			if (acumulado == null) {
				acumulado = new CashJournalSummaryByPaymentCodeDTO(mediosPagosService.getMedioPago(movimiento.getPaymentMethodCode()));
				cashJournalSummaryByPaymentCode.put(movimiento.getPaymentMethodCode(), acumulado);
			}
			acumulado.addMovimiento(movimiento);
		}
		for (CashJournalLine movimiento : manualMovements) {
			CashJournalSummaryByPaymentCodeDTO acumulado = cashJournalSummaryByPaymentCode.get(movimiento.getPaymentMethodCode());
			if (acumulado == null) {
				acumulado = new CashJournalSummaryByPaymentCodeDTO(mediosPagosService.getMedioPago(movimiento.getPaymentMethodCode()));
				cashJournalSummaryByPaymentCode.put(movimiento.getPaymentMethodCode(), acumulado);
			}
			acumulado.addMovimiento(movimiento);
		}
	}

	public void recalculateCountTotals() {
		cashJournalTotals.setTotalCount(BigDecimal.ZERO);

		for (CashJournalSummaryByPaymentCodeDTO cajaLinea : cashJournalSummaryByPaymentCode.values()) {
			cajaLinea.limpiarRecuento();
		}
		for (CashJournalCountLine linea : cashJournalCountLines) {
			cashJournalTotals.setTotalCount(cashJournalTotals.getTotalCount().add(linea.getTotal()));
			CashJournalSummaryByPaymentCodeDTO acumulado = cashJournalSummaryByPaymentCode.get(linea.getPaymentMethodCode());
			if (acumulado == null) {
				acumulado = new CashJournalSummaryByPaymentCodeDTO(mediosPagosService.getMedioPago(linea.getPaymentMethodCode()));
				cashJournalSummaryByPaymentCode.put(linea.getPaymentMethodCode(), acumulado);
			}
			acumulado.addLineaRecuento(linea);
		}
	}

	public void addCountLine(CashJournalCountLine cashJournalCountLine) {
		CashJournalCountLine lineaRecuentoExistente = null;
		for (CashJournalCountLine cajaLineaRecuento : cashJournalCountLines) {
			if (cajaLineaRecuento.getPaymentMethodCode().equals(cashJournalCountLine.getPaymentMethodCode()) && BigDecimalUtil.isIgual(cajaLineaRecuento.getCountValue(), cashJournalCountLine.getCountValue())
			        && mediosPagosService.getMedioPago(cajaLineaRecuento.getPaymentMethodCode()).getEfectivo()) {
				lineaRecuentoExistente = cajaLineaRecuento;
			}
		}
		if (lineaRecuentoExistente != null) {
			lineaRecuentoExistente.setQuantity(lineaRecuentoExistente.getQuantity() + cashJournalCountLine.getQuantity());
		}
		else {
			cashJournalCountLines.add(cashJournalCountLine);
		}
		cashJournalTotals.setTotalCount(cashJournalTotals.getTotalCount().add(cashJournalCountLine.getTotal()));
	}

	public void removeCountLine(CashJournalCountLine cashJournalCountLine) {
		cashJournalTotals.setTotalCount(cashJournalTotals.getTotalCount().subtract(cashJournalCountLine.getTotal()));
		cashJournalCountLines.remove(cashJournalCountLine);
	}

	public List<CashJournalLine> getSalesMovements() {
		return salesMovements;
	}

	public List<CashJournalLine> getManualMovements() {
		return manualMovements;
	}

	public List<CashJournalLine> getCashJournalLines() {
		LinkedList<CashJournalLine> movements = new LinkedList<>();
		movements.addAll(manualMovements);
		movements.addAll(salesMovements);
		return movements;
	}

	public void setSalesMovements(List<CashJournalLine> movements) {
		this.salesMovements = movements;
	}

	public void setManualMovements(List<CashJournalLine> movements) {
		this.manualMovements = movements;
	}

	public List<CashJournalCountLine> getCashJournalCountLines() {
		return cashJournalCountLines;
	}

	public void setCashJournalCountLines(List<CashJournalCountLine> cashJournalCountLines) {
		this.cashJournalCountLines = cashJournalCountLines;
	}

	public void setCashJournalTotals(CashJournalTotalsDTO totals) {
		this.cashJournalTotals = totals;
	}

	public BigDecimal getTotalSalesInput() {
		return cashJournalTotals.getTotalSalesInput();
	}

	public BigDecimal getTotalSalesOutput() {
		return cashJournalTotals.getTotalSalesOutput();
	}

	public BigDecimal getTotalMovementsInput() {
		return cashJournalTotals.getTotalMovementsInput();
	}

	public BigDecimal getTotalMovementsOutput() {
		return cashJournalTotals.getTotalMovementsOutput();
	}

	public BigDecimal getTotalCount() {
		return cashJournalTotals.getTotalCount();
	}

	public BigDecimal getInputsTotals() {
		return cashJournalTotals.getTotalSalesInput().add(cashJournalTotals.getTotalMovementsInput());
	}

	public BigDecimal getOutputsTotal() {
		return cashJournalTotals.getTotalSalesOutput().add(cashJournalTotals.getTotalMovementsOutput());
	}

	public BigDecimal getTotal() {
		return getInputsTotals().subtract(getOutputsTotal());
	}

	public BigDecimal getCashMismatch() {
		return getTotalCount().subtract(getTotal());
	}

	public Integer getInputDocumentsCount() {
		return inputDocumentsCount;
	}

	public Integer getOutputDocumentsCount() {
		return outputDocumentsCount;
	}

	public String getCashJournalUid() {
		return cashJournalHdr.getCashJournalUid();
	}

	public Date getOpeningDate() {
		return cashJournalHdr.getOpeningDate();
	}

	public Map<String, CashJournalSummaryByPaymentCodeDTO> getCashJournalSummaryByPaymentCode() {
		return cashJournalSummaryByPaymentCode;
	}

	public void setClosingDate(Date closingDate) {
		cashJournalHdr.setClosingDate(closingDate);
	}

	public Date getClosingDate() {
		return cashJournalHdr.getClosingDate();
	}

	public void setTransmissionDate(Date transmissionDate) {
		cashJournalHdr.setTransmissionDate(transmissionDate);
	}

	public Date getTransmissionDate() {
		return cashJournalHdr.getTransmissionDate();
	}

	public String getStoreCode() {
		return cashJournalHdr.getStoreCode();
	}

	public String getTillCode() {
		return cashJournalHdr.getTillCode();
	}

	public int getTotalDocumentsCount() {
		return inputDocumentsCount + outputDocumentsCount;
	}

	public String getOpenUserCode() {
		return cashJournalHdr.getOpenUserCode();
	}

	public String getCloseUserCode() {
		return cashJournalHdr.getCloseUserCode();
	}

	public void setCloseUserCode(String userCode) {
		cashJournalHdr.setCloseUserCode(userCode);
	}

	public void setAccountingDate(Date accountingDate) {
		cashJournalHdr.setAccountingDate(accountingDate);
	}

	public Date getAccountingDate() {
		return cashJournalHdr.getAccountingDate();
	}

	public CashJournalHdr getCashJournalHdr() {
		return cashJournalHdr;
	}

	public void setInputDocumentsCount(Integer inputDocumentsCount) {
		this.inputDocumentsCount = inputDocumentsCount;
	}

	public void setOutputDocumentsCount(Integer outputDocumentsCount) {
		this.outputDocumentsCount = outputDocumentsCount;
	}

}
