package com.shorterurl.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shorterurl.service.ShorterUrlService;

@RunWith(SpringRunner.class)
@WebMvcTest(ShorterUrlResource.class)
public class ShorterUrlResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShorterUrlService service;

	@Test
	public void test_RedirectUrlEndpointFromService() throws Exception {
		when(service.getOriginalURL("50328aa4")).thenReturn("http://www.google.com.br");
		this.mockMvc.perform(get("/url/50328aa4")).andDo(print()).andExpect(status().is3xxRedirection());
	}

	@Test
	public void test_ShorterUrlEndpointFromService() throws Exception {
		when(service.shorterUrl("localhost:8080/url", "http://www.google.com.br"))
				.thenReturn("localhost:8080/url/50328aa4");
		this.mockMvc.perform(post("/url").contentType(MediaType.APPLICATION_JSON).content("http://www.google.com.br")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
}