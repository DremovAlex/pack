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
public class StampDamageHistoryView {
    
    private SimpleStringProperty dateOfDamageDetection;
    private SimpleStringProperty shift;
    private SimpleStringProperty descriptionOfDamage;
    
	public String getDateOfDamageDetection() {
		return dateOfDamageDetection.get();
	}
	public void setDateOfDamageDetection(SimpleStringProperty dateOfDamageDetection) {
		this.dateOfDamageDetection = dateOfDamageDetection;
	}
	public String getShift() {
		return shift.get();
	}
	public void setShift(SimpleStringProperty shift) {
		this.shift = shift;
	}
	public String getDescriptionOfDamage() {
		return descriptionOfDamage.get();
	}
	public void setDescriptionOfDamage(SimpleStringProperty descriptionOfDamage) {
		this.descriptionOfDamage = descriptionOfDamage;
	}

}
