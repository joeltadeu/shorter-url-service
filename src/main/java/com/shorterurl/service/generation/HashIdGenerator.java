package com.shorterurl.service.generation;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
public class HashIdGenerator  {

	public String generate(String url) {
		return Hashing.adler32().hashString(url, StandardCharsets.UTF_8).toString();
	}
}
