package com.comerzzia.pos.services.core.sesion.paymentMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.payments.configuration.PaymentMethodConfiguration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethodsData {
    protected Map<String, MedioPagoBean> mediosPago = new HashMap<>();
    protected List<MedioPagoBean> mediosPagoTarjetas = new ArrayList<>();
    protected List<MedioPagoBean> mediosPagoContado = new ArrayList<>();
    protected List<MedioPagoBean> mediosPagoVisibleVenta = new ArrayList<>();
    protected MedioPagoBean medioPagoDefecto;
    protected MedioPagoBean medioPagoPromocional;
    protected MedioPagoBean medioPagoEntregaCuenta;
    protected MedioPagoBean medioPagoVale; 
    
    protected List<PaymentMethodConfiguration> paymentMethodConfigurations;
}
