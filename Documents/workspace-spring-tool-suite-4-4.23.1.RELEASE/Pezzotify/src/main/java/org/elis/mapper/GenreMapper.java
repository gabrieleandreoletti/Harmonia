package org.elis.mapper;

import org.elis.dto.GenreDto;
import org.elis.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GenreMapper {

	public Genre fromGenreDto(GenreDto c); 
	public GenreDto toGenreDto(Genre c); 
}
