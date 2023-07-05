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
		ResponseEntity<T> entity = restTemplate.getForEntity(url, clazz);		
				
		if (!entity.getStatusCode().equals(HttpStatus.OK)) {
			throw new RestClientException("Ошибка соединения");
		}
		
		return (T) entity.getBody();
	}
	
	public static <T> void sendObject(String url, T t) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<HttpStatus> entity = restTemplate.postForEntity(url, t, HttpStatus.class);
		
		if (!entity.getStatusCode().equals(HttpStatus.OK)) {
			throw new RestClientException("Ошибка соединения");
		}
	}
}
