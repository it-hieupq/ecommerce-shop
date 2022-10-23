package com.capstone.client.user.domain.service;

import com.capstone.client.user.domain.common.Stringify;
import com.capstone.client.user.domain.model.dto.response.UserDTO;
import com.capstone.client.user.domain.model.entity.User;
import com.capstone.client.user.domain.model.dto.request.UserReqDTO;
import com.capstone.client.user.domain.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User findByUserId(Integer userId){
        return userRepo.findByUserId(userId);
    }

    public UserDTO login(UserReqDTO userReq) throws NoSuchAlgorithmException {
        return userRepo.findByUsernameAndPassword(userReq.getUsername(), generateMD5Hash(userReq.getPassword())).toDTO();
    }

    public UserDTO signup(User user) throws NoSuchAlgorithmException {
        user.setPassword(generateMD5Hash(user.getPassword()));
        return userRepo.save(user).toDTO();
    }

    public List<UserDTO> findAll() {
        return toDTOList(userRepo.findAll());
    }

    private List<UserDTO> toDTOList(List<User> users) {
        if(users == null || users.isEmpty()) return Collections.emptyList();

        List<UserDTO> userDTOList = new ArrayList<>();

        users.forEach(user->userDTOList.add(user.toDTO()));
        return userDTOList;
    }

    public boolean existedByEmailOrUsername(String email, String username){
        return userRepo.existsByEmailOrUsername(email, username);
    }

    public boolean existedById(Integer userId){
        return this.userRepo.existsById(userId);
    }

    public boolean checkValid(User user) {
        if( user.getEmail().isEmpty() || user.getUsername().isEmpty()
                || !user.getUsername().matches(Stringify.REGEX_USERNAME)
                || !user.getEmail().matches(Stringify.REGEX_EMAIL)
                || user.getPassword()== null || user.getPassword().isEmpty()){
            return false;
        }

        return true;
    }

    public UserDTO save(User user) throws NoSuchAlgorithmException {
        user.setPassword(generateMD5Hash(user.getPassword()));
        return userRepo.save(user).toDTO();
    }

    public String generateMD5Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
