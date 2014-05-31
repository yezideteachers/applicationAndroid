package com.l3I.rally.server;

import java.io.File;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.security.Guard;

/* ce Dispatcher extend application et implemente la methode createInboundRoot */

@SuppressWarnings({ "deprecation", "unused" })
public class RestletDispatch extends Application {
	
	public synchronized Restlet createInboundRoot(){
		/* on cree un nouveau router avec le context actuel de l'application */
		
		final Router router = new Router(getContext());
		this.getConnectorService().getClientProtocols().add(Protocol.CLAP);
		this.getConnectorService().getClientProtocols().add(Protocol.HTTP);
		this.getConnectorService().getClientProtocols().add(Protocol.HTTPS);
		
		/* on indique que les liens contenant /user seront redirigé vers la classe UserController */
		router.attach("/user",UserController.class);
		return router;
	}

}
