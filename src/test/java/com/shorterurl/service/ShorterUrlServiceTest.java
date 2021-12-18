package com.shorterurl.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.shorterurl.service.generation.HashIdGenerator;
import com.shorterurl.service.generation.HashIdGeneratorTest;
import com.shorterurl.service.validation.ShorterUrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.repository.ShorterUrlRepository;
import com.shorterurl.service.exception.ObjectNotFoundException;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShorterUrlServiceTest {

	@Mock
	ShorterUrlRepository repository;
	
	@Mock
	ShorterUrlValidator validator;
	
	@Mock
	HashIdGenerator generator;
	
	@InjectMocks
	ShorterUrlService service;
	
	@Test
	public void test_saveUrlAndReturnShorterUrl() {
		ShorterUrl shorterUrl = new ShorterUrl("50328aa4", "http://www.google.com.br");
		when(generator.generate("http://www.google.com.br")).thenReturn("50328aa4");
		when(repository.save(shorterUrl)).thenReturn(shorterUrl);
		assertEquals("localhost:8080/url/50328aa4", service.shorterUrl("localhost:8080/url", "http://www.google.com.br"));
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

		Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.getOriginalURL("50328aa45");
		});

		String expectedMessage = "URL not found for {id: 50328aa45}";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
	}
}
