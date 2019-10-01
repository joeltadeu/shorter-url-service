package com.shorterurl.service.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.shorterurl.config.UrlValidatorConfig;
import com.shorterurl.service.exception.ValidationErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, 
classes = { UrlValidatorConfig.class, ShorterUrlValidatorTest.class })
public class ShorterUrlValidatorTest {

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Autowired
	private IValidator validator;


	@Test
	public void test_UrlNotEnteredWithSpaces() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL not entered");
	    validator.validate("     ");
	}
	
	@Test
	public void test_UrlNotEnteredWithNull() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL not entered");
	    validator.validate(null);
	}
	
	@Test
	public void test_UrlDoesNotHasDomain() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL is invalid");
	    validator.validate("http://rer");
	}
	
	@Test
	public void test_UrltHasUnacceptableCharacters() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL is invalid");
	    validator.validate("http://google$.com.^br");
	}
	
	@Test
	public void test_UrltHasUnacceptableFTPProtocol() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL is invalid");
	    validator.validate("ftp.uol.com.br");
	}
	
	@Test
	public void test_UrltHasUnacceptableHTTPProtocol() {
		exceptionRule.expect(ValidationErrorException.class);
	    exceptionRule.expectMessage("URL is invalid");
	    validator.validate("htto://www.uol.com");
	}
}
