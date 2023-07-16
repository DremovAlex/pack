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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import oriseus.pack.App;
import oriseus.pack.dto.StampRepairHistoryDTO;
import oriseus.pack.dto.StampRepairHistoryWrapper;
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
public class RepairHistoryController {
    
    @FXML
    private Text nameText;
    @FXML
    private Text sumText;
    
    @FXML
    private TableView<StampRepairHistoryView> tableView;
    
    @FXML
    private TableColumn<String, String> repairDateColumn;
    @FXML
    private TableColumn<String, String> repairPriceColumn;
    
    @FXML
    private Button takeReport;
    @FXML
    private Button exitButton;
    
    private WindowService windowService;
    private StampView stampView;
    private ObservableList<StampRepairHistoryView> observableList;
    private App app;
    
    private static final Logger logger = (Logger) LogManager.getLogger(RepairHistoryController.class);
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        app = new App();
        nameText.setText(stampView.getName());
        
        repairDateColumn.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        repairPriceColumn.setCellValueFactory(new PropertyValueFactory<>("repairPrice"));
        
        StampRepairHistoryWrapper stampRepairHistoryWrapper = null;
		
        stampRepairHistoryWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") +
				"/stampRepairHistory/" + stampView.getName().replace(" ", "%20"), StampRepairHistoryWrapper.class);
        
		observableList = ConvertService.convertToStampRepairHistoryViewObservableList(stampRepairHistoryWrapper.getList());
        
		Long sum = 0L;
		
		for (StampRepairHistoryDTO stampRepairHistoryDTO : stampRepairHistoryWrapper.getList()) {
			sum += stampRepairHistoryDTO.getRepairPrice();
		}
		
        sumText.setText(ConvertService.convertLongToString(sum));
        tableView.setItems(observableList);
    }
    
    @FXML
    private void takeReport() {
    	File report = new File(PropertiesService.getProperties("TempReportFileLocation") +
				 			   PropertiesService.getProperties("TempReportFileName") + 
				 			   PropertiesService.getProperties("TempReportFileSuffix"));

		FilesService.getReportRepairHistory(observableList, report, sumText.getText(), stampView.getName());
		  
		try {
			FilesService.openFile(app.getHostServices(), report);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		report.delete();
    }
    
    @FXML
    private void logout() throws IOException {
        windowService.closeWindow(exitButton);
    }
}
