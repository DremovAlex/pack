package oriseus.pack.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class StampDamageHistoryWrapper {
	@JsonDeserialize(as=ArrayList.class, contentAs=StampDamageHistoryDTO.class)
	private ArrayList<StampDamageHistoryDTO> list;
	
	public StampDamageHistoryWrapper() {
		list = new ArrayList<StampDamageHistoryDTO>();
	}

	public ArrayList<StampDamageHistoryDTO> getList() {
		return list;
	}

	public void setList(ArrayList<StampDamageHistoryDTO> list) {
		this.list = list;
	}
}
