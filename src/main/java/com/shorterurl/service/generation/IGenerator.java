package com.shorterurl.service.generation;

import org.springframework.stereotype.Component;

@Component
public interface IGenerator {

	public String generate(String url);
}
