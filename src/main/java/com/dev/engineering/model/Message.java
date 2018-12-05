package com.dev.engineering.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Messaging Model", description="Save message from user and return it as JSON response")
public class Message {
	
	@ApiModelProperty(notes="Text variable for message")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
