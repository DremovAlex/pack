package oriseus.packserver.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class StampWrapper {

	@JsonDeserialize(as=ArrayList.class, contentAs=StampDTO.class)
	private ArrayList<StampDTO> list;
	
	public StampWrapper() {
		list = new ArrayList<StampDTO>();
	}

	public ArrayList<StampDTO> getList() {
		return list;
	}

	public void setList(ArrayList<StampDTO> list) {
		this.list = list;
	}
}
