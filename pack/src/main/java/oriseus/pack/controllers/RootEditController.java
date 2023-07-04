package oriseus.pack.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

public class RootEditController {
	
    @FXML
    TextField nameTextField;	
    @FXML
    TextField storageCellTextField;
    @FXML
    TextField technologicalMapNameTextField;
    @FXML
    TextField rubPriceTextField;
    @FXML
    TextField kopPriceTextField;
    
    @FXML
    TextArea notesTextArea;
    
    @FXML
    Text warningText;
    
    @FXML
    CheckBox isDamageCheckBox;
    @FXML
    CheckBox isRepairPackCheckBox;
    @FXML
    CheckBox isAvailabilityCheckBox;
    @FXML
    CheckBox isDisposalCheckBox;
	
    @FXML
    Button editButton;
    @FXML
    Button exitButton;
	
    StampView stampView;
    WindowService windowService;
        
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        
        nameTextField.setText(stampView.getName());
        storageCellTextField.setText(stampView.getStorageCell());
        technologicalMapNameTextField.setText(stampView.getTechnologicalMapName());
        notesTextArea.setText(stampView.getNotes());
        isDamageCheckBox.setSelected(ConvertService.convertStringToBoolean(stampView.getDamaged()));
        isRepairPackCheckBox.setSelected(ConvertService.convertStringToBoolean(stampView.getRepairPack()));
        isAvailabilityCheckBox.setSelected(ConvertService.convertStringToBoolean(stampView.getAvailability()));
        isDisposalCheckBox.setSelected(ConvertService.convertStringToBoolean(stampView.getDisposal()));
        rubPriceTextField.setText(ConvertService.convertStringToRub(stampView.getPrice()));
        kopPriceTextField.setText(ConvertService.convertStringToKop(stampView.getPrice()));
    }
	
    @FXML
    private void edit() throws IOException {
    	
    	if (nameTextField.getText().isEmpty()) {
    		warningText.setText("Пожалуйста, введите номер клише");
    	}
    	
    	String rub = rubPriceTextField.getText();
    	String kop = kopPriceTextField.getText();
    	
    	try {
			Integer.parseInt(rub);
			Integer.parseInt(kop);
		} catch (Exception e) {
			warningText.setText("Пожалуйста укажите коректную сумму");
			return;
		}
    	
    	if (rub == null || rub.isEmpty()) {
    		rub = "0";
    	}
    	
    	if (kop == null || kop.isEmpty()) {
    		kop = "00";
    	}
    	
    	Long price = Long.parseLong(rub + kop);
    	
        stampView = new StampView();
        stampView.setName(new SimpleStringProperty(nameTextField.getText()));
        stampView.setStorageCell(new SimpleStringProperty(storageCellTextField.getText()));
        stampView.setTechnologicalMapName(new SimpleStringProperty(technologicalMapNameTextField.getText()));
        stampView.setDamaged(new SimpleStringProperty(ConvertService.convertBooleanToString(isDamageCheckBox.isSelected())));
        stampView.setRepairPack(new SimpleStringProperty(ConvertService.convertBooleanToString(isRepairPackCheckBox.isSelected())));
        stampView.setAvailability(new SimpleStringProperty(ConvertService.convertBooleanToString(isAvailabilityCheckBox.isSelected())));
        stampView.setDisposal(new SimpleStringProperty(ConvertService.convertBooleanToString(isDisposalCheckBox.isSelected())));
        stampView.setPrice(new SimpleStringProperty(ConvertService.convertLongToString(price)));
        stampView.setAddingDate(new SimpleStringProperty(ConvertService.convertLocalDateTimeStringToString(LocalDateTime.now())));
        stampView.setNotes(new SimpleStringProperty(notesTextArea.getText()));
        		
        HttpService.sendObject(ConvertService.convertToStampDTO(stampView), PropertiesService.getProperties("ServerUrl") + "/stamps/update", "POST");
        
        windowService.closeWindow(editButton);
    }
	
    @FXML
    private void exit() throws IOException {
    	windowService.closeWindow(exitButton);
    }
	
	
}
