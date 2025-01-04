package org.elis.mapper;

import org.elis.dto.SubscriptionDto;

import org.elis.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubscriptionMapper {
	public Subscription fromSubscriptionDto(SubscriptionDto c);

	public SubscriptionDto toSubscriptionDto(Subscription c);
}
