package com.lms.exception;

import lombok.Builder;

@Builder
public class ResourceNotFound extends RuntimeException{

	public ResourceNotFound() {
		super("Resource Not Found..!!");
	}
	
	public ResourceNotFound(String message) {
		super(message);
	}
}
