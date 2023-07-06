package oriseus.packserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oriseus.packserver.dto.RoleDTO;
import oriseus.packserver.models.Role;
import oriseus.packserver.repositories.LoginRepository;
import oriseus.packserver.utils.Convert;

@Service
public class LoginService {

	private final LoginRepository loginRepository;
	private final Convert convert;
	
	@Autowired
	public LoginService(LoginRepository loginRepository,
						Convert convert) {
		this.loginRepository = loginRepository;
		this.convert = convert;
	}
	
	public RoleDTO getRoleByName(RoleDTO roleDTO) {
		
		Role role = convert.convertToRole(roleDTO);
		Role newRole = loginRepository.findByName(role.getName());
		
		if (role == null || !role.getName().equals(newRole.getName()) || !role.getPassword().equals(newRole.getPassword())) {
			return null;
		} else {			
			return convert.convertToRoleDTO(newRole);
		}
	}
}
