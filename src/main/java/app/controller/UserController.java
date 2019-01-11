package app.controller;

import app.dto.RoleDto;
import app.dto.UserDto;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Светлана on 30.07.2018.
 */

@Controller
public class UserController {
	public static final String baseURL = "http://localhost:8090/admin/users";
	public static final String URLforUser = "http://localhost:8090/admin/users/{id}";
	public static final String URLforRoles = "http://localhost:8090/admin/roles";
	public static final RestTemplate restTemplate = new RestTemplate();

	//RestTemplate можно и мб нужно один на все приложение, использовать getForEntity

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(Model model) {
		return "error";
	}

    /*@RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(Model model, @AuthenticationPrincipal User principal) {
        model.addAttribute("currentUser", principal);
        return "user";
    }*/

	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	public String authorization(@ModelAttribute("user") UserDto user, HttpServletResponse response) {
		ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity
				("http://localhost:8090/authorization?login=" + user.getLogin() + "&password=" + user.getPassword(), user, UserDto.class);
		String sessionID = responseEntity.getHeaders().get("Set-Cookie").get(0);
		sessionID = sessionID.split(";")[0];
		Cookie cookie = new Cookie("sessionID", sessionID);
		response.addCookie(cookie);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String usersList(Model model, @CookieValue(value = "sessionID") String sessionID) {
		List<UserDto> listUsers = Arrays.asList(restTemplate.exchange(baseURL, HttpMethod.GET, createRequest(sessionID), UserDto[].class).getBody());
		List<RoleDto> listRoles = Arrays.asList(restTemplate.exchange(URLforRoles, HttpMethod.GET, createRequest(sessionID), RoleDto[].class).getBody());
		model.addAttribute("usersList", listUsers);
		model.addAttribute("rolesList", listRoles);
		return "admin";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") UserDto user, @CookieValue(value = "sessionID") String sessionID) {
		restTemplate.exchange(baseURL, HttpMethod.POST, new HttpEntity<>(user, createHeaders(sessionID)), UserDto.class);
		//restTemplate.postForObject(baseURL, createRequest(sessionID), UserDto.class, user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
	public String deleteUser(long id, @CookieValue(value = "sessionID") String sessionID) {
		restTemplate.exchange(URLforUser, HttpMethod.DELETE, createRequest(sessionID), String.class, id);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") UserDto user, @CookieValue(value = "sessionID") String sessionID) {
		restTemplate.exchange(URLforUser, HttpMethod.PUT, new HttpEntity<>(user, createHeaders(sessionID)), UserDto.class, user.getId());
		return "redirect:/admin";
	}

	public HttpEntity<String> createRequest(String sessionID) {
		return new HttpEntity<>(createHeaders(sessionID));
	}

	public HttpHeaders createHeaders(String sessionID) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", sessionID);
		return headers;
	}
}
