package org.elis.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "follower_id", "followed_id" }))
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Customer follower;
	@ManyToOne
	private Customer followed;

	@CreationTimestamp
	private LocalDateTime orario;

	@UpdateTimestamp
	private LocalDateTime orarioAggiornato;

}
