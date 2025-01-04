package org.elis.utilities;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.management.relation.Role;

import org.elis.model.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JWTUtilities {
	private static final String KEY = "uauuagdubqjkdnqdjbqobdoqbdjqbdoboidbewoifbw";

	private SecretKey getSignatureKey() {
		return Keys.hmacShaKeyFor(KEY.getBytes());
	}

	public String generateToken(Customer utente) {
		long oggi = System.currentTimeMillis();
		long oggiPiuTrenta = oggi + (1000L * 60 * 60 * 24 * 30);
		Date issuedAt = new Date(oggi);
		Date expiration = new Date(oggiPiuTrenta);

		return Jwts.builder().subject(utente.getUsername()).issuedAt(issuedAt).expiration(expiration).claims()
				.add("role", utente.getRuolo()).and().signWith(getSignatureKey()).compact();
	}

	private Claims getPayload(String token) throws SignatureException {
		return Jwts.parser().verifyWith(getSignatureKey()).build().parseSignedClaims(token).getPayload();
	}

	public Role getRole(String token) throws SignatureException {
		return getPayload(token).get("role", Role.class);
	}

	public String getSubject(String token) throws SignatureException {
		return getPayload(token).getSubject();
	}

	public Date getIssuedAt(String token) throws SignatureException {
		return getPayload(token).getIssuedAt();
	}

	public Date getExpiration(String token) throws SignatureException {
		return getPayload(token).getExpiration();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		return !isTokenExpired(token) && isSubjectValid(token, userDetails) && isUserValid(userDetails);
	}

	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date(System.currentTimeMillis()));
	}

	private boolean isSubjectValid(String token, UserDetails userDetails) {
		return getSubject(token).equals(userDetails.getUsername());
	}

	private boolean isUserValid(UserDetails userDetails) {
		return userDetails.isEnabled() && userDetails.isAccountNonExpired() && userDetails.isCredentialsNonExpired();
	}

}
