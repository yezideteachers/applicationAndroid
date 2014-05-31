package com.l3I.rally.server;


import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.l3I.rally.model.User;

public interface UserControllerInterface {
	@Put
	void create (User user);
	
	@Get
	Container getAllUsers(); 

}
