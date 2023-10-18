package com.mirea.authorization.repositories;

import com.mirea.authorization.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
//    private static final List<UserEntity> users = new ArrayList<>();
//
//    public void addUser(UserEntity user) {
//        users.add(user);
//    }
//
//    public UserEntity getUserByUsername(String username) {
//        for (var user : users) {
//            if (user.getUsername().equals(username)) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    public List<UserEntity> getUsers(){
//        return users;
//    }
}
