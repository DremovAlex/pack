package oriseus.packserver.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	@Value("${TechnicalMapFolder}")
	private String technicalMapsFolder;
	
	@Value("${TechnicalMapImagesFolder}")
	private String technicalMapsImagesFolder;
	
	@Value("${ArchiveImagesFolder}")
	private String archiveImages;
	
	@Value("${TechnicalMapImagesSuffix}")
	private String TechnicalMapImagesSuffix;
	
	public File getTechnicalMap(String fileName, String owner) {
		File file = new File(technicalMapsFolder + owner + "/" + fileName);
		return file;
	}
		
	public void saveTechnicalMap(File file, String owner) throws IOException {	
		saveFile(file, technicalMapsFolder + owner, file.getName());
	}
	
	public void deleteTechnicalMap(String owner) {
		deleteFile(technicalMapsFolder, owner);
	}
	
	public File getImageOfTechnicalMap(String fileName, String owner) {
		File file = new File(technicalMapsImagesFolder + owner + "/" + fileName);
		return file;
	}
	
	public void saveImageOfTechnicalMap(File file, String owner) throws IOException {
		saveFile(file, technicalMapsImagesFolder + owner, file.getName());
	}
	
	public void deleteImageOfTechnicalMap(String owner) {
		deleteFile(technicalMapsImagesFolder, owner);
	}
	
	public File getDamagedImageOfTechnicalMap(String fileName, String owner) {
		File directory = new File(technicalMapsImagesFolder + owner + "/damaged");
		if (!directory.exists()) return null;
		File file = new File(directory + "/" + fileName);
		return file;
	}
	
	public void saveDamagedImageOfTechnicalMap(File file, String owner) throws IOException {
		File directory = new File(technicalMapsImagesFolder + owner + "/damaged");
		if (!directory.exists()) {
			directory.mkdir();
		}

		saveFile(file, directory.toString() + "/", file.getName());
	}
	
	public boolean cleanDamagedImageOfTechnologicalMap(String fileName, String owner) {
		File directory = new File(technicalMapsImagesFolder + owner + "/damaged");
		if (!directory.exists()) {
			return false;
		}
		
		File oldFile = new File(directory + "/" + fileName);
		File newFile = new File(technicalMapsImagesFolder + owner + "/" + fileName);
		if (!oldFile.exists() || !newFile.exists()) {
			return false;
		}
		
		try {
			Files.copy(newFile.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sendDamagedImageToArchive(String owner, String dateOfSending, String fileName) {
		File directory = new File(archiveImages + owner);
		if (!directory.exists()) {
			directory.mkdir();
		}
	
		File damagedImage = new File(technicalMapsImagesFolder + owner + "/damaged/" + fileName);
		File archiveImage = new File(directory + "/" + dateOfSending + "_" + fileName);
		
		try {
			Files.copy(damagedImage.toPath(), archiveImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean cleanArchive(String owner) {
		deleteFile(archiveImages, owner);
		return true;
	}
	
	public List<File> getDamagedImagesFromArchive(String owner) {
		File directory = new File(archiveImages + owner);
		return Arrays.asList(directory.listFiles());
	}
	
	private void saveFile(File file, String path, String fileName) throws IOException {
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}

		File savingFile = new File(directory + "/" + fileName);
		Files.copy(file.toPath(), savingFile.toPath(), StandardCopyOption.REPLACE_EXISTING);	
	}
	
	private void deleteFile(String path, String owner) {
		File directory = new File(path + owner);
		
		if (!directory.exists()) return;
		
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					f.delete();
				}
			} 
			file.delete();	
		}
		directory.delete();
	}
	
}
