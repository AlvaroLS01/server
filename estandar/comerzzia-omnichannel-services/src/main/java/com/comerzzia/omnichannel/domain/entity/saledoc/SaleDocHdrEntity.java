package com.comerzzia.omnichannel.domain.entity.saledoc;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDocHdrEntity {
   private String activityUid;
   private Long salesDocId;
   private String companyCode;
   private Integer period;
   private String serialCode;
   private Long salesDocNumber;
   private java.util.Date salesDocDate;
   private String hour;
   private java.util.Date supplyDate;
   private String storeCode;
   private String tillCode;
   private String cashJournalUid;
   private String applicationCode;
   private String whConceptCode;
   private String customerCode;
   private String rateCode;
   private Long freightTypeId;
   private String contactPerson;
   private String customerReference;
   private Integer taxGroupId;
   private Long taxTreatmentId;
   private java.math.BigDecimal baseAmount;
   private java.math.BigDecimal taxAmount;
   private java.math.BigDecimal grandAmount;
   private String comments;
   private String userCode;
   private Long saleInvoiceId;
   private Long suppInvoiceId;
   private String lyCustomerCard;
   private String shipmentTracking;
   private String salesDocUid;
   private Long docTypeId;
   private String salesDocCode;
   private String sourceSalesDocCode;
   private Long sourceDocTypeId;
   private String salesDocSerial;
   private String currencyCode;
   private String customerDes;
   private String address;
   private String city;
   private String province;
   private String location;
   private String postalCode;
   private String countryCode;
   private String identificationTypeCode;
   private String vatNumber;
   private String invoiceCustomerCode;
   private Boolean deposit;
   private String carrierCode;
   private Long logStatusActionId;
   private Long logStatusId;
   private String expeditionUid;
   private String deliveryStoreCode;
   private Boolean pricesWithTaxes;
}
