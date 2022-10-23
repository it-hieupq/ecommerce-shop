package com.capstone.client.user.domain.controller.user;

import com.capstone.client.user.domain.common.ResponseDTO;
import com.capstone.client.user.domain.common.Stringify;
import com.capstone.client.user.domain.model.dto.response.UserDTO;
import com.capstone.client.user.domain.model.entity.User;
import com.capstone.client.user.domain.model.dto.request.UserReqDTO;
import com.capstone.client.user.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("UserController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserReqDTO userReq){
        Map<String, Object> map = new HashMap<>();
        String message = "Logged in successfully";
        HttpStatus status = HttpStatus.OK;
        UserDTO userDTO = null;

        try {
            userDTO = userService.login(userReq);

            if(userDTO == null || userDTO.getUserId()==null) {
                message = "user not found";
                status = HttpStatus.NOT_FOUND;
                userDTO = null;
            }

        } catch (Exception e){
            map.put("message", e.getMessage());
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("user", userDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody User user){
        String message = "Add user successfully";
        HttpStatus status = HttpStatus.CREATED;
        Map<String, Object> map = new HashMap<>();
        UserDTO userDTO = null;
        try {
            if(!userService.checkValid(user)){
                message = "Username or email is invalid.";
                status = HttpStatus.EXPECTATION_FAILED;
            } else if(userService.existedByEmailOrUsername(user.getEmail(), user.getUsername())) {
                message = "Username or email is empty or already used.";
                status = HttpStatus.EXPECTATION_FAILED;
            } else {
                userDTO = userService.save(user);
            }
        } catch (Exception e) {
            message = e.getLocalizedMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("user", userDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

}
