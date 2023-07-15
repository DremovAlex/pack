/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.application.HostServices;
import javafx.collections.ObservableList;
import oriseus.pack.modelsViews.*;

/**
 *
 * @author oriseus
 */
public class FilesService {
	
	private FilesService() {}
    
    public static void openFile(HostServices host, File file) throws IOException {    	
    	File tempFile = new File(PropertiesService.getProperties("TempReportFileLocation") + file.getName());
        Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    	host.showDocument(tempFile.toString());
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public static void deleteTempFiles() {
    	File tempDirectory = new File(PropertiesService.getProperties("TempReportFileLocation"));
    	for (File file : tempDirectory.listFiles()) {
    		file.delete();
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
