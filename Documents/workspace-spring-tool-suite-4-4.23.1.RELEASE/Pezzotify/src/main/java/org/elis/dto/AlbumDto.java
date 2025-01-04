package org.elis.dto;

import java.util.List;


import lombok.Data;

@Data
public class AlbumDto {
	
	private long id;
	private String name;
	private List<SongDto> songs;
	private LabelDto label;
	private CustomerDto artist;
	
}
