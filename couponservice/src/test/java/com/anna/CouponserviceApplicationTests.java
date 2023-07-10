package com.anna;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

@SpringBootTest
@AutoConfigureMockMvc
class CouponserviceApplicationTests {

	@Autowired
	MockMvc mvc;
	
	@Test
	void testGetCouponWithoutAuth_Forbidden() throws Exception {
		mvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"USER"})
	void testGetCouponWithAuth_Success() throws Exception {
		mvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isOk()).andExpect(content().string("{\"id\":2,\"code\":\"SUPERSALE\",\"discount\":10000.00,\"expDate\":\"12/1/2020\"}"));
	}
	
	
}
