/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import oriseus.pack.App;
import oriseus.pack.dto.StampDamageHistoryWrapper;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.FilesService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

/**
 *
 * @author oriseus
 */
public class DamageHistoryController {
    
    @FXML
    Text nameText;
    @FXML
    Text warningText;
    
    @FXML
    TableView tableView;
    
    @FXML
    TableColumn shiftColumn;
    @FXML
    TableColumn dateOfDamageColumn;
    @FXML
    TableColumn discriptionOfDamageColumn;
    
    @FXML
    TextArea discriptionOfDamageTextArea;
    
    @FXML
    Button takeReport;
    @FXML
    Button exitButton;
    @FXML
    Button imageButton;
    
    WindowService windowService;
    StampView stampView;
    ObservableList<StampDamageHistoryView> observableList;
    StampDamageHistoryView stampDamageHistoryView;
    App app;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        app = new App();
        nameText.setText(stampView.getName());
        
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        dateOfDamageColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfDamageDetection"));
        discriptionOfDamageColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionOfDamage"));
        
        StampDamageHistoryWrapper stampDamageHistoryWrapper = null;
		
        stampDamageHistoryWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") + 
				"/stampDamageHistory/" + stampView.getName().replace(" ", "%20"), StampDamageHistoryWrapper.class);
		
        observableList = ConvertService.convertToStampDamageHistoryViewObservableList(stampDamageHistoryWrapper.getList());
        tableView.setItems(observableList); 
    }
    
    @FXML
    private void takeReport() {
        File file = new File(PropertiesService.getProperties("TempReportFileLocation") +
        					PropertiesService.getProperties("TempReportFileName") + 
        					PropertiesService.getProperties("TempReportFileSuffix"));
        
        FilesService.getReportDamageHistory(observableList, stampView.getName(), file);
        
//        FilesService.openFile(app.getHostServices(), PropertiesService.getProperties("TempReportFileLocation"),        
//        											PropertiesService.getProperties("TempReportFileName"),
//        											PropertiesService.getProperties("TempReportFileSuffix"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        
        }
        file.delete();
    }
    
    @FXML
    private void logout() throws IOException {
        windowService.closeWindow(exitButton);
    }
    
    @FXML
    private void initLabel() {
        stampDamageHistoryView = (StampDamageHistoryView) tableView.getSelectionModel().getSelectedItem();
        discriptionOfDamageTextArea.setText(stampDamageHistoryView.getDescriptionOfDamage());
    }
    
    @FXML
    private void openImage() {
    	stampDamageHistoryView = (StampDamageHistoryView) tableView.getSelectionModel().getSelectedItem();
    	
    	if (stampDamageHistoryView == null) {
    		warningText.setText("Вы ничего не выбрали");
    		return;
    	}
    	
//    	FilesService.openFile(app.getHostServices(), PropertiesService.getProperties("ArchiveOfDamagedTechnicalMapsImages") + "/" + stampView.getTechnologicalMapName() + "/",
//    			stampDamageHistoryView.getNameOfTechnicalMap(),
//    			PropertiesService.getProperties("TechnicalMapImagesSuffix"));   	
    }
}
