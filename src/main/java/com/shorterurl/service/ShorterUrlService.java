package com.shorterurl.service;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.repository.ShorterUrlRepository;
import com.shorterurl.service.exception.ObjectNotFoundException;
import com.shorterurl.service.generation.HashIdGenerator;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ShorterUrlService {

  private final HashIdGenerator generator;

  private final ShorterUrlRepository repository;

  public ShorterUrlService(ShorterUrlRepository repository, HashIdGenerator generator) {
    this.repository = repository;
    this.generator = generator;
  }

  public String getOriginalURL(String id) {
    Optional<ShorterUrl> opt = repository.findById(id);
    opt.orElseThrow(
        () -> new ObjectNotFoundException(String.format("URL not found for {id: %s}", id)));
    return opt.get().getUrl();
  }

  public ShorterUrl shorterUrl(String url) {
    String id = generator.generate(url);
    ShorterUrl shorterUrl = new ShorterUrl(id, url);
    repository.save(shorterUrl);
    return shorterUrl;
  }
}
