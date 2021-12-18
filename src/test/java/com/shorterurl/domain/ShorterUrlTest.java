package com.shorterurl.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class ShorterUrlTest {

	@Test
	public void test_IdWasFilled() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		assertEquals("50328aa4", shorterUrl.getId());
	}
	
	@Test
	public void test_UrlWasFilled() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		assertEquals("http://www.google.com.br", shorterUrl.getUrl());
	}
	
	@Test
	public void test_CreatedAtWasFilled() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		assertNotNull(shorterUrl.getCreatedAt());
	}
	
	@Test
	public void test_ExpiredAtWasFilled() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		assertNotNull(shorterUrl.getExpiredAt());
	}
	
	@Test
	public void test_TinyUrlMethod() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		assertEquals("localhost:8080/url/50328aa4", shorterUrl.tinyUrl("localhost:8080/url"));
	}
}
