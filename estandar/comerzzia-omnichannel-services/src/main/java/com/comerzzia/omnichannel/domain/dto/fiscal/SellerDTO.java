package com.comerzzia.omnichannel.domain.dto.fiscal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerDTO {
    private String companyCode;
    private String companyDes;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String phone1;
    private String phone2;
    private String fax;
    private String vatNumber;
    private String tradeName;
}