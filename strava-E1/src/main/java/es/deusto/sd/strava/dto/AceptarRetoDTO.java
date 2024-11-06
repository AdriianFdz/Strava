package es.deusto.sd.strava.dto;

public class AceptarRetoDTO {
	private String userToken;
	private int idReto;

	// Constructor sin argumentos
	public AceptarRetoDTO() {
	}

	// Constructor con argumentos
	public AceptarRetoDTO(String userToken, int idReto) {
		this.userToken = userToken;
		this.idReto = idReto;
	}

	// Getters y setters
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public int getIdReto() {
		return idReto;
	}

	public void setIdReto(int idReto) {
		this.idReto = idReto;
	}
}
