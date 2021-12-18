package com.shorterurl.service.generation;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

@Component
public class HashIdGenerator  {

	public String generate(String url) {
		return Hashing.adler32().hashString(url, StandardCharsets.UTF_8).toString();
	}
}
