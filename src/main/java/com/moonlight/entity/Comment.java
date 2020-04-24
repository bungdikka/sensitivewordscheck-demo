package com.moonlight.entity;

import com.moonlight.validator.Sensitive;

public class Comment {
	@Sensitive
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
