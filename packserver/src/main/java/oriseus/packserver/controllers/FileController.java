package oriseus.packserver.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.dto.StampDamageHistoryDTO;
import oriseus.packserver.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	
	private final FileService fileService;
	
	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@GetMapping("/technicalMap")
	public ResponseEntity<File> getTechnicalMap(@RequestHeader("fileName") String fileName,
												@RequestHeader("owner") String owner) {		
		File file = fileService.getTechnicalMap(fileName, owner);	
		
		if (file == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}		
	}
	
	@PostMapping("/technicalMap")
	public ResponseEntity<HttpStatus> postTechnicalMap(@RequestBody File file,
													   @RequestHeader("owner") String owner) {
				
		try {
			fileService.saveTechnicalMap(file, owner);
		} catch (IOException e) {	
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/imageOfTechnicalMap")
	public ResponseEntity<File> getImageOfTechnicalMap(@RequestHeader("fileName") String fileName,
													   @RequestHeader("owner") String owner) {
		
		File file = fileService.getImageOfTechnicalMap(fileName, owner);	
		
		if (file == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}
	}
	
	@PostMapping("/imageOfTechnicalMap")
	public ResponseEntity<HttpStatus> postImageOfTechnicalMap(@RequestBody File file,
			   												  @RequestHeader("owner") String owner) {
		
		try {
			fileService.saveImageOfTechnicalMap(file, owner);
		} catch (IOException e) {	
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);		
	}
	
	@GetMapping("/damagedImageOfTechnicalMap")
	public ResponseEntity<File> getDamagedImageOfTechnicalMap(@RequestHeader("fileName") String fileName,
			   												  @RequestHeader("owner") String owner) {
		
		File file = fileService.getDamagedImageOfTechnicalMap(fileName, owner);
		
		if (file == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}
	}
	
	@PostMapping("/damagedImageOfTechnicalMap")
	public ResponseEntity<HttpStatus> postDamagedImageOfTechnicalMap(@RequestBody File file,
				  													 @RequestHeader("owner") String owner) {
		
		try {
			fileService.saveDamagedImageOfTechnicalMap(file, owner);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/fromRepair")
	public ResponseEntity<HttpStatus> fromRepair(@RequestHeader("fileName") String fileName,
										   		 @RequestHeader("owner") String owner) {
		
		boolean isSuccess = fileService.cleanDamagedImageOfTechnologicalMap(fileName, owner);
		if (!isSuccess) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(HttpStatus.OK);
		}
	}
	
	@GetMapping("/fromArchive")
	public ResponseEntity<List<File>> getArchiveImages(@RequestHeader("owner") String owner) {
		
		List<File> listOfFileFromArchive = fileService.getDamagedImagesFromArchive(owner);
		
		if (listOfFileFromArchive == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(listOfFileFromArchive, HttpStatus.OK);
		}
	}
	
}
