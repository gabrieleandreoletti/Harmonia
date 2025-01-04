package org.elis.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true, unique = true)
	private String name;
	@Column(nullable = true)
	@Min(value = 0)
	@Max(value = 999)
	private double price;

	private LocalDateTime subscriptionDate;

	private LocalDateTime expiration;

	@OneToMany(mappedBy = "subscription")
	private List<Customer> customer;

	@CreationTimestamp
	private LocalDateTime orario;

	@UpdateTimestamp
	private LocalDateTime orarioAggiornato;
}
