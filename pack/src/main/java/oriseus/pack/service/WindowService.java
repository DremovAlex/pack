/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oriseus.pack.controllers.LoginController;

public class WindowService {
    
    private Parent root;
    private Stage stage;
    
    private static final Logger logger = (Logger) LogManager.getLogger(WindowService.class);
    
    public void openNewWindow(String fxml, String label, Control control) throws IOException {
        stage = new Stage();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        root = fxmlLoader.load();
        
        stage.setScene(new Scene(root));
        stage.setTitle(label);
        
        Image image = null;
		try {
			image = new Image(getClass().getResource(File.separator + "oriseus" + File.separator + "icons" + File.separator + "windowIcon.png").toURI().toString());
		} catch (URISyntaxException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}

        stage.getIcons().add(image);        
        stage.setResizable(false);
        stage.show();
        
        stage.setOnCloseRequest(e -> {
            FilesService.deleteTempFiles();
        });
        
        Stage oldStage = (Stage) control.getScene().getWindow();
        oldStage.close();
    }

    public void openModalWindow(ActionEvent event, String fxml, String label) throws IOException {
        stage = new Stage();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        root = fxmlLoader.load();
        
        stage.setScene(new Scene(root));
        stage.setTitle(label);
        
        Image image = null;
		try {
			image = new Image(getClass().getResource(File.separator + "oriseus" + File.separator + "icons" + File.separator + "windowIcon.png").toURI().toString());
		} catch (URISyntaxException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
 
        stage.showAndWait();
        
        stage.setOnCloseRequest(e -> {
            FilesService.deleteTempFiles();
        });
    }

	public void closeWindow(Control control) {
        Stage stage = (Stage) control.getScene().getWindow();
        
        stage.setOnCloseRequest(e -> {
            FilesService.deleteTempFiles();
        });
        
        stage.close();
    }
    
}
