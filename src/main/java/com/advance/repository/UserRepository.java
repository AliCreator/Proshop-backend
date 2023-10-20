package com.advance.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.advance.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE Users SET name = :name WHERE id = :userId", nativeQuery = true)
	User updateUserName(@Param("name") String name, @Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Users SET is_admin = true WHERE id = :userId", nativeQuery = true)
	User updateUserRole(@Param("userId") Long userId);

	@Query(value = "SELECT * FROM Users WHERE email = :email", nativeQuery = true)
	User getUserByEmail(@Param("email") String email);
	
	@Query(value = "SELECT COUNT(*) FROM Users WHERE email = :email", nativeQuery = true)
	Integer getEmailCount(@Param("email") String email);
}
