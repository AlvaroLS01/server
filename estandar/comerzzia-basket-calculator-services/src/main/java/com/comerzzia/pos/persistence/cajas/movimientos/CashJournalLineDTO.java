package com.comerzzia.pos.persistence.cajas.movimientos;

import java.math.BigDecimal;

import com.comerzzia.pos.util.format.FormatUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CashJournalLineDTO extends CashJournalLine {
    private String paymentMethodDes;
    
    public BigDecimal getOutput() {
    	if (output == null){
    		return BigDecimal.ZERO;
    	}
        return output;
    }

    public BigDecimal getInput() {
    	if (input == null){
    		return BigDecimal.ZERO;
    	}
        return input;
    }

    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------
    
    public String getAbonoTicket() {
        if (input == null || input.compareTo(BigDecimal.ZERO)== 0){
            return null;
        }
        return FormatUtil.getInstance().formateaImporte(input);
    }
    
    public String getCargoTicket() {
        if (output == null || output.compareTo(BigDecimal.ZERO)== 0){
            return null;
        }
        return FormatUtil.getInstance().formateaImporte(output);
    }
    
    public String getFechaTicket() {

    	String fechaTicket = FormatUtil.getInstance().formateaFechaCorta(cashJournalDate);
    	String horaTicket = FormatUtil.getInstance().formateaHora(cashJournalDate);

    	return fechaTicket +" "+ horaTicket;
    }
}