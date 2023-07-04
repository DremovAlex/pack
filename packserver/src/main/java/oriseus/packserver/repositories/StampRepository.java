package oriseus.packserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oriseus.packserver.models.Stamp;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Integer> {

	Stamp findByName(String name);

}
