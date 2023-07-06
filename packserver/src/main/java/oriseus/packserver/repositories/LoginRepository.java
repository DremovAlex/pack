package oriseus.packserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oriseus.packserver.models.Role;

@Repository
public interface LoginRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}
