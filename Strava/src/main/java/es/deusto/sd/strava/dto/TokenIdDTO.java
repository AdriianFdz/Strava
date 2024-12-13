package es.deusto.sd.strava.dto;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class TokenIdDTO {
	@NotNull
	private String token;
	@NotNull
	private Integer id;
	
	public TokenIdDTO(String token, Integer id) {
		super();
		this.token = token;
		this.id = id;
	}
	
	public TokenIdDTO() {
		super();
		this.token = "";
		this.id = null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenIdDTO other = (TokenIdDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(token, other.token);
	}
	
	
}
