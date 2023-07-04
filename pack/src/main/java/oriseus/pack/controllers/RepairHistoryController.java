/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import oriseus.pack.App;
import oriseus.pack.dto.StampDamageHistoryWrapper;
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
    Text nameText;
    @FXML
    Text sumText;
    
    @FXML
    TableView tableView;
    
    @FXML
    TableColumn repairDateColumn;
    @FXML
    TableColumn repairPriceColumn;
    
    @FXML
    Button takeReport;
    @FXML
    Button exitButton;
    
    WindowService windowService;
    StampView stampView;
    ObservableList<StampRepairHistoryView> observableList;
    App app;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        app = new App();
        nameText.setText(stampView.getName());
        
        repairDateColumn.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        repairPriceColumn.setCellValueFactory(new PropertyValueFactory<>("repairPrice"));
        
        StampRepairHistoryWrapper stampRepairHistoryWrapper = null;
		try {
			stampRepairHistoryWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") +
					"/stampRepairHistory/" + stampView.getName().replace(" ", "%20"), StampRepairHistoryWrapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
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
        File file = new File(PropertiesService.getProperties("TempReportFileLocation") +
        PropertiesService.getProperties("TempReportFileName") + 
        PropertiesService.getProperties("TempReportFileSuffix"));
        
        FilesService.getReportRepairHistory(observableList, file, sumText.getText(), stampView.getName());
        FilesService.openFile(app.getHostServices(), 
        
        PropertiesService.getProperties("TempReportFileLocation"),
        PropertiesService.getProperties("TempReportFileName"),
        PropertiesService.getProperties("TempReportFileSuffix"));
        
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
}
