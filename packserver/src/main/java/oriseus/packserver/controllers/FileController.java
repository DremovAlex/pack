package oriseus.packserver.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Value("${token}")	
	private String token;
	
	private final FileService fileService;
	
	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@GetMapping("/technicalMap")
	public ResponseEntity<?> getTechnicalMap(@RequestHeader("fileName") String fileName,
											 @RequestHeader("token") String headerToken,								 
											 @RequestHeader("owner") String owner) {		
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		File file = fileService.getTechnicalMap(fileName, owner);	
		
		if (file == null) {
			return new ResponseEntity<>("Technical map not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}		
	}
	
	@PostMapping("/technicalMap")
	public ResponseEntity<?> postTechnicalMap(@RequestBody File file,
											  @RequestHeader("token") String headerToken,
											  @RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
				
		try {
			fileService.saveTechnicalMap(file, owner);
		} catch (IOException e) {	
			e.printStackTrace();
			return new ResponseEntity<>("Technical map saving error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PostMapping("/changeTechnicalMap")
	public ResponseEntity<?> changeTechnicalMap(@RequestBody File file,
												@RequestHeader("token") String headerToken,
												@RequestHeader("oldOwner") String oldOwner,
												@RequestHeader("newOwner") String newOwner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			fileService.changeTechnicalMap(file, oldOwner, newOwner);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Technical map change error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/imageOfTechnicalMap")
	public ResponseEntity<?> getImageOfTechnicalMap(@RequestHeader("token") String headerToken,
													@RequestHeader("fileName") String fileName,
													@RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		File file = fileService.getImageOfTechnicalMap(fileName, owner);	
		
		if (file == null) {
			return new ResponseEntity<>("Image of technical map not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}
	}
	
	@PostMapping("/imageOfTechnicalMap")
	public ResponseEntity<?> postImageOfTechnicalMap(@RequestBody File file,
													 @RequestHeader("token") String headerToken,
													 @RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			fileService.saveImageOfTechnicalMap(file, owner);
		} catch (IOException e) {	
			e.printStackTrace();
			return new ResponseEntity<>("Image of technical map saving error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);		
	}
	@PostMapping("/changeImageOfTechnicalMap")
	public ResponseEntity<?> changeImageOfTechnicalMap(@RequestBody File file,
													   @RequestHeader("token") String headerToken,
													   @RequestHeader("oldOwner") String oldOwner,
													   @RequestHeader("newOwner") String newOwner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			fileService.changeImageOfTechnicalMap(file, oldOwner, newOwner);
			fileService.changeDamagedImageOfTechnicalMap(file, oldOwner, newOwner);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Image of technical map change error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);		
	}
	
	@GetMapping("/damagedImageOfTechnicalMap")
	public ResponseEntity<?> getDamagedImageOfTechnicalMap(@RequestHeader("token") String headerToken,
														   @RequestHeader("fileName") String fileName,
			   											   @RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		File file = fileService.getDamagedImageOfTechnicalMap(fileName, owner);
		
		if (file == null) {
			return new ResponseEntity<>("Damaged image of technical map not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);
		}
	}
	
	@PostMapping("/damagedImageOfTechnicalMap")
	public ResponseEntity<?> postDamagedImageOfTechnicalMap(@RequestBody File file,
															@RequestHeader("token") String headerToken,
															@RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			fileService.saveDamagedImageOfTechnicalMap(file, owner);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Damaged image of technical map saving error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/fromRepair")
	public ResponseEntity<?> fromRepair(@RequestHeader("token") String headerToken,
										@RequestHeader("fileName") String fileName,
										@RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		boolean isSuccess = fileService.cleanDamagedImageOfTechnologicalMap(fileName, owner);
		if (!isSuccess) {
			return new ResponseEntity<>("Clean damaged image of technological map error", HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(HttpStatus.OK);
		}
	}
	
	@GetMapping("/fromArchive")
	public ResponseEntity<?> getArchiveImages(@RequestHeader("token") String headerToken,
											  @RequestHeader("owner") String owner) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<File> listOfFileFromArchive = fileService.getDamagedImagesFromArchive(owner);
		
		if (listOfFileFromArchive == null) {
			return new ResponseEntity<>("Images from archive not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(listOfFileFromArchive, HttpStatus.OK);
		}
	}
	
}
