package oriseus.packserver.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oriseus.packserver.dto.StampRepairHistoryDTO;
import oriseus.packserver.dto.StampRepairHistoryWrapper;
import oriseus.packserver.models.Stamp;
import oriseus.packserver.models.StampRepairHistory;
import oriseus.packserver.repositories.StampRepairHistoryReppository;
import oriseus.packserver.utils.Convert;

@Service
@Transactional
public class StampRepairHistoryService {

	private final StampRepairHistoryReppository stampRepairHistoryReppository;
	private final StampService stampService;
	private final Convert convert;
	
	@Autowired
	public StampRepairHistoryService(StampRepairHistoryReppository stampRepairHistoryReppository,
									StampService stampService,
									Convert convert) {
		this.stampRepairHistoryReppository = stampRepairHistoryReppository;
		this.stampService = stampService;
		this.convert = convert;
	}
	
	@Transactional
	public void addRepairHistory(StampRepairHistory stampRepairHistory) {
		enrichStampRepairHistory(stampRepairHistory);
		stampRepairHistoryReppository.save(stampRepairHistory);
	}
	
	public StampRepairHistoryWrapper findByName(String name) {
		Stamp stamp = stampService.findByName(name);
		List<StampRepairHistory> stampRepairHistories = stampRepairHistoryReppository.findByStampName(name);
		ArrayList<StampRepairHistoryDTO> stampRepairHistoryDTOs = new ArrayList<StampRepairHistoryDTO>();
		for (StampRepairHistory stampRepairHistory : stampRepairHistories) {
			StampRepairHistoryDTO stampRepairHistoryDTO = convert.convertToStampRepairHistoryDTO(stampRepairHistory);
			stampRepairHistoryDTO.setStampDTO(convert.convertToStampDTO(stamp));
			stampRepairHistoryDTOs.add(stampRepairHistoryDTO);
		}
		
		StampRepairHistoryWrapper stampRepairHistoryWrapper = new StampRepairHistoryWrapper();
		stampRepairHistoryWrapper.setList(stampRepairHistoryDTOs);
		return stampRepairHistoryWrapper;
	}
	
	private void enrichStampRepairHistory(StampRepairHistory stampRepairHistory) {
		stampRepairHistory.setStamp(stampService.findByName(stampRepairHistory.getStamp().getName()));
		stampRepairHistory.setRepairDate(LocalDateTime.now());
	}
}
