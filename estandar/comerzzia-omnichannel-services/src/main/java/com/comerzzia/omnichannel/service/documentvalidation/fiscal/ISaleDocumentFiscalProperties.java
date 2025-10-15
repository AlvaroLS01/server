package com.comerzzia.omnichannel.service.documentvalidation.fiscal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface ISaleDocumentFiscalProperties {
	public interface SourceDocument extends ISaleDocumentFiscalProperties {
	}
	
	public interface Totals {
	   BigDecimal getBaseAmount();
	   BigDecimal getTotalAmount();
	   BigDecimal getTaxAmount();
	   BigDecimal getBalanceDue();
	   BigDecimal getChangeDue(); 
	}
	
	public interface Payment {
		String getPaymentMethodCode();
		String getPaymentMethodDes();
		BigDecimal getAmount();
	}
	
	public interface Seller {
	    String getCompanyCode();
	    String getCountryCode();
	    String getIdentificationTypeCode();	    
	    String getVatNumber();	    
	}
	
	public interface Recipient {
	    String getCustomerCode();
	    String getName();
	    String getCountryCode();
	    String getAddress();
	    String getCity();
	    String getProvince();
	    String getLocation();
	    String getPostalCode();
	    
	    String getIdentificationTypeCode();	    
	    String getVatNumber();
	}
	
	String getSalesDocUid();
	String getSalesDocSerial();	
    Long getSalesDocNumber();
    String getSalesDocCode();
    Long getTaxTreatmentId();
    Date getDocumentDate();
    
    Long getDocTypeId();
    String getDocTypeCode();    
        
    Seller getSeller();
                    
    Recipient getRecipient();
    
    Totals getTotals();
        
    List<Payment> getPayments();
    
    SourceDocument getSourceDocument();
}
