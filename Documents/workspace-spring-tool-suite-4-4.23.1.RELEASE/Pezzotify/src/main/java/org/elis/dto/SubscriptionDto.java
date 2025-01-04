package org.elis.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionDto {
	long id;
	@NotBlank
	private double price;
	@NotBlank
	@Size(min = 3 , max =  20)
	private String name;
	private LocalDateTime subscriptionDate;
	private LocalDateTime expiration;
}
