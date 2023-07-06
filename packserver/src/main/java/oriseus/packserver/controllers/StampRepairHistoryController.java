package oriseus.packserver.controllers;

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

import oriseus.packserver.dto.StampRepairHistoryDTO;
import oriseus.packserver.dto.StampRepairHistoryWrapper;
import oriseus.packserver.services.StampRepairHistoryService;
import oriseus.packserver.utils.Convert;

@RestController
@RequestMapping("/stampRepairHistory")
public class StampRepairHistoryController {
	
	@Value("${token}")	
	private String token;
	
	private final StampRepairHistoryService stampRepairHistoryService;
	private final Convert convert;
	
	@Autowired
	public StampRepairHistoryController(StampRepairHistoryService stampRepairHistoryService,
										Convert convert) {
		this.stampRepairHistoryService = stampRepairHistoryService;
		this.convert = convert;
	}
	
	@PostMapping("/addNewRepairHistory")
	public ResponseEntity<HttpStatus> addNewRepairHistory(@RequestBody StampRepairHistoryDTO stampRepairHistoryDTO,
														@RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		stampRepairHistoryService.addRepairHistory(convert.convertToStampRepairHistory(stampRepairHistoryDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<StampRepairHistoryWrapper> getByStampName(@PathVariable String name,
																	@RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		StampRepairHistoryWrapper stampRepairHistoryWrapper = stampRepairHistoryService.findByName(name);
		return new ResponseEntity<>(stampRepairHistoryWrapper, HttpStatus.OK);
	}
	
}
