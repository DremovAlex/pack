package oriseus.packserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oriseus.packserver.models.Stamp;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Integer> {

	Optional<Stamp> findByName(String name);

	Optional<Stamp> findById(Integer id);
}
