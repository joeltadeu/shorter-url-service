package com.shorterurl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shorterurl.domain.ShorterUrl;

@Repository
public interface ShorterUrlRepository extends CrudRepository<ShorterUrl, String>{

}
