package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.*;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user= userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	
	@Override
	public UserDto getUserbyId(Integer userId) {
		
		User user= userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
		
		return user;
		
	}
	
public UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
		
	}


}
