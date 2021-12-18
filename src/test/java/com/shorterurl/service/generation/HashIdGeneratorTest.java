package com.shorterurl.service.generation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class HashIdGeneratorTest {

	@InjectMocks
	private HashIdGenerator generator;

	@Test
	public void test_IdGenerationForTheSameUrlIsUnique() {
		String id1 = generator.generate("https://www.google.com.br");
		String id2 = generator.generate("https://www.google.com.br");
		assertEquals(id1, id2);
	}
	
	@Test
	public void test_IdGenerationForDifferentUrlIsDifferent() {
		String id1 = generator.generate("https://www.google.com.br");
		String id2 = generator.generate("https://www.go0gle.com.br");
		assertNotEquals(id1, id2);
	}
}
