package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {
	 @RequestMapping(value="/hotjar/api", method = RequestMethod.POST)
	    public @ResponseBody ResponseEntity<String> addTrack(@RequestParam Map<String,String> requestParams) {
	        System.out.println("Adding user...");
	        return new ResponseEntity<String>("User, Page added", HttpStatus.OK);  
	    }
	
}
