package com.freedom.web;

import com.freedom.web.controller.PublicationController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class SpringmvcApplicationTests {
	@Autowired
	private PublicationController publicationController;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		this.mockMvc.perform(get("/publish/all"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
