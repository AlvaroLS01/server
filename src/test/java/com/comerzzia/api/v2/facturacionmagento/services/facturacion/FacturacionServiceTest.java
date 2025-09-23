package com.comerzzia.api.v2.facturacionmagento.services.facturacion;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.FacturacionRequest;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Promotion;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PromotionApplied;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Promotions;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PromotionsApplied;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.PromotionsSummary;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.Ticket;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TicketItem;
import com.comerzzia.api.v2.facturacionmagento.web.rest.facturacion.dtos.models.TicketItems;
import com.comerzzia.omnichannel.model.documents.sales.ticket.CuponAplicadoTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.PromocionTicket;
import com.comerzzia.omnichannel.model.documents.sales.ticket.lineas.PromocionLineaTicket;

public class FacturacionServiceTest {

    private FacturacionService facturacionService;
    private Promotion promotion;
    private PromotionApplied promotionApplied;

    @Before
    public void setUp() {
        facturacionService = new FacturacionService();
        promotion = new Promotion();
        promotion.setPromotionId(1);
        promotion.setDiscountAmount(BigDecimal.TEN);

        Promotions promotions = new Promotions();
        promotions.setPromotion(Collections.singletonList(promotion));

        TicketItem ticketItem = new TicketItem();
        ticketItem.setPromotions(promotions);

        TicketItems ticketItems = new TicketItems();
        ticketItems.setTicketItem(Collections.singletonList(ticketItem));

        Ticket ticket = new Ticket();
        ticket.setTicketItems(ticketItems);

        promotionApplied = new PromotionApplied();
        promotionApplied.setPromotionId(1);
        promotionApplied.setDiscountAmount(5.0);

        PromotionsApplied promotionsApplied = new PromotionsApplied();
        promotionsApplied.setPromotionApplied(Collections.singletonList(promotionApplied));

        PromotionsSummary promotionsSummary = new PromotionsSummary();
        promotionsSummary.setPromotionsApplied(promotionsApplied);

        FacturacionRequest request = new FacturacionRequest();
        request.setTicket(ticket);
        request.setPromotionsSummary(promotionsSummary);

        facturacionService.request = request;
    }

    @Test
    public void shouldAdjustLineAndSummaryPromotionDiscountsToNegative() throws Exception {
        Method ajustarLineas = FacturacionService.class.getDeclaredMethod("ajustarLineasDevolucion", Ticket.class);
        ajustarLineas.setAccessible(true);

        ajustarLineas.invoke(facturacionService, facturacionService.request.getTicket());

        assertThat(promotion.getDiscountAmount()).isEqualByComparingTo(BigDecimal.TEN.negate());
        assertThat(promotionApplied.getDiscountAmount()).isEqualTo(-5.0d);
    }

    @Test
    public void shouldPropagateNegativeDiscountsToTicketPromotionModels() throws Exception {
        Method ajustarLineas = FacturacionService.class.getDeclaredMethod("ajustarLineasDevolucion", Ticket.class);
        ajustarLineas.setAccessible(true);
        ajustarLineas.invoke(facturacionService, facturacionService.request.getTicket());

        PromocionLineaTicket promocionLineaTicket = new PromocionLineaTicket();
        promocionLineaTicket.setImporteTotalDto(promotion.getDiscountAmount());

        PromocionTicket promocionTicket = new PromocionTicket();
        promocionTicket.setImporteTotalAhorro(BigDecimal.valueOf(promotionApplied.getDiscountAmount()));

        CuponAplicadoTicket cuponAplicadoTicket = new CuponAplicadoTicket();
        cuponAplicadoTicket.setImporteTotalAhorrado(BigDecimal.valueOf(promotionApplied.getDiscountAmount()));

        assertThat(promocionLineaTicket.getImporteTotalDto()).isEqualByComparingTo(BigDecimal.TEN.negate());
        assertThat(promocionTicket.getImporteTotalAhorro()).isEqualByComparingTo(BigDecimal.valueOf(-5.0d));
        assertThat(cuponAplicadoTicket.getImporteTotalAhorrado()).isEqualByComparingTo(BigDecimal.valueOf(-5.0d));
    }
}
