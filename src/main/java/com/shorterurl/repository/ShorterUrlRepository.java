package com.shorterurl.repository;

import com.shorterurl.domain.ShorterUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShorterUrlRepository extends CrudRepository<ShorterUrl, String> {}
