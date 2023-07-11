package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import oriseus.pack.App;
import oriseus.pack.dto.StampWrapper;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.FilesService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.SearchService;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.WindowService;

public class UserMainController {
    
    @FXML
    TableView<StampView> tableView;
    
    @FXML
    TextField searchTextField;
    
    @FXML
    Text warningText;
    
    @FXML
    private Button searchButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button changeStorageCellButton;
    @FXML
    private Button buttonTechnicalMap;
    @FXML
    private Button setDamageButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button watchDamageButton;
    
    @FXML
    private TableColumn<StampView, String> columnName;
    @FXML
    private TableColumn<StampView, String> columnStorageCell;
    @FXML
    private TableColumn<StampView, String> columnTechnologicalMapName;
    @FXML
    private TableColumn<StampView, String> columnIsDamaged;
    @FXML
    private TableColumn<StampView, String> columnIsRepairPack;
    @FXML
    private TableColumn<StampView, String> columnIsAvailabity;
    @FXML
    private TableColumn<StampView, String> columnNotes;
            
    public static StampView stampView;
    WindowService windowService;
    App app;
    ObservableList<StampView> observableList;
    HostServices host;
    String location;
    String suffix;
    String path;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        app = new App();
        
        columnName.setCellValueFactory(new PropertyValueFactory<StampView, String>("name"));
        columnStorageCell.setCellValueFactory(new PropertyValueFactory<>("storageCell"));
        columnTechnologicalMapName.setCellValueFactory(new PropertyValueFactory<>("technologicalMapName"));
        columnIsDamaged.setCellValueFactory(new PropertyValueFactory<>("damaged"));
		columnIsRepairPack.setCellValueFactory(new PropertyValueFactory<>("repairPack"));
        columnIsAvailabity.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        StampWrapper stampWrapper = null;
       
		stampWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") + "/stamps", StampWrapper.class);
        observableList = ConvertService.convertToStampViewObservableList(stampWrapper.getList());
        tableView.setItems(observableList); 
    }
    
    @FXML
    private void setDamage(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
        if(stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
        
        windowService.openModalWindow(event, "/oriseus/pack/userSetDamage.fxml", "Детали");
        tableView.refresh();
    }
    
    @FXML
    private void changeStorageCell(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
        if(stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
        
        windowService.openModalWindow(event, "/oriseus/pack/userChangeStorageCell.fxml", "Детали");
        tableView.refresh();
    }
    
    @FXML
    private void logout() throws IOException {
        windowService.openNewWindow("/oriseus/pack/login.fxml", "Вход", logoutButton);
    }
    
    @FXML
    private void search() throws IOException {
        SearchService.search(tableView, searchTextField.getText(), observableList);
    }
    
    @FXML
    private void openTechnicalMap() throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
        if (stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
        
        File file = HttpService.getFile(PropertiesService.getProperties("ServerUrl") + "/file/technicalMap", 
        		stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapSuffix"), stampView.getName());
        FilesService.openFile(app.getHostServices(), file);        
    }        
    @FXML
    private void update() throws IOException {
    	initialize();
    }
    
    @FXML
    private void watchDamage() throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	if (stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
    	
    	if (stampView.getDamaged().equals("Да")) {
    		File damagedImage = HttpService.getFile(PropertiesService.getProperties("ServerUrl") + "/file/damagedImageOfTechnicalMap", 
            		stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapImagesSuffix"), stampView.getName());
    		FilesService.openFile(app.getHostServices(), damagedImage);
    	} else {
    		warningText.setText("На клише отсутствуют повреждения");    		
		}
    }
}
