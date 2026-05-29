package com.shorterurl.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.shorterurl.service.ShorterUrlService;

@ExtendWith(MockitoExtension.class)
public class ShorterUrlControllerTest {

	@Mock
	private ShorterUrlService service;

	@InjectMocks
	private ShorterUrlController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void test_RedirectUrlEndpointFromService() throws Exception {
		when(service.getOriginalURL("50328aa4")).thenReturn("http://www.google.com.br");
		this.mockMvc.perform(get("/url/50328aa4")).andDo(print()).andExpect(status().is3xxRedirection());
	}

	@Test
	public void test_ShorterUrlEndpointFromService() throws Exception {
		when(service.shorterUrl("http://localhost/url", "http://www.google.com.br"))
				.thenReturn("http://localhost/url/50328aa4");
		this.mockMvc.perform(post("/url")
				.contentType(MediaType.APPLICATION_JSON)
				.content("http://www.google.com.br")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
