package org.elis.dto;

import org.elis.model.Customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FollowDto {
	private long id;
	@NotBlank
	private Customer followed,follower;
	
	
}
