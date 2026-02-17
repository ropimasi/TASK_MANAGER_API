package dev.ropimasi.taskmanagerapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;



@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
/* Para padronização e conveniência escolhi usar a especificação da RFC 7807 (Problem Details for HTTP APIs). */
	
	private Integer status;
	private String type;
	private String title;
	private String detail;

	private String userMessage;
	private LocalDateTime timestamp;

	private List<Field> fields;


	/* Criar classe interna aqui dentro já que vai ser usada somente aqui dentro. */
	@Getter
	@Builder
	public static class Field {
		private String name;
		private String userMessage;

	}

}
