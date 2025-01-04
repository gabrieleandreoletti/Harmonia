package org.elis.dto;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SongDto {
	long id;
	@NotBlank(message = "Il nome della canzone non può essere nullo")
	@Size(min = 3 , max = 20)
	private String name;
	
	private CustomerDto artist;
	
	private List<ArtistDto> featuring;
	
	
	private GenreDto genre;
	
	private AlbumDto album;

	private LocalDateTime releaseDate = LocalDateTime.now();
}
