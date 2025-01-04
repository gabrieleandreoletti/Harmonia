package org.elis.mapper;

import org.elis.dto.LabelDto;

import org.elis.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LabelMapper {
	public Label fromLabelDto(LabelDto c);

	public LabelDto toLabelDto(Label c);
}
