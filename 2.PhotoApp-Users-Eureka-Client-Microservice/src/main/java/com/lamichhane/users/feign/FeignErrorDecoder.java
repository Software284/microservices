package com.lamichhane.users.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Service
public class FeignErrorDecoder implements ErrorDecoder{
	
	@Autowired
	Environment environment;

	@Override
	public Exception decode(String methodKey, Response response) {
		switch(response.status()) {
		case 400:
			// Do something
			// return new BadRequestExcepton
			break;
		case 404:
			if(methodKey.contains("getAlbums")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()),environment.getProperty("albums.exceptions.albums-not-found"));
			}
			break;
		default: 
			new Exception(response.reason());
		}
		return null;
	}

}
