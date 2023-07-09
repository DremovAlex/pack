package oriseus.packserver.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stamprepairhistory")
public class StampRepairHistory {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "repairdate")
    private LocalDateTime repairDate;
	
	@Column(name = "repairprice")
    private Long repairPrice;
	
	@ManyToOne
	@JoinColumn(name = "stamp", referencedColumnName = "id")
	private Stamp stamp;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(LocalDateTime repairDate) {
		this.repairDate = repairDate;
	}
	public Long getRepairPrice() {
		return repairPrice;
	}
	public void setRepairPrice(Long repairPrice) {
		this.repairPrice = repairPrice;
	}
	public Stamp getStamp() {
		return stamp;
	}
	public void setStamp(Stamp stamp) {
		this.stamp = stamp;
	}
}
