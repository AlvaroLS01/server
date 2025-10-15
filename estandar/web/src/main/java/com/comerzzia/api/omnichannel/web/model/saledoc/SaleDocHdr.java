package com.comerzzia.api.omnichannel.web.model.saledoc;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocHdr {
	protected Long salesDocId;
	protected String companyCode;
	protected Integer period;
	protected String serialCode;
	protected Long salesDocNumber;
	protected java.util.Date salesDocDate;
	protected String hour;
	protected java.util.Date supplyDate;
	protected String storeCode;
	protected String tillCode;
	protected String cashJournalUid;
	protected String applicationCode;
	protected String whConceptCode;
	protected String customerCode;
	protected String rateCode;
	protected Long freightTypeId;
	protected String contactPerson;
	protected String customerReference;
	protected Integer taxGroupId;
	protected Long taxTreatmentId;
	protected java.math.BigDecimal baseAmount;
	protected java.math.BigDecimal taxAmount;
	protected java.math.BigDecimal grandAmount;
	protected String comments;
	protected String userCode;
	protected Long saleInvoiceId;
	protected Long suppInvoiceId;
	protected String lyCustomerCard;
	protected String shipmentTracking;
	protected String salesDocUid;
	
	protected Long docTypeId;
	protected String docTypeCode;
	protected String docTypeDes;
	
	protected String salesDocCode;
	protected String sourceSalesDocCode;
	protected Long sourceDocTypeId;
	protected String salesDocSerial;
	protected String currencyCode;
	protected String customerDes;
	protected String address;
	protected String city;
	protected String province;
	protected String location;
	protected String postalCode;
	protected String countryCode;
	protected String identificationTypeCode;
	protected String vatNumber;
	protected String invoiceCustomerCode;
	protected Boolean deposit;
	protected String carrierCode;
	protected Long logStatusActionId;
	protected Long logStatusId;
	protected String expeditionUid;
	protected String deliveryStoreCode;
	protected Boolean pricesWithTaxes;

	protected List<SaleDocLine> lines;
	protected List<SaleDocPayment> payments;
	protected List<SaleDocTax> taxes;
}
