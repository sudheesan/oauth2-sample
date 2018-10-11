package com.example.ssd.facebookoauthsample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String index(){
		return "index.html";
	}
	
	@RequestMapping("/auth")
	public String index(@RequestParam("code") String code){
		return "index.html";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "index.html";
	}
	
	@RequestMapping("/main")
	public String main(){
		return "index.html";
	}
	

}
