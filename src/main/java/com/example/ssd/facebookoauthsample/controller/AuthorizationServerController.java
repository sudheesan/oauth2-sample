package com.example.ssd.facebookoauthsample.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.ssd.facebookoauthsample.data.ResourceContainer;
import com.example.ssd.facebookoauthsample.util.FileIO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AuthorizationServerController {

	@RequestMapping("/friendlistviewapp/callback")
	public void getResource(@RequestParam("code") String code)
			throws JSONException, IOException {
		FileIO fileIO = new FileIO();
		String authorizationCode = code;
		System.out.println("The code is = "+authorizationCode);
		if (authorizationCode != null && authorizationCode.length() > 0) {
			final String TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token";
			final String GRANT_TYPE = "authorization_code";
			final String REDIRECT_URI = "http://localhost:8080/friendlistviewapp/callback";
			final String CLIENT_ID = "244993339493539";
			final String CLIENT_SECRET = "b535677dd9e4c3a4db2ee3c9d3267d98";
			// Generate POST request
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Content-Type", "text/plain");
			String bodyPart1 = ("grant_type="+ URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8.name()));
			String bodyPart2 = ("redirect_uri="+ URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()));
			String bodyPart3 = ("code="+ URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8.name()));
			String bodyPart4 = ("client_id="+ URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name()));

			String body=(bodyPart1+"&"+bodyPart2+"&"+bodyPart3+"&"+bodyPart4);
			
			// Add "Authorization" header with encoded client credentials
			String clientCredentials = CLIENT_ID + ":" + CLIENT_SECRET;
			String encodedClientCredentials = new String(Base64.encodeBase64(clientCredentials.getBytes()));
			httpHeaders.set("Authorization", "Basic " + encodedClientCredentials);
			// Make the access token request
			HttpEntity<String> httpEntity = new HttpEntity<String>(body, httpHeaders);
			RestTemplate restTemplate = new RestTemplate();
			String responce = restTemplate.postForObject(TOKEN_ENDPOINT, httpEntity, String.class);

			// Handle access token response
			JSONObject responceObj = new JSONObject(responce);
			// Isolate access token
			String accessToken = null;

			System.out.println("Found Responce Object : "+responceObj);
			try {
				
				accessToken = responceObj.get("access_token").toString();
				System.out.println("Access token: " + accessToken);

				fileIO.writeToFile(accessToken);

			} catch (JsonParseException e) {
				System.out.println("Error while parsing the response from facebook : " + e.getMessage());
			}

			// Request profile and feed data with access token
			// Request feed data with access token
			String requestUrl = "https://graph.facebook.com/v2.10/me/feed?limit=25";

			RestTemplate dataRestTemplate = new RestTemplate();
			HttpHeaders tokenHeader = new HttpHeaders();
			tokenHeader.add("Authorization", "Bearer " + accessToken);
			HttpEntity<?> dataHttpEntiy = new HttpEntity<>(tokenHeader);
 			ResponseEntity<String> dataResponce = dataRestTemplate.exchange(requestUrl, HttpMethod.GET,dataHttpEntiy,String.class);
			
 			ObjectMapper mapper = new ObjectMapper();
 			JsonNode root = mapper.readTree(dataResponce.getBody());
 			JsonNode data = root.path("data");
 			System.out.println("The Details = "+data.asText());
			// Generate a unique key
			int randomNum = ThreadLocalRandom.current().nextInt(1, 9999999);
			// Add the facebook response data to the resources map along with
			// the key
			ResourceContainer.getInstance().addResource(String.valueOf(randomNum), data);

			fileIO.writeToFile(data.asText());

		

			

		} else {
			// Handle failure
		}
	}
	
	@RequestMapping("/friendlistviewapp/feed")
	public ResponseEntity<?> getAllFeeds(){
		HashMap<String, JsonNode> detailsNode = (HashMap<String, JsonNode>) ResourceContainer.getInstance().getAllResources();
		return new ResponseEntity<>(detailsNode.values(),HttpStatus.OK);
	
	}

}
