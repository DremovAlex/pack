package oriseus.packserver;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import oriseus.packserver.utils.Convert;

@SpringBootApplication
public class PackserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackserverApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Convert convert() {
		return new Convert(modelMapper());
	}
}
