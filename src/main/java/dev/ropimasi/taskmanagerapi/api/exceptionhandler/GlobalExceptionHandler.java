package dev.ropimasi.taskmanagerapi.api.exceptionhandler;

import java.time.LocalDateTime;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import dev.ropimasi.taskmanagerapi.api.service.exception.ResourceNotFoundException;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage("The requested resource was not found. Please check the ID and try again.")
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	
	@Override
	protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.ARGUMENT_NOT_VALID;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder((HttpStatus)status, problemType, detail)
				.userMessage("Validation failed.")
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	

	@Override
	protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READBLE;
		// My choose: do not use "ex.getMessage()" because it is too technical and not user-friendly.
		String detail = "The request body is invalid. Check for syntax errors, missing fields, or incorrect data types.";
		
		Problem problem = createProblemBuilder((HttpStatus)status, problemType, detail)
				.userMessage("Invalid value provided for a field, or a sintax error in the request body. Check data types (e.g., boolean, numbers, string) and the JSON structure.")
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	
	
	/* My approach: this method "handleExceptionInternal()" is a centralizer of response sender, including a
	 * "Problem" object in response body, adhering to Problem Details for HTTP APIs. */
	@Override
	protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		if (body == null) {
			body = Problem.builder().status(statusCode.value()).title(ex.getMessage())
					.userMessage(((HttpStatus) statusCode).getReasonPhrase()).timestamp(LocalDateTime.now()).build();
		} else if (body instanceof String) {
			body = Problem.builder().status(statusCode.value()).title((String) body).userMessage(((String) body))
					.timestamp(LocalDateTime.now()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}

	
	private Problem.ProblemBuilder createProblemBuilder(
			HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);		
	}

}
 
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
