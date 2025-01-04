package org.elis.mapper;

import org.elis.dto.PlaylistDto;

import org.elis.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlaylistMapper {

	public Playlist fromPlaylistDto(PlaylistDto c);

	public PlaylistDto toPlaylistDto(Playlist c);
}
