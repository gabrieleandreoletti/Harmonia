package org.elis.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginCustomerDto {
	long id;
	@NotBlank 
	String username;
	@NotBlank 
	String password;
}
