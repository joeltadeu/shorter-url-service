package com.shorterurl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.repository.ShorterUrlRepository;
import com.shorterurl.service.exception.ObjectNotFoundException;
import com.shorterurl.service.generation.IGenerator;
import com.shorterurl.service.validation.IValidator;

@RunWith(MockitoJUnitRunner.class)
public class ShorterUrlServiceTest {

	@Mock
	ShorterUrlRepository repository;
	
	@Mock
	IValidator validator;
	
	@Mock
	IGenerator generator;
	
	@InjectMocks
	ShorterUrlService service;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
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
		exceptionRule.expect(ObjectNotFoundException.class);
	    exceptionRule.expectMessage("URL not found for {id: 50328aa45}");
		Optional<ShorterUrl> optional = Optional.empty();
		when(repository.findById("50328aa45")).thenReturn(optional);
		service.getOriginalURL("50328aa45");
		
	}
}
