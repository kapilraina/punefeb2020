package com.ms.bootcamp.DiscountMicroservice;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class DiscountController {

	private static Logger log = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	DiscountDataMap discountDataMap;
	/*
	 * @Autowired AuditService auditService;
	 * 
	 * @Autowired DiscountStreamService discountStreamService;
	 */

	@PostConstruct
	public void initBean() {
		/*
		 * discountMap.put(ProductCategory.FURNISHING, 1.0);
		 * discountMap.put(ProductCategory.KITCHENELECTRONIC, 3.0);
		 * discountMap.put(ProductCategory.MEDICALEQUIPMENT, 7.0);
		 * discountMap.put(ProductCategory.MOBILEPHONE, 5.0);
		 * discountMap.put(ProductCategory.MUSICINSTRUMENT, 9.0);
		 * discountMap.put(ProductCategory.TOY, 2.0);
		 */
	}

	@RequestMapping(value = "/caldisc", method = RequestMethod.POST)
	public DiscountResponse calculateDiscount(@RequestBody DiscountRequest request) {
		log.info(request.toString());
		double fixedCategoryDiscount = discountDataMap.getDiscountMap().get(request.getCategory());
		double onSpotDiscount = Math.floor(Math.random() * 10);
		double discountper = fixedCategoryDiscount + onSpotDiscount;
		double drp = Math.ceil(request.getMrp() - ((discountper / 100) * request.getMrp()));
		DiscountResponse response = new DiscountResponse(request.getCategory(), request.getMrp(), drp,
				fixedCategoryDiscount, onSpotDiscount);
		/*
		 * String responseJSON = ""; try { ObjectMapper obj = new ObjectMapper();
		 * responseJSON = obj.writeValueAsString(response); // Should be taken to an
		 * async thread to not block the response, since this is a // realtime call from
		 * other MS. auditService.pubAuditEvent(responseJSON);
		 * discountStreamService.pubAuditEvent(responseJSON);
		 * 
		 * } catch (Exception e) {
		 * log.error("Couldnt Emit Discount Audit Event/Stream. Error :" +
		 * e.getMessage()); log.info(responseJSON); }
		 */

		return response;
	}

	@RequestMapping(value = "/getrandom", method = RequestMethod.GET)
	public double getRandom() {

		return Math.ceil(Math.random() * 10);
	}

}
