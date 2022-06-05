package com.revature;

import com.revature.controller.RequestMapper;

import io.javalin.Javalin;

public class Main {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(2237);
		
		RequestMapper requestMapper = new RequestMapper();
		
		requestMapper.configureRoutes(app);
		
	}

}