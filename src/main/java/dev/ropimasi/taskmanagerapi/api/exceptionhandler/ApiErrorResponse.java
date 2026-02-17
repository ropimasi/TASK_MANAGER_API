package dev.ropimasi.taskmanagerapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@JsonInclude(Include.NON_NULL)
public record ApiErrorResponse(
		int status,
		String message,
		LocalDateTime timestamp,
		List<ErrorDetail> errors) {

	public record ErrorDetail(String field, String message) {
	}
}
