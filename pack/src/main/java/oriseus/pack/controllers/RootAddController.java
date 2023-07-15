package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

public class RootAddController {

    @FXML
    private Button addButton;	
    @FXML
    private Button exitButton;
    @FXML
    private Button addImageButton;
    @FXML
    private Button addTechnicalMapButton;
	
	
    @FXML
    private TextField nameField;
    @FXML
    private TextField storageCellField;
    @FXML
    private TextField technologicalMapNameField;
    @FXML
    private TextField rubPriceTextField;
    @FXML
    private TextField kopPriceTextField;
    
    @FXML
    private CheckBox isDamageCheckBox;
    @FXML
    private CheckBox isRepairPackCheckBox;
    @FXML
    private CheckBox isAvailabilityCheckBox;
    @FXML
    private CheckBox isDisposalCheckBox;
    
    @FXML
    private TextArea notesTextArea;
    
    @FXML
    private Text warningText;
    @FXML
    private Text technicalMapText;
    @FXML
    private Text technicalMapImageText;
        
    private WindowService windowService;
    private File technicalMapFile;
    private File technicalMapImageFile;
        
    @FXML
    private void initialize() {
        windowService = new WindowService();
    }
	
    @FXML
    private void add() throws IOException {
    	String rub = rubPriceTextField.getText();
    	String kop = kopPriceTextField.getText();
    	
    	if (rub == null || rub.isEmpty()) {
    		rub = "0";
    	}
    	
    	if (kop == null || kop.isEmpty()) {
    		kop = "00";
    	}
    	
    	if (nameField.getText().isEmpty()) {
    		warningText.setText("Пожалуйста, введите номер клише");
    		return;
    	}
    	try {
			Integer.parseInt(rub);
			Integer.parseInt(kop);
		} catch (Exception e) {
			warningText.setText("Пожалуйста укажите коректную сумму");
			return;
		}
    	
    	if (technicalMapFile == null || technicalMapImageFile == null) {
    		warningText.setText("Вы не выбрали файл тех. карты или изображение тех. карты");
    		return;
    	}
    	   	
    	Long price = Long.parseLong(rub + kop);
    	
        StampView stampView = new StampView();
        stampView.setName(new SimpleStringProperty(nameField.getText()));
        stampView.setStorageCell(new SimpleStringProperty(storageCellField.getText()));
        stampView.setTechnologicalMapName(new SimpleStringProperty(technologicalMapNameField.getText()));
        stampView.setDamaged(new SimpleStringProperty(ConvertService.convertBooleanToString(isDamageCheckBox.isSelected())));
        stampView.setRepairPack(new SimpleStringProperty(ConvertService.convertBooleanToString(isRepairPackCheckBox.isSelected())));
        stampView.setAvailability(new SimpleStringProperty(ConvertService.convertBooleanToString(isAvailabilityCheckBox.isSelected())));
        stampView.setDisposal(new SimpleStringProperty(ConvertService.convertBooleanToString(isDisposalCheckBox.isSelected())));
        stampView.setPrice(new SimpleStringProperty(ConvertService.convertLongToString(price)));
        stampView.setAddingDate(new SimpleStringProperty(ConvertService.convertLocalDateTimeStringToString(LocalDateTime.now())));
        stampView.setNotes(new SimpleStringProperty(notesTextArea.getText()));
		
        HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stamps/addNewStamp", ConvertService.convertToStampDTO(stampView));        
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/technicalMap", technicalMapFile, nameField.getText());   
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/imageOfTechnicalMap", technicalMapImageFile, nameField.getText());
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/damagedImageOfTechnicalMap", technicalMapImageFile, nameField.getText());
        
        windowService.closeWindow(addButton);
    }
    
    @FXML
    private void addImage() {   	    	
    	if (nameField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер клише");
    		return;
    	}
    	
    	if (technologicalMapNameField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер технологической карты");
    		return;
    	}
    	
    	FileChooser fileChooser = new FileChooser();   	
    	File selectedFile = fileChooser.showOpenDialog(addTechnicalMapButton.getScene().getWindow());   	
    	
    	if (selectedFile == null) return;
    	
    	technicalMapImageFile = new File(PropertiesService.getProperties("TempReportFileLocation") + technologicalMapNameField.getText() + PropertiesService.getProperties("TechnicalMapImagesSuffix"));
		try {
			Files.copy(selectedFile.toPath(), technicalMapImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			technicalMapImageText.setText(technicalMapImageFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void addTechnicalMap() {  	
    	if (nameField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер клише");
    		return;
    	}
    	
    	if (technologicalMapNameField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер технологической карты");
    		return;
    	}
    	
    	FileChooser fileChooser = new FileChooser();    	
    	File selectedFile = fileChooser.showOpenDialog(addTechnicalMapButton.getScene().getWindow());   	
    	
    	if (selectedFile == null) return;
    	
    	technicalMapFile = new File(PropertiesService.getProperties("TempReportFileLocation") + technologicalMapNameField.getText() + PropertiesService.getProperties("TechnicalMapSuffix"));
    	try {
			Files.copy(selectedFile.toPath(), technicalMapFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			technicalMapText.setText(technicalMapFile.getName());
    	} catch (IOException e) {
			e.printStackTrace();
		}   	
    }
	
    @FXML
    private void exit() throws IOException {
    	windowService.closeWindow(exitButton);
    }
}
