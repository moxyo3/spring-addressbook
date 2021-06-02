package com.example.addressbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.addressbook.accessingdatamysql.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
	public UserData findByUsername(String username);

}
