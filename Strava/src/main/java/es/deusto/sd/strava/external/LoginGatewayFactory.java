package es.deusto.sd.strava.external;

import es.deusto.sd.strava.entity.ServidorAuth;

public class LoginGatewayFactory {	
	public ILoginServiceGateway getLoginServiceGateway(ServidorAuth servidor) {
		switch (servidor) {
		case GOOGLE:
			return new GoogleServiceGateway();
//		case META:
//			return new MetaServiceGateway();
		default:
			return null;
		}
	}
}
