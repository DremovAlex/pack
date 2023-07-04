package oriseus.packserver.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stamp")
public class Stamp implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "storagecell")
    private String storageCell;
	
	@Column(name = "technologicalmapname")
    private String technologicalMapName;
	
	@Column(name = "damaged")
    private Boolean damaged;
	
	@Column(name = "repairpack")
    private Boolean repairPack;
	
	@Column(name = "availability")
    private Boolean availability;
	
	@Column(name = "disposal")
    private Boolean disposal;
	
	@Column(name = "price")
    private Long price;
	
	@Column(name = "addingdate")
    private LocalDateTime addingDate;
	
	@Column(name = "notes")
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
