package app.security;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	/*@Autowired
	private UserService userService;*/

	//Возвращает юзера в формате нужном спрингу для его работы
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		/*User user = userService.getUser(userService.getUserId(login));

		if (user == null) {
			throw new UsernameNotFoundException("Username " + login + " not found");
		}*/

		return null;
	}
	
}
