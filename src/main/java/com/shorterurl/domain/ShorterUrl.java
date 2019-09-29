package com.shorterurl.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@RedisHash("ShorterUrl")
@ApiModel(description = "Represents data from URL registered in database with a generated short ID")
public class ShorterUrl implements Serializable {

	private static final long serialVersionUID = -6572972326868097075L;

	@Id
	@ApiModelProperty(notes = "Id of the short URL", name = "id", required = true, example = "7uHb82q", position = 0)
	private String id;
	
	@ApiModelProperty(notes = "Original URL", name = "url", required = true, example = "https://www.google.com", position = 1)
	private String url;
	
	@ApiModelProperty(notes = "URL creation date", name = "createdAt", required = true, example = "2019-10-20 18:45:12", position = 2)
	private LocalDateTime createdAt;
	
	@ApiModelProperty(notes = "URL expiration date", name = "expiredAt", required = true, example = "2020-10-20 18:45:12", position = 3)
	private LocalDateTime expiredAt;
	
	public ShorterUrl() {
	}
	
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

	public String tinyUrl(String localUrl) {
		return localUrl.concat("/").concat(id);
	}
}
