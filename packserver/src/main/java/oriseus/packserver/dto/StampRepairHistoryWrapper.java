package oriseus.packserver.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class StampRepairHistoryWrapper {

	@JsonDeserialize(as=ArrayList.class, contentAs=StampRepairHistoryDTO.class)
	private ArrayList<StampRepairHistoryDTO> list;
	
	public StampRepairHistoryWrapper() {
		list = new ArrayList<StampRepairHistoryDTO>();
	}

	public ArrayList<StampRepairHistoryDTO> getList() {
		return list;
	}

	public void setList(ArrayList<StampRepairHistoryDTO> list) {
		this.list = list;
	}
	
}
