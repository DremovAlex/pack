package oriseus.packserver.models;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stampdamagehistory")
public class StampDamageHistory {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "dateofdamagedetection")
    private LocalDateTime dateOfDamageDetection;
    
	@Column(name = "shift")
	private Integer shift;
	
	@Column(name = "descriptionofdamage")
    private String descriptionOfDamage;
	
	@ManyToOne
	@JoinColumn(name = "stamp", referencedColumnName = "id")
	private Stamp stamp;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Stamp getStamp() {
		return stamp;
	}
	public void setStamp(Stamp stamp) {
		this.stamp = stamp;
	}	
	
}
