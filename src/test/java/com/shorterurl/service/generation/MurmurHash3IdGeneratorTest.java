package com.shorterurl.service.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, 
classes = { MurmurHash3IdGenerator.class, MurmurHash3IdGeneratorTest.class })
public class MurmurHash3IdGeneratorTest {

	@Autowired
	private IGenerator generator;

	@Test
	public void test_IdGenerationForTheSameUrlIsUnique() {
		String id1 = generator.generate("http://www.google.com.br");
		String id2 = generator.generate("http://www.google.com.br");
		assertEquals(id1, id2);
	}
	
	@Test
	public void test_IdGenerationForDifferentUrlIsDifferent() {
		String id1 = generator.generate("http://www.google.com.br");
		String id2 = generator.generate("http://www.go0gle.com.br");
		assertNotEquals(id1, id2);
	}
}
