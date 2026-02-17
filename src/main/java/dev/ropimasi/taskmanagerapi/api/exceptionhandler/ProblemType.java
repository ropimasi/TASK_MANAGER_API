package dev.ropimasi.taskmanagerapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	MESSAGE_NOT_READBLE("/message-not-readble", "Message not readable"),
	ARGUMENT_NOT_VALID("/argument-not-valid", "Argument not valid");
	
	private String uri;
	private String title;
	

	ProblemType(String path, String title) {
		this.uri = "https://taskmanager.ropimasi.dev/api/problems" + path;
		this.title = title;
	}

}
