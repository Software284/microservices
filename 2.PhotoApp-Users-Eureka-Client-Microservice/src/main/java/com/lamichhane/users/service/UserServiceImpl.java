package com.lamichhane.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lamichhane.users.dao.UsersDao;
import com.lamichhane.users.entity.UserEntity;
import com.lamichhane.users.feign.AlbumServiceClient;
import com.lamichhane.users.model.AlbumResponseModel;
import com.lamichhane.users.shared.UserDto;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UsersDao usersDao;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	@Autowired	
//	RestTemplate getRestTemplate;
	

	AlbumServiceClient albumServiceClient;
	
	@Autowired
	private Environment environment;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UserServiceImpl(AlbumServiceClient albumServiceClient) {
		this.albumServiceClient = albumServiceClient;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity entity = mapper.map(userDetails, UserEntity.class);
		
		UserDto dto = mapper.map(entity, UserDto.class);
		
		usersDao.save(entity);
		return dto; 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity entity = usersDao.findByEmail(username);
		if(entity == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(entity.getEmail(),entity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
		
	}

	@Override
	public UserDto getUserDetailByEmail(String email) {
		UserEntity entity = usersDao.findByEmail(email);
		if(entity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new ModelMapper().map(entity,UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = usersDao.findByUserId(userId);
		if(userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		/*
		String albumUrl = String.format(environment.getProperty("albums.url"), userId);
		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = getRestTemplate.exchange(albumUrl, HttpMethod.GET,null,new ParameterizedTypeReference<List<AlbumResponseModel>>(){
			
		});
		List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
		*/
		System.out.println("Before calling album microservice");
		List<AlbumResponseModel> albumsList = albumServiceClient.getAlbums(userId);
		System.out.println("After calling album microservice");
		userDto.setAlbums(albumsList);
		
		 
		return userDto;
		
	}

}
