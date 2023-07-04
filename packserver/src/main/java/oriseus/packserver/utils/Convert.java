package oriseus.packserver.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import oriseus.packserver.dto.StampDTO;
import oriseus.packserver.dto.StampDamageHistoryDTO;
import oriseus.packserver.dto.StampRepairHistoryDTO;
import oriseus.packserver.models.Stamp;
import oriseus.packserver.models.StampDamageHistory;
import oriseus.packserver.models.StampRepairHistory;

public class Convert {
	
	private final ModelMapper modelMapper;
	
	@Autowired
	public Convert(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Stamp convertToStamp(StampDTO stampDTO) {
		return modelMapper.map(stampDTO, Stamp.class);
	}
	public StampDTO convertToStampDTO(Stamp stamp) {
		return modelMapper.map(stamp, StampDTO.class);
	}
	public StampDamageHistory convertToStampDamageHistory(StampDamageHistoryDTO stampDamageHistoryDTO) {
		return modelMapper.map(stampDamageHistoryDTO, StampDamageHistory.class);
	}
	public StampDamageHistoryDTO convertToDamageHistoryDTO(StampDamageHistory stampDamageHistory) {
		return modelMapper.map(stampDamageHistory, StampDamageHistoryDTO.class);
	}
	public StampRepairHistory convertToStampRepairHistory(StampRepairHistoryDTO stampRepairHistoryDTO) {
		return modelMapper.map(stampRepairHistoryDTO, StampRepairHistory.class);
	}
	public StampRepairHistoryDTO convertToStampRepairHistoryDTO(StampRepairHistory stampRepairHistory) {
		return modelMapper.map(stampRepairHistory, StampRepairHistoryDTO.class);
	}
}
