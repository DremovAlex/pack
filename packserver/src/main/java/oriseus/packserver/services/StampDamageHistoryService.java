package oriseus.packserver.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oriseus.packserver.dto.StampDamageHistoryDTO;
import oriseus.packserver.dto.StampDamageHistoryWrapper;
import oriseus.packserver.models.Stamp;
import oriseus.packserver.models.StampDamageHistory;
import oriseus.packserver.repositories.StampDamageHistoryRepository;
import oriseus.packserver.utils.Convert;

@Service
@Transactional
public class StampDamageHistoryService {

	private final StampDamageHistoryRepository stampDamageHistoryRepository;
	private final StampService stampService;
	private final Convert convert;
	
	public StampDamageHistoryService(StampDamageHistoryRepository stampDamageHistoryRepository,
									StampService stampService,
									Convert convert) {
		this.stampDamageHistoryRepository = stampDamageHistoryRepository;
		this.stampService = stampService;
		this.convert = convert;
	}
	
	@Transactional
	public void addDamageHistory(StampDamageHistory stampDamageHistory) throws IOException {
		Stamp stamp = stampDamageHistory.getStamp();
		stampService.updateStamp(stamp);
		stampDamageHistoryRepository.save(stampDamageHistory);		
	}
	
	public StampDamageHistoryWrapper findbyName(String name) {
		Stamp stamp = stampService.findByName(name);
		List<StampDamageHistory> stampDamageHistories = stampDamageHistoryRepository.findByStampName(name);
		ArrayList<StampDamageHistoryDTO> stampDamageHistoryDTOs = new ArrayList<StampDamageHistoryDTO>();
		for (StampDamageHistory stampDamageHistory : stampDamageHistories) {
			StampDamageHistoryDTO stampDamageHistoryDTO = convert.convertToDamageHistoryDTO(stampDamageHistory);
			stampDamageHistoryDTO.setStampDTO(convert.convertToStampDTO(stamp));
			stampDamageHistoryDTOs.add(stampDamageHistoryDTO);
		}
		StampDamageHistoryWrapper stampWrapper = new StampDamageHistoryWrapper();
		stampWrapper.setList(stampDamageHistoryDTOs);
		return stampWrapper;
	}
}
