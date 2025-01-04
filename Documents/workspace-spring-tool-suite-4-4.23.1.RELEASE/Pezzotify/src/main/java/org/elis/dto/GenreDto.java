package org.elis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreDto {
	private long id;
	@NotBlank(message = "Il nome del genere non può essere nullo")
	@Size(min = 3 , max = 20)
	private String name;
}
