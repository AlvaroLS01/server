package com.comerzzia.omnichannel.domain.dto.fiscal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipientDTO {
    private String name;
    private String address;
    private String city;
    private String province;
    private String location;
    private String identificationTypeCode;
    private String countryCode;
    private String postalCode;
    private String phone;
    private String vatNumber;

    private String bank;
    private String bankAdress;
    private String bankCity;
    private String iban;
}
