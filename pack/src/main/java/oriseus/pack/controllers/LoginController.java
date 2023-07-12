package oriseus.pack.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.dto.RoleDTO;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.WindowService;

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
    private Button primaryButton;

    private WindowService windowService;
    private String login;
    private String password;
    
    @FXML
    private void switchToMainPage(ActionEvent event) throws IOException {
        login = loginField.getText();
    	password = passwordField.getText();
    	
    	if (login == null || login.isBlank() || password == null || password.isBlank()) {
    		text.setText("Пожалуйста введите логин и пароль");
    	}
    	
    	RoleDTO roleDTO = new RoleDTO();
    	roleDTO.setName(login);
    	roleDTO.setPassword(password);
    	roleDTO.setRole("");

    	roleDTO = HttpService.sendAndGetObject(PropertiesService.getProperties("ServerUrl") + "/login" , roleDTO, RoleDTO.class);
    	
    	if (roleDTO.getRole().equals("root")) {
    		windowService.openNewWindow("/oriseus/pack/rootMainPage.fxml", "Главное окно", loginField);
    	} else if (roleDTO.getRole().equals("user")) {
    		windowService.openNewWindow("/oriseus/pack/userMainPage.fxml", "Главное окно", loginField);
    	} else {
    		text.setText("Ошибка входа");
    	}
    }
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
    }
    
}
