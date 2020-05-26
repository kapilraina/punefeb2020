package com.ms.bootcamp.productmicroservice.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ms.bootcamp.productmicroservice.model.DiscountRequest;
import com.ms.bootcamp.productmicroservice.model.DiscountResponse;
import com.ms.bootcamp.productmicroservice.model.Product;
import com.ms.bootcamp.productmicroservice.model.ProductDTO;
import com.ms.bootcamp.productmicroservice.repository.ProductRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
//@RibbonClient(name = "discountms",configuration = RibbonConfiguration.class)
@RibbonClient(name = "discountms")
public class ProductService {

	@Autowired
	ProductRepository repo;
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	@Lazy
	private RestTemplate lbrestTemplate;

	@Autowired
	DiscountFeignProxy discountProxy;

	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Optional<Product> getProduct(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	public void save(Product p) {
		repo.save(p);
	}

	/**
	 * Use generic Discovery Client
	 * 
	 * @param id
	 * @return
	 */

	public ProductDTO getProductv2(int id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances("DISCOUNTMS");
			System.out.println("\nserviceInstances \n" + serviceInstances);
			DiscountRequest drequest = new DiscountRequest();
			drequest.setCategory(p.getCategory());
			drequest.setMrp(p.getMrp());
			ServiceInstance instance = serviceInstances.get(0);
			URI uri = URI.create("http://" + instance.getHost() + ":" + instance.getPort() + "/caldisc");
			RestTemplate restTemplate = new RestTemplate();
			DiscountResponse discountResponse = restTemplate.postForObject(uri, drequest, DiscountResponse.class);
			ProductDTO pdto = new ProductDTO();
			pdto.setCategory(p.getCategory());
			pdto.setDrp(discountResponse.getDrp());
			pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
			pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
			pdto.setId(p.getId());
			pdto.setMrp(p.getMrp());
			pdto.setName(p.getName());
			pdto.setShortDescription(p.getShortDescription());
			pdto.setTags(p.getTags());
			return pdto;
		} else {
			return null;
		}

	}

	/**
	 * Use Eureka Client
	 * 
	 * @param id
	 * @return
	 */

	public ProductDTO getProductv3(int id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();

			DiscountRequest drequest = new DiscountRequest();
			drequest.setCategory(p.getCategory());
			drequest.setMrp(p.getMrp());

			InstanceInfo info = eurekaClient.getNextServerFromEureka("discountms", false);
			System.out.println("\n info \n" + info);
			URI uri = URI.create("http://" + info.getHostName() + ":" + info.getPort() + "/caldisc");
			RestTemplate restTemplate = new RestTemplate();
			DiscountResponse discountResponse = restTemplate.postForObject(uri, drequest, DiscountResponse.class);
			ProductDTO pdto = new ProductDTO();
			pdto.setCategory(p.getCategory());
			pdto.setDrp(discountResponse.getDrp());
			pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
			pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
			pdto.setId(p.getId());
			pdto.setMrp(p.getMrp());
			pdto.setName(p.getName());
			pdto.setShortDescription(p.getShortDescription());
			pdto.setTags(p.getTags());
			return pdto;
		} else {
			return null;
		}

	}

	/**
	 * Using the Ribbon API Directly
	 * 
	 * @param id
	 * @return
	 */
	public ProductDTO getProductv4(Integer id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();

			DiscountRequest drequest = new DiscountRequest();
			drequest.setCategory(p.getCategory());
			drequest.setMrp(p.getMrp());
			ServiceInstance serviceInstance = loadBalancer.choose("discountms");
			System.out.println("serviceInstance  =" + serviceInstance);
			String baseUrl = serviceInstance.getUri().toString();
			baseUrl = baseUrl + "/caldisc";

			RestTemplate restTemplate = new RestTemplate();
			DiscountResponse discountResponse = restTemplate.postForObject(baseUrl, drequest, DiscountResponse.class);
			ProductDTO pdto = new ProductDTO();
			pdto.setCategory(p.getCategory());
			pdto.setDrp(discountResponse.getDrp());
			pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
			pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
			pdto.setId(p.getId());
			pdto.setMrp(p.getMrp());
			pdto.setName(p.getName());
			pdto.setShortDescription(p.getShortDescription());
			pdto.setTags(p.getTags());
			return pdto;
		} else {
			return null;
		}

	}

	/**
	 * Using the Load Balancer Rest
	 * 
	 * @param id
	 * @return
	 */
	public ProductDTO getProductv5(Integer id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();

			DiscountRequest drequest = new DiscountRequest();
			drequest.setCategory(p.getCategory());
			drequest.setMrp(p.getMrp());
			DiscountResponse discountResponse = lbrestTemplate.postForObject("http://discountms/caldisc", drequest,
					DiscountResponse.class);
			ProductDTO pdto = new ProductDTO();
			pdto.setCategory(p.getCategory());
			pdto.setDrp(discountResponse.getDrp());
			pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
			pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
			pdto.setId(p.getId());
			pdto.setMrp(p.getMrp());
			pdto.setName(p.getName());
			pdto.setShortDescription(p.getShortDescription());
			pdto.setTags(p.getTags());
			return pdto;
		} else {
			return null;
		}

	}

	/**
	 * Using the Load Balancer Rest + Hystrix
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductv6(Integer id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();
			return p;
		} else {
			return null;
		}

	}

	@HystrixCommand(commandKey = "APPLY-DISCOUNT-COMMAND", fallbackMethod = "discountFallback", threadPoolKey = "discountFallback", commandProperties = {

			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),

			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })

	/*
	 * @HystrixCommand(commandKey = "APPLY-DISCOUNT-COMMAND", fallbackMethod =
	 * "discountFallback")
	 */
	public ProductDTO applyDiscount(Product p) {
		DiscountRequest drequest = new DiscountRequest();
		drequest.setCategory(p.getCategory());
		drequest.setMrp(p.getMrp());
		URI uri = null;
		try {
			uri = new URI("http://discountms/caldisc");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// throw e;
		}
		DiscountResponse discountResponse = lbrestTemplate.postForObject(uri, drequest, DiscountResponse.class);
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(discountResponse.getDrp());
		pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
		pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
		pdto.setId(p.getId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());
		return pdto;
	}

	public ProductDTO getProductv7(Integer id) {
		Optional<Product> op = getProduct(id);
		if (op.isPresent()) {
			Product p = op.get();
			DiscountRequest request = new DiscountRequest(p.getCategory(), p.getMrp());
			DiscountResponse response = discountProxy.calculateDiscount(request);
			ProductDTO pDto = new ProductDTO(p.getId(), p.getName(), p.getShortDescription(), p.getCategory(),
					p.getMrp(), response.getDrp(), response.getFixedCategoryDiscount(), response.getOnSpotDiscount(),
					p.getTags());
			return pDto;
		} else {
			return null;
		}

	}

	public ProductDTO discountFallback(Product p) {
		// log.debug("Short Circuit -discountFallback");
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setId(p.getId());
		pdto.setMrp(p.getMrp());
		pdto.setDrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());
		return pdto;
	}
}
