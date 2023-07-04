package oriseus.pack.dto;

import java.time.LocalDateTime;

public class StampRepairHistoryDTO {

	private LocalDateTime repairDate;
    private Long repairPrice;
    private StampDTO stampDTO;
	
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
	public StampDTO getStampDTO() {
		return stampDTO;
	}
	public void setStampDTO(StampDTO stampDTO) {
		this.stampDTO = stampDTO;
	}
	
}
