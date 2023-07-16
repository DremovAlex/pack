package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

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

public class RootEditController {
	
    @FXML
    private TextField nameTextField;	
    @FXML
    private TextField storageCellTextField;
    @FXML
    private TextField technologicalMapNameTextField;
    @FXML
    private TextField rubPriceTextField;
    @FXML
    private TextField kopPriceTextField;
    
    @FXML
    private TextArea notesTextArea;
    
    @FXML
    private Text warningText;
    @FXML
    private Text technicalMapText;
    @FXML
    private Text imageOfTechnicalMapText;
    
    @FXML
    private CheckBox isDamageCheckBox;
    @FXML
    private CheckBox isRepairPackCheckBox;
    @FXML
    private CheckBox isAvailabilityCheckBox;
    @FXML
    private CheckBox isDisposalCheckBox;
	
    @FXML
    private Button editButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button technicalMapButton;
    @FXML
    private Button imageOfTechnicalMapButton;
	
    private StampView stampView;
    private WindowService windowService;
    private File technicalMap;
    private File imageOftechnicalMap;
        
    private static final Logger logger = (Logger) LogManager.getLogger(RootEditController.class);
    
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

        technicalMap = HttpService.getFile(PropertiesService.getProperties("ServerUrl") + "/file/technicalMap", 
        		stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapSuffix"), stampView.getName());
        imageOftechnicalMap = HttpService.getFile(PropertiesService.getProperties("ServerUrl") + "/file/imageOfTechnicalMap", 
        		stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapImagesSuffix"), stampView.getName());

        technicalMapText.setText(technicalMap.getName());
        imageOfTechnicalMapText.setText(imageOftechnicalMap.getName());
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
    	Integer id = stampView.getId();
    	
    	String oldName = stampView.getName();
    	
        stampView = new StampView();
        stampView.setId(id);
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
                
        HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stamps/update", ConvertService.convertToStampDTO(stampView));
        
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/changeTechnicalMap", technicalMap, oldName, nameTextField.getText());   
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/changeImageOfTechnicalMap", imageOftechnicalMap, oldName, nameTextField.getText());
        
        windowService.closeWindow(editButton);
    }
	
    @FXML
    private void exit() throws IOException {
    	windowService.closeWindow(exitButton);
    }
	
    @FXML
    private void addImage() {   	    	
    	if (nameTextField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер клише");
    		return;
    	}
    	
    	if (technologicalMapNameTextField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер технологической карты");
    		return;
    	}
    	
    	FileChooser fileChooser = new FileChooser();   	
    	File selectedFile = fileChooser.showOpenDialog(technicalMapButton.getScene().getWindow());   	
    	
    	if (selectedFile == null) return;
    	
    	imageOftechnicalMap = new File(PropertiesService.getProperties("TempReportFileLocation") + technologicalMapNameTextField.getText() + PropertiesService.getProperties("TechnicalMapImagesSuffix"));
		try {
			Files.copy(selectedFile.toPath(), imageOftechnicalMap.toPath(), StandardCopyOption.REPLACE_EXISTING);
			imageOfTechnicalMapText.setText(technicalMap.getName());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
    }
    
    @FXML
    private void addTechnicalMap() {  	
    	if (nameTextField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер клише");
    		return;
    	}
    	
    	if (technologicalMapNameTextField.getText().isBlank()) {
    		warningText.setText("Пожалуйста, укажите номер технологической карты");
    		return;
    	}
    	
    	FileChooser fileChooser = new FileChooser();    	
    	File selectedFile = fileChooser.showOpenDialog(technicalMapButton.getScene().getWindow());   	
    	
    	if (selectedFile == null) return;
    	
    	technicalMap = new File(PropertiesService.getProperties("TempReportFileLocation") + technologicalMapNameTextField.getText() + PropertiesService.getProperties("TechnicalMapSuffix"));
    	try {
			Files.copy(selectedFile.toPath(), technicalMap.toPath(), StandardCopyOption.REPLACE_EXISTING);
			technicalMapText.setText(technicalMap.getName());
    	} catch (IOException e) {
    		logger.error(e.getMessage());
			e.printStackTrace();
		}   	
    }
}
