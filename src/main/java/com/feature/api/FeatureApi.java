package com.feature.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import com.feature.dto.Product;
import com.feature.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feature")
@RequiredArgsConstructor
public class FeatureApi {

	private final FeatureManager manager;

	public static final Feature DISCOUNT_APPLIED = new NamedFeature("DISCOUNT_APPLIED");

	private final InventoryService service;

	@GetMapping("/orders")
	public List<Product> showAvailableProducts() {
		if (manager.isActive(DISCOUNT_APPLIED)) {
			return applyDiscount(service.getAllProducts());
		} else {
			return service.getAllProducts();
		}
	}

	private List<Product> applyDiscount(List<Product> availableProducts) {
		List<Product> orderListAfterDiscount = new ArrayList<>();
		availableProducts.forEach(order -> {
			order.setPrice(order.getPrice() - (order.getPrice() * 5 / 100));
			orderListAfterDiscount.add(order);
		});
		return orderListAfterDiscount;
	}

}
