package oriseus.pack.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import oriseus.pack.controllers.LoginController;
import oriseus.pack.utils.ServerException;

public class HttpService {
	
	private static String token;
	
    private static final Logger logger = (Logger) LogManager.getLogger(HttpService.class);
		
	public static <T> T getObject(String url, Class<T> clazz) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<T> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, clazz);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException( "Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
		
		return (T) response.getBody();
	}
	
	public static <T> void sendObject(String url, T t) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		
		HttpEntity<T> entity = new HttpEntity<>(t, headers);
				
		ResponseEntity<HttpStatus> response = null;
		
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, entity, HttpStatus.class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
	}
	
	public static <T> T sendAndGetObject(String url, T t, Class<T> clazz) throws ServerException {
		
		final  RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");

		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		
		HttpEntity<T> entity = new HttpEntity<>(t, headers);
		
		ResponseEntity<T> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, entity, clazz);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			throw new ServerException(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
				
		return (T) response.getBody();
	}
	
	public static File getFile(String url, String fileName, String owner) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		headers.set("fileName", fileName);
		headers.set("owner", owner);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<File> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, File.class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
		
		return response.getBody();
	}
	
	public static File[] getFiles(String url, String owner) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		headers.set("owner", owner);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<File[]> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, File[].class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
		
		return response.getBody();
	}
	
	public static void sendFile(String url, File file, String owner) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		headers.set("owner", owner);
		
		HttpEntity<File> entity = new HttpEntity<>(file, headers);

		ResponseEntity<HttpStatus> response = null;
		
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, entity, HttpStatus.class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
	}
	
	public static void sendFile(String url, File file, String oldOwner, String newOwner) {
		
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		headers.set("oldOwner", oldOwner);
		headers.set("newOwner", newOwner);
		
		HttpEntity<File> entity = new HttpEntity<>(file, headers);

		ResponseEntity<HttpStatus> response = null;
		
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, entity, HttpStatus.class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
	}
	
	public static void sendGetRequest(String url, String fileName, String owner) {
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		token = PropertiesService.getProperties("Token");
	
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", token);
		headers.set("fileName", fileName);
		headers.set("owner", owner);
		
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<HttpStatus> response = null;
		
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, entity, HttpStatus.class);
		} catch (ResourceAccessException resourceAccessException) {
			AlertService.showAlertException("Ошибка связи с сервером", resourceAccessException.getCause().toString());
			logger.error(resourceAccessException.getMessage());
		} catch (Exception ex) {
			AlertService.showAlertException("Ошибка связи с сервером", ex.getCause().toString());
			logger.error(ex.getMessage());
		}
		
		if (response.getBody() == null) {
			AlertService.showAlertException("Ошибка связи с сервером", "Сервер не отвечает");
			logger.error(new NullPointerException());
		}
	}

}
