package oriseus.pack.dto;

import java.time.LocalDateTime;

public class StampDamageHistoryDTO {

	private LocalDateTime dateOfDamageDetection;
    private Integer shift;
    private String descriptionOfDamage;
    private String nameOfTechnicalMap;
    private StampDTO stampDTO;
	
	public LocalDateTime getDateOfDamageDetection() {
		return dateOfDamageDetection;
	}
	public void setDateOfDamageDetection(LocalDateTime dateOfDamageDetection) {
		this.dateOfDamageDetection = dateOfDamageDetection;
	}
	public String getDescriptionOfDamage() {
		return descriptionOfDamage;
	}
	public void setDescriptionOfDamage(String descriptionOfDamage) {
		this.descriptionOfDamage = descriptionOfDamage;
	}
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	public String getNameOfTechnicalMap() {
		return nameOfTechnicalMap;
	}
	public void setNameOfTechnicalMap(String nameOfTechnicalMap) {
		this.nameOfTechnicalMap = nameOfTechnicalMap;
	}
	public StampDTO getStampDTO() {
		return stampDTO;
	}
	public void setStampDTO(StampDTO stampDTO) {
		this.stampDTO = stampDTO;
	}	
	
}
