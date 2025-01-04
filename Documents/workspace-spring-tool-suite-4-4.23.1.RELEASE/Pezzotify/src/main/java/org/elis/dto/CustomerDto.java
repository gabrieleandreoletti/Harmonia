package org.elis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {
	private long id;
	@NotBlank
	private String username;
	@JsonIgnore
	private List<FollowDto> follower;
	@JsonIgnore
	private List<FollowDto> followed;
	private List<SongDto> userSongs;
	private List<PlaylistDto> userPlaylist;
	private SubscriptionDto sub;
	private String nome, cognome, email, password;
}
