package com.example.ssd.facebookoauthsample.util;

public class ApiConstants {
	public static final String TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token";
	public static final String ID_FEILD_ENDPOINT ="https://graph.facebook.com/v2.8/me?fields=id";
	public static final String GRAPH_ENDPOINT ="https://graph.facebook.com/v3.1/";
	public static final String TAGGED ="/tagged";
	public static final String TAGGABLE_FRIEMDS = "/taggable_friends";
	public static final String GRANT_TYPE = "authorization_code";
	public static final String REDIRECT_URI = "http://localhost:8080/auth";
	public static final String CLIENT_ID = "244993339493539";
	public static final String CLIENT_SECRET = "b535677dd9e4c3a4db2ee3c9d3267d98";
	public static final String FEED_REQUEST_URI = "https://graph.facebook.com/v2.10/me/feed?limit=25";


}
