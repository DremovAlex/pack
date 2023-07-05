package oriseus.pack.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HttpService {
		
	public static <T> T getObject(String url, Class<T> clazz) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<T> entity = null;
		
		try {
			entity = restTemplate.getForEntity(url, clazz);	
		} catch (Exception e) {
			AlertService.showAlertException(e, "Ошибка соединения", e.getMessage());
		}
			
		if (!entity.getStatusCode().equals(HttpStatus.OK)) {			
			Exception e = new RestClientException(entity.getStatusCode().toString());
			AlertService.showAlertException(e, "Ошибка соединения", e.getMessage());
		}
		
		return (T) entity.getBody();
	}
	
	public static <T> void sendObject(String url, T t) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<HttpStatus> entity = null;
		
		try {
			entity = restTemplate.postForEntity(url, t, HttpStatus.class);
		} catch (Exception e) {
			AlertService.showAlertException(e, "Ошибка соединения", e.getMessage());
		}
			
		if (!entity.getStatusCode().equals(HttpStatus.OK)) {
			Exception e = new RestClientException(entity.getStatusCode().toString());
			AlertService.showAlertException(e, "Ошибка соединения", e.getMessage());
		}
	}
}
