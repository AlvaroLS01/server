package com.comerzzia.pos.services.cupones.generation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.core.contadores.ContadorServiceException;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.coupons.types.CouponTypeDTO;
import com.comerzzia.pos.util.i18n.I18N;

@Component
public class CouponsCodeGeneratorService {

	public static final Integer GENERATION_MODE_ALEATORY = 0;
	public static final Integer GENERATION_MODE_SEQUENTIALLY = 1;
	public static final Integer GENERATION_MODE_MANUAL = 2;

	@Autowired
	Sesion sesion;

	/*
	 * Default format for sequentially and aleatory coupons CouponTypePrefix + PromotionId (10 digits pad zero left) +
	 * Balance (5 digits pad zero left) + Start date (AAMMDD) End date (AAMMDD) coupon identificator (8 digits)
	 */

	public String generateCouponCode(GeneratedCouponDto newCoupon) throws ContadorServiceException {
		CouponTypeDTO couponType = sesion.getSesionPromociones().getCouponTypeDTO(newCoupon.getCouponTypeCode());
		
		if (couponType != null && couponType.getGenerationMode().equals(GENERATION_MODE_MANUAL)) {
			if (StringUtils.isEmpty(newCoupon.getCouponCode())) {
				throw new RuntimeException(I18N.getTexto("El código de cupón no puede ser vacío en una generación manual."));
			}
			return newCoupon.getCouponCode();
		}
		else {
			// default mode aleatory
			return getCouponCodeAleatory(newCoupon);
		}
	}

	protected String getCouponBody(GeneratedCouponDto newCoupon) {
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");

		String promotionId = StringUtils.leftPad(newCoupon.getPromotionId().toString(), 10, "0");
		String balance = StringUtils.leftPad(newCoupon.getCouponAmount() == null ? "0" : newCoupon.getCouponAmount().multiply(new BigDecimal(100)).abs().toBigInteger().toString(), 5, "0");
		String startDate = (newCoupon.getStartDate() != null) ? df.format(newCoupon.getStartDate()) : "000000";
		String endDate = (newCoupon.getEndDate() != null) ? df.format(newCoupon.getEndDate()) : "000000";

		return promotionId + balance + startDate + endDate;
	}

	protected String getCouponCodeAleatory(GeneratedCouponDto newCoupon) {
		CouponTypeDTO couponType = sesion.getSesionPromociones().getCouponTypeDTO(newCoupon.getCouponTypeCode());
		String couponBody = getCouponBody(newCoupon);

		String value = RandomStringUtils.random(8, "0123456789ABCDEFGHJKLMNOPQRSTVWXYZ");
		String couponCode = formatCouponCode(value, couponType.getPrefix(), couponBody, couponType.getMaxLength());

		return couponCode;
	}

	protected String formatCouponCode(String value, String prefix, String suffix, Integer maxLength) {
		return StringUtils.join(new String[] { prefix, suffix, StringUtils.leftPad(value.toString(), maxLength, "0") });
	}

}
