package oriseus.packserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oriseus.packserver.models.StampRepairHistory;

@Repository
public interface StampRepairHistoryReppository extends JpaRepository<StampRepairHistory, Integer> {

	List<StampRepairHistory> findByStampName(String name);

}
