package com.shorterurl.service.generation;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

@Component
public class MurmurHash3IdGenerator implements IGenerator {

	@Override
	public String generate(String url) {
		return Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
	}
}
