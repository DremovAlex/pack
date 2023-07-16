/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private Text nameText;
    @FXML
    private Text warningText;
    
    @FXML
    private TableView<StampDamageHistoryView> tableView;
    
    @FXML
    private TableColumn<String, String> shiftColumn;
    @FXML
    private TableColumn<String, String> dateOfDamageColumn;
    @FXML
    private TableColumn<String, String> discriptionOfDamageColumn;
    
    @FXML
    private TextArea discriptionOfDamageTextArea;
    
    @FXML
    private Button takeReport;
    @FXML
    private Button exitButton;
    @FXML
    private Button imageButton;
    
    private WindowService windowService;
    private StampView stampView;
    private ObservableList<StampDamageHistoryView> observableList;
    private File[] arrayOfImagesFromArchive;
    private StampDamageHistoryView stampDamageHistoryView;
    private App app;
    
    private static final Logger logger = (Logger) LogManager.getLogger(DamageHistoryController.class);
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        app = new App();
        nameText.setText(stampView.getName());
        
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        dateOfDamageColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfDamageDetection"));
        discriptionOfDamageColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionOfDamage"));
		
        StampDamageHistoryWrapper stampDamageHistoryWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") + 
				"/stampDamageHistory/" + stampView.getName().replace(" ", "%20"), StampDamageHistoryWrapper.class);
		
        observableList = ConvertService.convertToStampDamageHistoryViewObservableList(stampDamageHistoryWrapper.getList());
        arrayOfImagesFromArchive = HttpService.getFiles(PropertiesService.getProperties("ServerUrl") + "/file/fromArchive",
				stampView.getName());
        
        tableView.setItems(observableList); 
    }
    
    @FXML
    private void takeReport() {
        File report = new File(PropertiesService.getProperties("TempReportFileLocation") +
        					   PropertiesService.getProperties("TempReportFileName") + 
        					   PropertiesService.getProperties("TempReportFileSuffix"));
        
        FilesService.getReportDamageHistory(observableList, stampView.getName(), report);
               
        try {
        	FilesService.openFile(app.getHostServices(), report);
        } catch (IOException e) {
        	logger.error(e.getMessage());
        	e.printStackTrace();
        }
        report.delete();
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
    	
    	for (File file : arrayOfImagesFromArchive) {
    		if (file.getName().startsWith(ConvertService.convertToStampDamageHistoryDTO(stampDamageHistoryView).getDateOfDamageDetection().toString())) {
    			try {
					FilesService.openFile(app.getHostServices(), file);
					return;
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
    		}
    	}
    }
    
    @FXML
    public void exitApplication() {
    	Stage primaryStage = (Stage) imageButton.getScene().getWindow();
    	primaryStage.setOnCloseRequest(e -> {
            System.out.println("onCloseRequest handler called!");
        });
    }
}
