package com.shorterurl.service.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shorterurl.service.exception.ValidationErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ShorterUrlValidatorTest {

	@InjectMocks
	private ShorterUrlValidator validator;

	@Test
	public void test_UrlNotEnteredWithSpaces() {

		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate("     ");
		});

		String expectedMessage = "URL not entered";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_UrlNotEnteredWithNull() {
		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate(null);
		});

		String expectedMessage = "URL not entered";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_UrlDoesNotHasDomain() {
		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate("http://rer");
		});

		String expectedMessage = "URL is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_UrlHasUnacceptableCharacters() {
		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate("http://google$.com.^br");
		});

		String expectedMessage = "URL is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_UrlHasUnacceptableFTPProtocol() {
		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate("ftp.uol.com.br");
		});

		String expectedMessage = "URL is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_UrlHasUnacceptableHTTPProtocol() {
		Exception exception = assertThrows(ValidationErrorException.class, () -> {
			validator.validate("htto://www.uol.com");
		});

		String expectedMessage = "URL is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
