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
import oriseus.pack.service.ConvertService;
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

    WindowService windowService;
    String login;
    String password;
    
    @FXML
    private void switchToMainPage(ActionEvent event) throws IOException {
        login = loginField.getText();
    	password = passwordField.getText();

        if (login.equals(PropertiesService.getProperties("RootLogin")) && password.equals(PropertiesService.getProperties("RootPassword"))) {
            windowService.openNewWindow("/oriseus/pack/rootMainPage.fxml", "Главное окно", loginField);
        } else if (login.equals(PropertiesService.getProperties("UserLogin")) && password.equals(PropertiesService.getProperties("UserLogin"))) {
            windowService.openNewWindow("/oriseus/pack/userMainPage.fxml", "Главное окно", loginField);
        } else {
            text.setText("Вы ввели не правильный логин или пароль.");
        }
    }
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
    }
    
}
