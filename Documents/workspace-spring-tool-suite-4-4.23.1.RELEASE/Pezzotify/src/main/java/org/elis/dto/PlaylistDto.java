package org.elis.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlaylistDto {
	long id;
	
	@NotBlank(message = "Il nome della playlist non può essere nullo")
	@Size(min = 3 , max = 20)
	private String name;
	
	private List<SongDto> songs;
	
	
	private CustomerDto creator;
	
	
}
