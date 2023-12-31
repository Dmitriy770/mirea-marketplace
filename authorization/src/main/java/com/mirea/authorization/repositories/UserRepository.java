package com.mirea.authorization.repositories;

import com.mirea.authorization.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}
