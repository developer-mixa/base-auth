package com.example.base_auth.model;

import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserDbEntity, Long> {

    UserDbEntity findByUsername(String username);

}
