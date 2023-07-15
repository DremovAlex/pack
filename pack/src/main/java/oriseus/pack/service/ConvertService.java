package oriseus.pack.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oriseus.pack.dto.StampDTO;
import oriseus.pack.dto.StampDamageHistoryDTO;
import oriseus.pack.dto.StampRepairHistoryDTO;
import oriseus.pack.modelsViews.StampDamageHistoryView;
import oriseus.pack.modelsViews.StampRepairHistoryView;
import oriseus.pack.modelsViews.StampView;

public class ConvertService {
	  
	public ConvertService() {}

	public static StampView convertToStampView(StampDTO stampDTO) {
		StampView stampView = new StampView();
		stampView.setId(stampDTO.getId());
		stampView.setName(new SimpleStringProperty(stampDTO.getName()));
		stampView.setStorageCell(new SimpleStringProperty(stampDTO.getStorageCell()));
		stampView.setTechnologicalMapName(new SimpleStringProperty(stampDTO.getTechnologicalMapName()));
		stampView.setDamaged(new SimpleStringProperty(convertBooleanToString(stampDTO.isDamaged())));
		stampView.setRepairPack(new SimpleStringProperty(convertBooleanToString(stampDTO.isRepairPack())));
		stampView.setAvailability(new SimpleStringProperty(convertBooleanToString(stampDTO.isAvailability())));
		stampView.setDisposal(new SimpleStringProperty(convertBooleanToString(stampDTO.isDisposal())));
		stampView.setPrice(new SimpleStringProperty(convertLongToString(stampDTO.getPrice())));
		stampView.setAddingDate(new SimpleStringProperty(convertLocalDateTimeStringToString(stampDTO.getAddingDate())));
		stampView.setNotes(new SimpleStringProperty(stampDTO.getNotes()));
		return stampView;
	}
	
	public static StampDTO convertToStampDTO(StampView stampView) {
		StampDTO stampDTO = new StampDTO();
		stampDTO.setId(stampView.getId());
		stampDTO.setName(stampView.getName());
		stampDTO.setStorageCell(stampView.getStorageCell());
		stampDTO.setTechnologicalMapName(stampView.getTechnologicalMapName());
		stampDTO.setDamaged(convertStringToBoolean(stampView.getDamaged()));
		stampDTO.setRepairPack(convertStringToBoolean(stampView.getRepairPack()));
		stampDTO.setAvailability(convertStringToBoolean(stampView.getAvailability()));
		stampDTO.setDisposal(convertStringToBoolean(stampView.getDisposal()));
		stampDTO.setPrice(convertStringToLong(stampView.getPrice()));
		stampDTO.setAddingDate(convertStringToLocalDateTime(stampView.getAddingDate()));
		stampDTO.setNotes(stampView.getNotes());
		return stampDTO;
	}
	
	public static StampDamageHistoryView convertToStampDamageHistoryView(StampDamageHistoryDTO stampDamageHistoryDTO) {
		StampDamageHistoryView stampDamageHistoryView = new StampDamageHistoryView();
		stampDamageHistoryView.setDateOfDamageDetection(new SimpleStringProperty(convertLocalDateTimeStringToString(stampDamageHistoryDTO.getDateOfDamageDetection())));
		stampDamageHistoryView.setShift(new SimpleStringProperty(stampDamageHistoryDTO.getShift().toString()));
		stampDamageHistoryView.setDescriptionOfDamage(new SimpleStringProperty(stampDamageHistoryDTO.getDescriptionOfDamage()));
		stampDamageHistoryView.setNameOfTechnicalMap(stampDamageHistoryDTO.getNameOfTechnicalMap());
		stampDamageHistoryView.setStampDTO(stampDamageHistoryDTO.getStampDTO());
		return stampDamageHistoryView;
	}
	
	public static StampDamageHistoryDTO convertToStampDamageHistoryDTO(StampDamageHistoryView stampDamageHistoryView) {
		StampDamageHistoryDTO stampDamageHistoryDTO = new StampDamageHistoryDTO();
		stampDamageHistoryDTO.setDateOfDamageDetection(convertStringToLocalDateTime(stampDamageHistoryView.getDateOfDamageDetection()));
		stampDamageHistoryDTO.setShift(Integer.parseInt(stampDamageHistoryView.getShift()));
		stampDamageHistoryDTO.setDescriptionOfDamage(stampDamageHistoryView.getDescriptionOfDamage());
		stampDamageHistoryDTO.setNameOfTechnicalMap(stampDamageHistoryView.getNameOfTechnicalMap());
		stampDamageHistoryDTO.setStampDTO(stampDamageHistoryView.getStampDTO());
		return stampDamageHistoryDTO;
	}
	
