package com.shorterurl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.repository.ShorterUrlRepository;
import com.shorterurl.service.exception.ObjectNotFoundException;
import com.shorterurl.service.generation.IGenerator;
import com.shorterurl.service.validation.IValidator;

@Service
public class ShorterUrlService {

	private final IValidator validator;
	
	private final IGenerator generator;
	
	private final ShorterUrlRepository repository;

	public ShorterUrlService(ShorterUrlRepository repository, IValidator validator, IGenerator generator) {
		this.repository = repository;
		this.validator = validator;
		this.generator = generator;
	}

	public String getOriginalURL(String id) {
		Optional<ShorterUrl> opt = repository.findById(id);
		opt.orElseThrow(() -> new ObjectNotFoundException(String.format("URL not found for {id: %s}",id)));
		return opt.get().getUrl();
	}

	public String shorterUrl(String localUrl, String url) {
		validator.validate(url);
		String id = generator.generate(url);
		ShorterUrl shorterUrl = new ShorterUrl(id, url);
		repository.save(shorterUrl);
		return shorterUrl.tinyUrl(localUrl);
	}
}
