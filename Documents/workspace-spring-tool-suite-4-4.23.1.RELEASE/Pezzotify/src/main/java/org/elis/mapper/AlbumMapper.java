package org.elis.mapper;

import org.elis.dto.AlbumDto;
import org.elis.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AlbumMapper {

	public Album fromAlbumDto(AlbumDto c); 
	public AlbumDto toAlbumDto(Album c); 
}
