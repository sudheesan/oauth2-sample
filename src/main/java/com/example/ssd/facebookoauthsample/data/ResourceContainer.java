package com.example.ssd.facebookoauthsample.data;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

public class ResourceContainer {

	private static volatile Map<String, JsonNode> resourceMap;
	private static ResourceContainer resourceDataHolder;

	private ResourceContainer() {

		resourceMap = new HashMap<String, JsonNode>();
	}

	public static ResourceContainer getInstance() {

		if (resourceDataHolder == null) {

			synchronized (ResourceContainer.class) {
				if (resourceDataHolder == null) {
					resourceDataHolder = new ResourceContainer();
				}
			}
		}

		return resourceDataHolder;
	}

	public void addResource(String key, JsonNode value) {
		resourceMap.put(key, value);
	}

	public JsonNode getResource(String key) {
		return resourceMap.get(key);
	}
	
	public Map<String, JsonNode> getAllResources(){
		return resourceMap;
	}

}
