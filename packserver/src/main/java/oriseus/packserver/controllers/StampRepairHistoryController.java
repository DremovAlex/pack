package oriseus.packserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.dto.StampRepairHistoryDTO;
import oriseus.packserver.dto.StampRepairHistoryWrapper;
import oriseus.packserver.services.StampRepairHistoryService;
import oriseus.packserver.utils.Convert;

@RestController
@RequestMapping("/stampRepairHistory")
public class StampRepairHistoryController {
	
	private final StampRepairHistoryService stampRepairHistoryService;
	private final Convert convert;
	
	@Autowired
	public StampRepairHistoryController(StampRepairHistoryService stampRepairHistoryService,
										Convert convert) {
		this.stampRepairHistoryService = stampRepairHistoryService;
		this.convert = convert;
	}
	
	@PostMapping("/addNewRepairHistory")
	public ResponseEntity<HttpStatus> addNewRepairHistory(@RequestBody StampRepairHistoryDTO stampRepairHistoryDTO) {
		stampRepairHistoryService.addRepairHistory(convert.convertToStampRepairHistory(stampRepairHistoryDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public StampRepairHistoryWrapper getByStampName(@PathVariable String name) {
		return stampRepairHistoryService.findByName(name);
	}
	
}
