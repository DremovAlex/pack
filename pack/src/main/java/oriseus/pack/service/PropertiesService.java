/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import oriseus.pack.controllers.LoginController;

/**
 *
 * @author oriseus
 */
public class PropertiesService {
	
    private static final Logger logger = (Logger) LogManager.getLogger(PropertiesService.class);
    
    private PropertiesService() {}
    
    public static String getProperties(String key) {
        String value = null;
        
        try (FileInputStream fis = new FileInputStream("../pack/src/main/resources/oriseus/config/config.properties")) {
            Properties property = new Properties();
            property.load(fis);
            value = property.getProperty(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        
        return value;
    }
    
}
