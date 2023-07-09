/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import oriseus.pack.modelsViews.*;

/**
 *
 * @author oriseus
 */
public class FilesService {
	
	private FilesService() {}
    
    public static void openFile(HostServices host, String location, String fileName, String suffix) {
        String path = location + fileName + suffix;
        Path partPath = Paths.get(path);
        host.showDocument(partPath.toUri().toString());
    }
    
    // Path - location + name of stamp + "/damaged/" + name of stamp + "_damaged" + suffix 
    public static void prepairToSetDamageInImage(String location, String fileName, String suffix) throws IOException {
    	File directory = new File(location + fileName + "/damaged");
    	if (!directory.exists()) {
    		directory.mkdir();
    	}
    	
    	File damagedImage = new File(location + fileName + "/damaged/" + fileName + "_damaged" + suffix);
    	if (!damagedImage.exists()) {
    		File image = new File(location + fileName + "/" + fileName + suffix);   		
    		Files.copy(image.toPath(), damagedImage.toPath());
    	}
    }
/*
 * Send image with damage to archive. String fileName format: technicalMapName + ":" + LocalDateTime
 */
    public static void sendImageToArchive(String technicalMapName, String fileName) throws IOException {
    	File directory = new File(PropertiesService.getProperties("ArchiveOfDamagedTechnicalMapsImages") + technicalMapName);
    	if (!directory.exists()) {
    		directory.mkdir();
    	}
    	
    	File damagedImage = new File(PropertiesService.getProperties("ArchiveOfDamagedTechnicalMapsImages") + technicalMapName + "/" +
    								fileName + PropertiesService.getProperties("TechnicalMapImagesSuffix"));
    	if (!damagedImage.exists()) {		
    		File image = new File(PropertiesService.getProperties("TechnicalMapImagesLocation") + technicalMapName + "/damaged/" + technicalMapName + "_damaged" + 
    								PropertiesService.getProperties("TechnicalMapImagesSuffix"));
    		Files.copy(image.toPath(), damagedImage.toPath());
    	}   	
    }
    
    public static void deleteDamagedTechnicalMapImage(String technicalMapName) {
    	File deleteDirectory = new File(PropertiesService.getProperties("TechnicalMapImagesLocation") + technicalMapName + "/damaged");
    	if (!deleteDirectory.exists()) {
    		return;
    	} else {
    		for (File file : deleteDirectory.listFiles()) {
    			file.delete();
    		}
    		deleteDirectory.delete();
    	}    	
	}
    
    public static void deleteStampImagesFromArchive(String technicalMapName) {
    	File deleteDirectory = new File(PropertiesService.getProperties("ArchiveOfDamagedTechnicalMapsImages") + technicalMapName);
    	if (!deleteDirectory.exists()) {
    		return;
    	} else {
    		for (File file : deleteDirectory.listFiles()) {
    			file.delete();
    		}
    		deleteDirectory.delete();
    	}    	
    }
    
    public static void getReportDamageHistory(ObservableList<StampDamageHistoryView> list, String stampName, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(" *** " + stampName + " *** " + "\n" + "\n");
            for (StampDamageHistoryView stampDamageHistoryDTO : list) {
                String line = "Дата: " + stampDamageHistoryDTO.getDateOfDamageDetection() + "\n" +
                        "Смена: " + stampDamageHistoryDTO.getShift() + "\n" +
                        "Описание повреждений: " + stampDamageHistoryDTO.getDescriptionOfDamage() + "\n";
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException ex) {
            System.out.println("File don't created!");
        }
    }
    
    public static void getReportRepairHistory(ObservableList<StampRepairHistoryView> list, File file, String sum, String stampName) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(" *** " + stampName + " *** " + "\n" + "\n");
            writer.write("Общая сумма ремонтов: " + sum + "\n" + "\n");
            for (StampRepairHistoryView stampRepairHistoryDTO : list) {
                String line = "Дата: " + stampRepairHistoryDTO.getRepairDate() + "\n" +
                        "Цена ремонта: " + stampRepairHistoryDTO.getRepairPrice() + "\n";
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException ex) {
            System.out.println("File don't created!");
        }
    }
}
