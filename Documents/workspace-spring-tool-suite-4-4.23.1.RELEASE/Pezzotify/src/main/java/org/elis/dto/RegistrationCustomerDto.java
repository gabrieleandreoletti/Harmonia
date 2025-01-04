package org.elis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationCustomerDto {

	@NotBlank
	@Size(min = 4, max = 15)
	private String username;
	@NotBlank
	@Size(min = 4, max = 15)
	private String nome;
	@NotBlank
	@Size(min = 4, max = 15)
	private String cognome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Pattern(regexp = "^.*(?=.{4,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$", message = "password must have at leat one capital letter, one number, one symbol and not less than 4 characters")
	private String password;
}
