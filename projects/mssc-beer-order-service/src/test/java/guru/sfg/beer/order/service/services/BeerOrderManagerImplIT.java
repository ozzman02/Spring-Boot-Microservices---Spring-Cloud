package guru.sfg.beer.order.service.services;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.jgroups.util.Util.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.domain.Customer;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.repositories.CustomerRepository;
import guru.sfg.beer.order.service.services.beer.BeerServiceImpl;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;

@ExtendWith(WireMockExtension.class)
@SpringBootTest
public class BeerOrderManagerImplIT {
	
	@Autowired
	BeerOrderManager beerOrderManager;
	
	@Autowired
	BeerOrderRepository beerOrderRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	WireMockServer wireMockServer;
	
	@Autowired
	ObjectMapper objectMapper;
	
	Customer testCustomer;
	
	UUID beerId = UUID.randomUUID();
	
	@TestConfiguration
	static class RestTemplateBuilderProvider {
		
		@Bean(destroyMethod="stop")
		public WireMockServer wireMockServer() {
			WireMockServer server = with(wireMockConfig().port(8083));
			server.start();
			return server;
		}
	}
	
	@BeforeEach
	void setup() {
		testCustomer = customerRepository.save(Customer.builder().customerName("Test Customer").build());
	}
	
	@Test
	void testNewToAllocate() throws JsonProcessingException {
		
		BeerDto beerDto = BeerDto.builder().id(beerId).upc("12345").build();
		
		//BeerPagedList list = new BeerPagedList(List.of(beerDto));
		
		wireMockServer.stubFor(
				get(BeerServiceImpl.BEER_UPC_PATH_V1 + "12345")
				.willReturn(okJson(objectMapper.writeValueAsString(beerDto))));
		
		BeerOrder beerOrder = createBeerOrder();
		BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);
		assertNotNull(savedBeerOrder);
		assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());
	}
	
	public BeerOrder createBeerOrder() {
		
		BeerOrder beerOrder = BeerOrder.builder()
				.customer(testCustomer)
				.build();
		
		Set<BeerOrderLine> lines = new HashSet<>();
		
		lines.add(BeerOrderLine.builder()
				.beerId(beerId)
				.upc("12345")
				.orderQuantity(1)
				.beerOrder(beerOrder)
				.build());
		
		beerOrder.setBeerOrderLines(lines);
		
		return beerOrder;
	}
	
}
