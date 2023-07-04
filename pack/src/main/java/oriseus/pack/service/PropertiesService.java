/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author oriseus
 */
public class PropertiesService {
    
    private PropertiesService() {}
    
    public static String getProperties(String key) {
        String value = null;
        
        try (FileInputStream fis = new FileInputStream("../pack/src/main/resources/oriseus/config/config.properties")) {
            Properties property = new Properties();
            property.load(fis);
            value = property.getProperty(key);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        
        return value;
    }
    
}
