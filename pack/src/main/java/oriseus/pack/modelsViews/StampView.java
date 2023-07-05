package oriseus.pack.modelsViews;

import javafx.beans.property.SimpleStringProperty;

public class StampView {

	private Integer id;
    private SimpleStringProperty name;
    private SimpleStringProperty storageCell;
    private SimpleStringProperty technologicalMapName;
    private SimpleStringProperty damaged;
    private SimpleStringProperty repairPack;
    private SimpleStringProperty availability;
    private SimpleStringProperty disposal;
    private SimpleStringProperty price;
    private SimpleStringProperty addingDate;
    private SimpleStringProperty notes;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name.get();
	}
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
	public String getStorageCell() {
		return storageCell.get();
	}
	public void setStorageCell(SimpleStringProperty storageCell) {
		this.storageCell = storageCell;
	}
	public String getTechnologicalMapName() {
		return technologicalMapName.get();
	}
	public void setTechnologicalMapName(SimpleStringProperty technologicalMapName) {
		this.technologicalMapName = technologicalMapName;
	}
	public String getDamaged() {
		return damaged.get();
	}
	public void setDamaged(SimpleStringProperty damaged) {
		this.damaged = damaged;
	}
	public String getRepairPack() {
		return repairPack.get();
	}
	public void setRepairPack(SimpleStringProperty repairPack) {
		this.repairPack = repairPack;
	}
	public String getAvailability() {
		return availability.get();
	}
	public void setAvailability(SimpleStringProperty availability) {
		this.availability = availability;
	}
	public String getDisposal() {
		return disposal.get();
	}
	public void setDisposal(SimpleStringProperty disposal) {
		this.disposal = disposal;
	}
	public String getPrice() {
		return price.get();
	}
	public void setPrice(SimpleStringProperty price) {
		this.price = price;
	}
	public String getAddingDate() {
		return addingDate.get();
	}
	public void setAddingDate(SimpleStringProperty addingDate) {
		this.addingDate = addingDate;
	}
	public String getNotes() {
		return notes.get();
	}
	public void setNotes(SimpleStringProperty notes) {
		this.notes = notes;
	}
}
