package oriseus.pack.controllers;

import java.io.IOException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.application.HostServices;
import javafx.scene.control.TextField;
import oriseus.pack.App;
import oriseus.pack.dto.StampDTO;
import oriseus.pack.dto.StampWrapper;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.FilesService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.SearchService;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.WindowService;

public class RootMainController {
	
    @FXML
    private TableView tableView;
	
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button toRepair;
    @FXML
    private Button fromRepair;
    @FXML
    private Button buttonTechnicalMap;
    @FXML
    private Button buttonDamageHistory;
    @FXML
    private Button buttonRepairHistory; 
    @FXML
    private Button buttonExit;
    @FXML
    private Button searchButton;
    @FXML
    private Button updateButton;
    
    @FXML
    private TextField searchTextField;
    @FXML
    Text warningText;
    
    @FXML
    private TableColumn<StampView, String> columnName;
    @FXML
    private TableColumn<StampView, String> columnStorageCell;
    @FXML
    private TableColumn<StampView, String> columnTechnologicalMapName;
    @FXML
    private TableColumn<StampView, SimpleBooleanProperty> columnIsDamaged;
    @FXML
    private TableColumn<StampView, SimpleBooleanProperty> columnIsRepairPack;
    @FXML
    private TableColumn<StampView, SimpleBooleanProperty> columnIsAvailabity;
    @FXML
    private TableColumn<StampView, SimpleBooleanProperty> columnIsDisposal;
    @FXML
    private TableColumn<StampView, SimpleIntegerProperty> columnPrice; 
    @FXML
    private TableColumn<StampView, String> columnDateAdding;
    @FXML
    private TableColumn<StampView, String> columnNotes;
	
    public static StampView stampView;
    WindowService windowService;
    ObservableList<StampView> observableList;
    App app;
    HostServices host;
    String location;
    String suffix;
    String path;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        app = new App();
        
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnStorageCell.setCellValueFactory(new PropertyValueFactory<>("storageCell"));
        columnTechnologicalMapName.setCellValueFactory(new PropertyValueFactory<>("technologicalMapName"));
        columnIsDamaged.setCellValueFactory(new PropertyValueFactory<>("damaged"));
        columnIsRepairPack.setCellValueFactory(new PropertyValueFactory<>("repairPack"));
        columnIsAvailabity.setCellValueFactory(new PropertyValueFactory<>("availability"));
        columnIsDisposal.setCellValueFactory(new PropertyValueFactory<>("disposal"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnDateAdding.setCellValueFactory(new PropertyValueFactory<>("addingDate"));
        columnNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        StampWrapper stampWrapper = null;
		try {
			stampWrapper = HttpService.getObject(PropertiesService.getProperties("ServerUrl") + "/stamps", StampWrapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
        observableList = ConvertService.convertToStampViewObservableList(stampWrapper.getList());
        tableView.setItems(observableList); 
    }
	
    @FXML
    private void logout() throws IOException {
        windowService.openNewWindow("/oriseus/pack/login.fxml", "Вход", buttonExit);
    }
    
    @FXML
    private void add(ActionEvent event) throws IOException {    	
        windowService.openModalWindow(event, "/oriseus/pack/rootMainAdd.fxml", "Добавление");
        tableView.refresh();
    }
    
    @FXML
    private void edit(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	
        if(stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
        
        windowService.openModalWindow(event, "/oriseus/pack/rootMainEdit.fxml", "Редактирование");
        tableView.refresh();
    }
    
    @FXML
    private void toRepair() throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	
    	if (stampView == null) {
    		warningText.setText("Вы ничего не выбрали");
    		return;
    	}
    	
        if (stampView.getDamaged().equals("Да")) {
            stampView.setAvailability(new SimpleStringProperty("Нет"));
        } else {
        	warningText.setText("На клише отсутствуют повреждения");
        }
            
        HttpService.sendObject(ConvertService.convertToStampDTO(stampView), PropertiesService.getProperties("ServerUrl") + "/stamps/update", "POST");
           
        tableView.refresh();
    }
    
    @FXML
    private void fromRepair(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
        if (stampView != null && stampView.getAvailability().equals("Нет")) {
            windowService.openModalWindow(event, "/oriseus/pack/fromRepair.fxml", "Ввод в эксплуатацию");
            tableView.refresh();
        } else {
        	warningText.setText("Клише не в ремонте");
		}
    }
    
    @FXML
    private void delete() throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	if (stampView == null) {
    		warningText.setText("Вы ничего не выбрали");
    		return;
    	}
    	
    	HttpService.sendObject(ConvertService.convertToStampDTO(stampView), PropertiesService.getProperties("ServerUrl") + "/stamps/delete", "DELETE");
        
    	tableView.refresh();
    }
    
    @FXML
    private void openTechnicalMap() throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
        if (stampView == null) {
        	warningText.setText("Вы ничего не выбрали");
        	return;
        }
        FilesService.openFile(app.getHostServices(), PropertiesService.getProperties("TechnicalMapLocation"),
        		stampView.getTechnologicalMapName(), PropertiesService.getProperties("TechnicalMapSuffix"));
    }
    
    @FXML
    private void openDamageHistory(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	if (stampView == null) {
    		warningText.setText("Вы ничего не выбрали");
    		return;
    	}
        windowService.openModalWindow(event, "/oriseus/pack/damageHistory.fxml", "История повреждений");
    }
    
    @FXML
    private void openRepairHistory(ActionEvent event) throws IOException {
    	stampView = (StampView) tableView.getSelectionModel().getSelectedItem();
    	if (stampView == null) {
    		warningText.setText("Вы ничего не выбрали");
    		return;
    	}
        windowService.openModalWindow(event, "/oriseus/pack/repairHistory.fxml", "История ремонтов");

    }
    
    @FXML
    private void search() throws IOException {
        SearchService.search(tableView, searchTextField.getText(), observableList);
    }
    
    @FXML
    private void update() throws IOException {
    	initialize();
    }
}