package com.springbazaar.user_service.respository;

import com.springbazaar.user_service.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity,String> {
}
