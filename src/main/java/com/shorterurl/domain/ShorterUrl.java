package com.shorterurl.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ShorterUrl")
@Schema(description = "Represents data from URL registered in database with a generated short ID")
public class ShorterUrl implements Serializable {

	private static final long serialVersionUID = -6572972326868097075L;

	@Id
	@Schema(description = "Id of the short URL", example = "7uHb82q", requiredMode = Schema.RequiredMode.REQUIRED)
	private String id;

	@Schema(description = "Original URL", example = "https://www.google.com", requiredMode = Schema.RequiredMode.REQUIRED)
	private String url;

	@Schema(description = "URL creation date", example = "2019-10-20 18:45:12", requiredMode = Schema.RequiredMode.REQUIRED)
	private LocalDateTime createdAt;

	@Schema(description = "URL expiration date", example = "2020-10-20 18:45:12", requiredMode = Schema.RequiredMode.REQUIRED)
	private LocalDateTime expiredAt;
	
	public ShorterUrl(String id, String url) {
		this.id = id;
		this.url = url;
		this.createdAt = LocalDateTime.now();
		this.expiredAt = LocalDateTime.now().plusYears(1);
	}
	
	public String getId() {
		return id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}
	
	public String tinyUrl(String localUrl) {
		return localUrl.concat("/").concat(id);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShorterUrl other = (ShorterUrl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
