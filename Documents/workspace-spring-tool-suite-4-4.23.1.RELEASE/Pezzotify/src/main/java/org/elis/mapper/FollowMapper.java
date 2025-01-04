package org.elis.mapper;

import org.elis.dto.FollowDto;
import org.elis.model.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FollowMapper {
	public Follow fromFollowDto(FollowDto c);

	public FollowDto toFollowDto(Follow c);
}
