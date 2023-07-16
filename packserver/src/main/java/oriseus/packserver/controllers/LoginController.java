package oriseus.packserver.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.dto.RoleDTO;
import oriseus.packserver.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Value("${token}")	
	private String token;
	
	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody RoleDTO roleDTO,
								   @RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		RoleDTO responseRoleDTO = loginService.getRoleByName(roleDTO); 
		if (responseRoleDTO != null) {
			return new ResponseEntity<>(responseRoleDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Incorrect login or password", HttpStatus.NOT_FOUND);
		}
	}
}
