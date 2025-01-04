package org.elis.dto;

import java.util.List;

import lombok.Data;

@Data
public class ArtistDto {
	long id;
	String username;
	List<SongDto> featuring;
	List<AlbumDto> album;
	List<SongDto> songs;
}
