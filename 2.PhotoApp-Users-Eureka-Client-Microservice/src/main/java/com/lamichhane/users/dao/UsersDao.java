package com.lamichhane.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lamichhane.users.entity.UserEntity;

@Repository
public interface UsersDao extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByEmail(String email);
	public UserEntity findByUserId(String userId);
}
