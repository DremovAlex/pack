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

import oriseus.packserver.dto.StampDamageHistoryDTO;
import oriseus.packserver.dto.StampDamageHistoryWrapper;
import oriseus.packserver.services.StampDamageHistoryService;
import oriseus.packserver.utils.Convert;

@RestController
@RequestMapping("/stampDamageHistory")
public class StampDamageHistoryController {
	
	@Value("${token}")	
	private String token;
	
	private final StampDamageHistoryService stampDamageHistoryService;
	private final Convert convert;
	
	@Autowired
	public StampDamageHistoryController(StampDamageHistoryService stampDamageHistoryService,
										Convert convert) {
		this.stampDamageHistoryService = stampDamageHistoryService;
		this.convert = convert;
	}
	
	@PostMapping("/addNewDamageHistory")
	public ResponseEntity<HttpStatus> addNewDamageHistory(@RequestBody StampDamageHistoryDTO stampDamageHistoryDTO,
														@RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		stampDamageHistoryService.addDamageHistory(convert.convertToStampDamageHistory(stampDamageHistoryDTO));
		return ResponseEntity.ok(HttpStatus.OK);		
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<StampDamageHistoryWrapper> getByStampName(@PathVariable String name,
																	@RequestHeader("token") String headerToken) {
		
		if (!headerToken.equals(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		StampDamageHistoryWrapper stampDamageHistoryWrapper = stampDamageHistoryService.findbyName(name);
		return new ResponseEntity<>(stampDamageHistoryWrapper, HttpStatus.OK);
	}
}
