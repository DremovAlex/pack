package oriseus.packserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oriseus.packserver.dto.StampDTO;
import oriseus.packserver.dto.StampWrapper;
import oriseus.packserver.services.StampService;
import oriseus.packserver.utils.Convert;

@RestController
@RequestMapping("/stamps")
public class StampController {

	private final StampService stampService;
	private final Convert convert;
	
	@Autowired
	public StampController(StampService stampService,
							Convert convert) {
		this.stampService = stampService;
		this.convert = convert;
	}
	
	@PostMapping("/addNewStamp")
	public ResponseEntity<HttpStatus> addNewStamp(@RequestBody StampDTO stampDTO) {
		stampService.addNewStamp(convert.convertToStamp(stampDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<StampWrapper> getAll() {
		StampWrapper stampWrapper = stampService.findAll();
		return new ResponseEntity<>(stampWrapper, HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<StampDTO> getByName(@PathVariable String name) {
		StampDTO stampDTO = convert.convertToStampDTO(stampService.findByName(name));	
		return new ResponseEntity<>(stampDTO, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<HttpStatus> updateStamp(@RequestBody StampDTO stampDTO) {
		stampService.updateStamp(convert.convertToStamp(stampDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteStamp(@RequestBody StampDTO stampDTO) {
		stampService.deleteStamp(convert.convertToStamp(stampDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
