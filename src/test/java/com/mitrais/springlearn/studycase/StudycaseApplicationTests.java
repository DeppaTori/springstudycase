package com.mitrais.springlearn.studycase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mitrais.springlearn.studycase.test.OrderService;
import com.mitrais.springlearn.studycase.test.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StudycaseApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Test
	public void contextLoads() {
		Mockito.when(productService.getProductName()).thenReturn("Mock Product Name");
	      String testName = orderService.getProductName();
	      Assert.assertEquals("Mock Product Name", testName);
	}

}
