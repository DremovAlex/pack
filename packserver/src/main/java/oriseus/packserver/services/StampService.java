package oriseus.packserver.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oriseus.packserver.dto.StampDTO;
import oriseus.packserver.dto.StampWrapper;
import oriseus.packserver.models.Stamp;
import oriseus.packserver.repositories.StampRepository;
import oriseus.packserver.utils.Convert;

@Service
@Transactional
public class StampService {
	
	private final StampRepository stampRepository;
	private final Convert convert;
	private final FileService fileService;
	
	@Autowired
	public StampService(StampRepository stampRepository,
						Convert convert,
						FileService fileService) {
		this.stampRepository = stampRepository;
		this.convert = convert;
		this.fileService = fileService;
	}
	
	@Transactional
	public void addNewStamp(Stamp stamp) {
		stamp.setAddingDate(LocalDateTime.now());
		stampRepository.save(stamp);
	}

	public StampWrapper findAll() {
		List<Stamp> stamps = stampRepository.findAll();
		ArrayList<StampDTO> stampDTOs = new ArrayList<StampDTO>();
		for (Stamp stamp : stamps) {
			stampDTOs.add(convert.convertToStampDTO(stamp));
		}
		StampWrapper stampWrapper = new StampWrapper();
		stampWrapper.setList(stampDTOs);
		return stampWrapper;
	}
	
	public Stamp findByName(String name) {
		return stampRepository.findByName(name).orElseThrow();
	}

	@Transactional
	public void updateStamp(Stamp stamp) {
		Stamp oldStamp = stampRepository.findById(stamp.getId()).orElseThrow();
		stamp.setAddingDate(oldStamp.getAddingDate());		
		stampRepository.save(stamp);
	}

	@Transactional
	public void deleteStamp(Stamp stamp) {
		Stamp deletedStamp = stampRepository.findByName(stamp.getName()).orElseThrow();
		
		fileService.cleanArchive(stamp.getName());
		fileService.deleteTechnicalMap(stamp.getName());
		fileService.deleteImageOfTechnicalMap(stamp.getName());
		
		stampRepository.delete(deletedStamp);
	}

}
