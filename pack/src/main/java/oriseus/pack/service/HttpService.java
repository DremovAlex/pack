package oriseus.pack.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class HttpService {

	public static <T> T getObject(String stringUrl, Class<T> clazz) throws IOException {
		URL url = new URL(stringUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        
        httpURLConnection.setRequestMethod("GET");
        

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return (T) objectMapper.readValue(response.toString(), clazz);
	}
	
	public static <T> void sendObject(T t, String stringUrl, String method) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestMethod(method); 
        httpURLConnection.setDoOutput(true);
       
        ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String urlParameters = objectMapper.writeValueAsString(t);
				
        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
        outputStream.write(urlParameters);
        outputStream.flush();
        outputStream.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        httpURLConnection.disconnect();
	}
}
