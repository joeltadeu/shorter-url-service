package com.shorterurl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.shorterurl.service.generation.HashIdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.repository.ShorterUrlRepository;
import com.shorterurl.service.exception.ObjectNotFoundException;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShorterUrlServiceTest {

	@Mock
	ShorterUrlRepository repository;

	@Mock
	HashIdGenerator generator;

	@InjectMocks
	ShorterUrlService service;

	@Test
	public void test_saveUrlAndReturnShorterUrl() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		when(generator.generate("http://www.google.com.br")).thenReturn("50328aa4");
		when(repository.save(shorterUrl)).thenReturn(shorterUrl);

		ShorterUrl result = service.shorterUrl("http://www.google.com.br");

		assertEquals("50328aa4", result.getId());
		assertEquals("http://www.google.com.br", result.getUrl());
	}

	@Test
	public void test_getOriginalUrl() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		Optional<ShorterUrl> optional = Optional.of(shorterUrl);
		when(repository.findById("50328aa4")).thenReturn(optional);
		assertEquals("http://www.google.com.br", service.getOriginalURL("50328aa4"));
	}

	@Test
	public void test_ObjectNotFoundExceptionWhenIdIsWrong() {
		Optional<ShorterUrl> optional = Optional.empty();
		when(repository.findById("50328aa45")).thenReturn(optional);

		Exception exception = assertThrows(ObjectNotFoundException.class, () ->
				service.getOriginalURL("50328aa45"));

		assertTrue(exception.getMessage().contains("URL not found for {id: 50328aa45}"));
	}
}
