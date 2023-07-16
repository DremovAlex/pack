package oriseus.packserver.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.dto.StampDamageHistoryDTO;
import oriseus.packserver.dto.StampDamageHistoryWrapper;
import oriseus.packserver.services.FileService;
import oriseus.packserver.services.StampDamageHistoryService;
import oriseus.packserver.utils.Convert;

@RestController
@RequestMapping("/stampDamageHistory")
public class StampDamageHistoryController {
	
	@Value("${token}")	
	private String token;
	@Value("${TechnicalMapImagesSuffix}")
	private String TechnicalMapImagesSuffix;
	
	private final StampDamageHistoryService stampDamageHistoryService;
	private final Convert convert;
	private final FileService fileService;
	
	@Autowired
	public StampDamageHistoryController(StampDamageHistoryService stampDamageHistoryService,
										Convert convert,
										FileService fileService) {
		this.stampDamageHistoryService = stampDamageHistoryService;
		this.convert = convert;
		this.fileService = fileService;
	}
	
	@PostMapping("/addNewDamageHistory")
	public ResponseEntity<?> addNewDamageHistory(@RequestBody StampDamageHistoryDTO stampDamageHistoryDTO,
												 @RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			stampDamageHistoryService.addDamageHistory(convert.convertToStampDamageHistory(stampDamageHistoryDTO));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Stamp update error", HttpStatus.NOT_FOUND);
		}
		
		fileService.sendDamagedImageToArchive(stampDamageHistoryDTO.getStampDTO().getName(), 
											  stampDamageHistoryDTO.getDateOfDamageDetection().toString(), 
											  stampDamageHistoryDTO.getStampDTO().getTechnologicalMapName() + TechnicalMapImagesSuffix);
		
		return ResponseEntity.ok(HttpStatus.OK);		
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?> getByStampName(@PathVariable String name,
											@RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		StampDamageHistoryWrapper stampDamageHistoryWrapper = stampDamageHistoryService.findbyName(name);
		
		return new ResponseEntity<>(stampDamageHistoryWrapper, HttpStatus.OK);
	}
}