	public static StampRepairHistoryView convertToStampRepairHistoryView(StampRepairHistoryDTO stampRepairHistoryDTO) {
		StampRepairHistoryView stampRepairHistoryView = new StampRepairHistoryView();
		stampRepairHistoryView.setRepairDate(new SimpleStringProperty(convertLocalDateTimeStringToString(stampRepairHistoryDTO.getRepairDate())));
		stampRepairHistoryView.setRepairPrice(new SimpleStringProperty(convertLongToString(stampRepairHistoryDTO.getRepairPrice())));
		return stampRepairHistoryView;
	}
	
	public static StampRepairHistoryDTO convertToStampRepairHistoryDTO(StampRepairHistoryView stampRepairHistoryView) {
		StampRepairHistoryDTO stampRepairHistoryDTO = new StampRepairHistoryDTO();
		stampRepairHistoryDTO.setRepairDate(convertStringToLocalDateTime(stampRepairHistoryView.getRepairPrice()));
		stampRepairHistoryDTO.setRepairPrice(convertStringToLong(stampRepairHistoryView.getRepairPrice()));
		return stampRepairHistoryDTO;
	}
	
	public static ObservableList<StampView> convertToStampViewObservableList(List<StampDTO> list) {
		ObservableList<StampView> observableList = FXCollections.observableArrayList();
		for (StampDTO stampDTO : list) {
			observableList.add(convertToStampView(stampDTO));
		}
		return observableList;
	}
	
	public static List<StampDTO> convertToStampDTOList(ObservableList<StampView> observableList) {
		List<StampDTO> list = new ArrayList<StampDTO>();
		for (StampView stampView : observableList) {
			list.add(convertToStampDTO(stampView));
		}
		return list;
	}
	
	public static ObservableList<StampDamageHistoryView> convertToStampDamageHistoryViewObservableList(List<StampDamageHistoryDTO> list) {
		ObservableList<StampDamageHistoryView> observableList = FXCollections.observableArrayList();
		for (StampDamageHistoryDTO stampDamageHistoryDTO : list) {
			observableList.add(convertToStampDamageHistoryView(stampDamageHistoryDTO));
		}
		return observableList;
	}
	
	public static ObservableList<StampRepairHistoryView> convertToStampRepairHistoryViewObservableList(List<StampRepairHistoryDTO> list) {
		ObservableList<StampRepairHistoryView> observableList = FXCollections.observableArrayList();
		for (StampRepairHistoryDTO stampRepairHistoryDTO : list) {
			observableList.add(convertToStampRepairHistoryView(stampRepairHistoryDTO));
		}
		return observableList;
	}
	
	public static String convertBooleanToString(boolean b) {
		if (b) {
			return "Да";
		} else {
			return "Нет";
		}
	}
	
	public static boolean convertStringToBoolean(String s) {
		if (s.equals("Да")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String convertLongToString(Long l) {
		Long rub = l / 100;
		Long kop = l % 100;
		return rub + " руб. " + kop + " коп.";
	}
	
	public static Long convertStringToLong(String s) {
		String[] arr = s.split(" ");
		
		String rub = arr[0];
		String kop = arr[2];
		
		if (kop.length() == 1) {
			kop = "0" + kop;
		}
		
		String sum = rub + kop;		
		return Long.parseLong(sum);
	}
	
	public static String convertStringToRub(String string) {
		String[] arr = string.split(" ");		
		String rub = arr[0];
		return rub;
	}
	
	public static String convertStringToKop(String string) {
		String[] arr = string.split(" ");
		String kop = arr[2];
		if (kop.length() == 1) {
			kop = "0" + kop;
		} else if (kop.isEmpty()) {
			kop = "00";
		}
		return kop;
	}
	
	public static String convertLocalDateTimeStringToString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss  dd.MM.uu"));
	}
	
	public static LocalDateTime convertStringToLocalDateTime(String s) {
		return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("HH:mm:ss  dd.MM.uu"));
	}
}
