package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.dto.RoleDTO;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.WindowService;
import oriseus.pack.utils.ServerException;

public class LoginController {
	
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Text text;
    
    @FXML
    private VBox textField;
    
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;

    private WindowService windowService;
    private String login;
    private String password;
    
    private static final Logger logger = (Logger) LogManager.getLogger(LoginController.class);
    
    @FXML
    private void switchToMainPage() throws IOException {
        login = loginField.getText();
    	password = passwordField.getText();
    	
    	if (login == null || login.isBlank() || password == null || password.isBlank()) {
    		text.setText("Пожалуйста введите логин и пароль");
    	}
    	
    	RoleDTO roleDTO = new RoleDTO();
    	roleDTO.setName(login);
    	roleDTO.setPassword(password);
    	roleDTO.setRole("");
		
    	try {
			roleDTO = HttpService.sendAndGetObject(PropertiesService.getProperties("ServerUrl") + "/login" , roleDTO, RoleDTO.class);
		} catch (ServerException e) {
			text.setText("Неправильный логин или пароль");
			logger.warn(e.getStatusCode() + " : " + e.getMessage());
		}
    	
    	if (roleDTO.getRole().equals("root")) {
    		windowService.openNewWindow(File.separator + "oriseus"+ File.separator + "pack" + File.separator + "rootMainPage.fxml", "Главное окно", loginField);
    	} else if (roleDTO.getRole().equals("user")) {
    		windowService.openNewWindow(File.separator + "oriseus" + File.separator + "pack"+ File.separator + "userMainPage.fxml", "Главное окно", loginField);
    	} else {
    		text.setText("Ошибка входа");
    	}
    }
    
    @FXML
    private void exit() throws IOException {
    	System.exit(0);
    }
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
    }
    
}
