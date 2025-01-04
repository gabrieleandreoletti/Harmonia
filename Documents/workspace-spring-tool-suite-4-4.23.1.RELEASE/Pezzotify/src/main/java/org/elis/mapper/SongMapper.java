package org.elis.mapper;

import org.elis.dto.SongDto;
import org.elis.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SongMapper {

	public Song fromSongDto(SongDto c); 
	public SongDto toSongDto(Song c); 
}
