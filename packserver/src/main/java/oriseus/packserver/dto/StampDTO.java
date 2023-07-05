package oriseus.packserver.dto;

import java.time.LocalDateTime;

public class StampDTO {

	private Integer id;
	private String name; 
    private String storageCell;
    private String technologicalMapName;
    private Boolean damaged;
    private Boolean repairPack;
    private Boolean availability;
    private Boolean disposal;
    private Long price;
    private LocalDateTime addingDate;
    private String notes;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStorageCell() {
		return storageCell;
	}
	public void setStorageCell(String storageCell) {
		this.storageCell = storageCell;
	}
	public Boolean isDamaged() {
		return damaged;
	}
	public void setDamaged(Boolean damaged) {
		this.damaged = damaged;
	}
	public Boolean isRepairPack() {
		return repairPack;
	}
	public void setRepairPack(Boolean repairPack) {
		this.repairPack = repairPack;
	}
	public Boolean isAvailability() {
		return availability;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	public Boolean isDisposal() {
		return disposal;
	}
	public void setDisposal(Boolean disposal) {
		this.disposal = disposal;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public LocalDateTime getAddingDate() {
		return addingDate;
	}
	public void setAddingDate(LocalDateTime addingDate) {
		this.addingDate = addingDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTechnologicalMapName() {
		return technologicalMapName;
	}
	public void setTechnologicalMapName(String technologicalMapName) {
		this.technologicalMapName = technologicalMapName;
	}
	
}
