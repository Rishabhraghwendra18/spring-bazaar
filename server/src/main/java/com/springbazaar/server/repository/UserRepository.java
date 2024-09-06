package com.springbazaar.server.repository;

import com.springbazaar.server.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity,String> {
}
