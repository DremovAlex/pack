/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.modelsViews;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author oriseus
 */
public class StampRepairHistoryView {
    
    private SimpleStringProperty repairDate;
    private SimpleStringProperty repairPrice;
    
	public String getRepairDate() {
		return repairDate.get();
	}
	public void setRepairDate(SimpleStringProperty repairDate) {
		this.repairDate = repairDate;
	}
	public String getRepairPrice() {
		return repairPrice.get();
	}
	public void setRepairPrice(SimpleStringProperty repairPrice) {
		this.repairPrice = repairPrice;
	}

    
}
