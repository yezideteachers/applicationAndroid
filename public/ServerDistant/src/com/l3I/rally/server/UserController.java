package com.l3I.rally.server;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.l3I.rally.model.User;

public class UserController  extends ServerResource implements UserControllerInterface {

	public UserController() {
		
	}

	@Override
	
	public void create(User user){
		ObjectifyService.register(User.class);
		Objectify ofy = ObjectifyService.begin();

		User tp = new User();
		tp.setFirstname(user.getFirstname());
		tp.setLastname(user.getLastname());
		tp.setMail(user.getMail());
		tp.setPhone(user.getPhone());
		ofy.put(tp);
	}
	
	/* on recupere tous les utilisateurs � l'aide d'une query puis on cr�e notre container et on le retourne */

	@Override
	public Container getAllUsers() {
		Container content = null;
		List<User> users = new ArrayList<User>();
		ObjectifyService.register(User.class);
		Objectify ofy = ObjectifyService.begin();

		Query<User> q = ofy.query(User.class);

		for (User u : q)
			users.add(u);


		content = new Container();
		content.setUser_list(users);

		return content;
 }
}