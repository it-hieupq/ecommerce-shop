package com.capstone.client.user.domain.controller.admin;

import com.capstone.client.user.domain.common.ResponseDTO;
import com.capstone.client.user.domain.common.Stringify;
import com.capstone.client.user.domain.model.dto.response.UserDTO;
import com.capstone.client.user.domain.model.entity.User;
import com.capstone.client.user.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("AdminUserController")
@CrossOrigin(Stringify.ORIGIN_FRONTEND_URL)
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {

        Map<String, Object> map = new HashMap<>();
        String message = "Get all users successfully";
        HttpStatus status = HttpStatus.OK;
        List<UserDTO> users = new ArrayList<>();

        try {
            users = userService.findAll();
        } catch (Exception e) {
            message = e.getLocalizedMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("users", users);
        return ResponseDTO.getResponse(message, status, map);
    }

    @PostMapping("")
    public ResponseEntity<Object> add(User user) {
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

//    @PutMapping("/{userId}")
//    public ResponseEntity<Object> update(@PathVariable(name = "userId", required = true) int userId, User user) {
//        String message = "User has been updated";
//        HttpStatus status = HttpStatus.OK;
//        Map<String, Object> map = new HashMap<>();
//
//        User _user = null;
//
//        try {
//            _user = userDao.findOne(userId);
//
//            if (_user != null) {
//                user.setUserId(_user.getUserId());
//
//                if (userDao.update(user) <= 0) {
//                    user = null;
//                    message = "Update user failed";
//                    status = HttpStatus.EXPECTATION_FAILED;
//                }
//            } else {
//                message = "Not found user with id = " + userId;
//                status = HttpStatus.NOT_FOUND;
//            }
//
//        } catch (Exception e) {
//            message = "Update user failed, details: " + e.getMessage();
//            status = HttpStatus.EXPECTATION_FAILED;
//            user = null;
//        }
//
//        map.put("user", user);
//        return Responsor.getResponse(message, status, map);
//    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<Object> existsById(@PathVariable(name = "userId") Integer userId){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "";
        boolean result = false;

        try {
            result = userService.existedById(userId);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("result", result);

        return ResponseEntity.status(status).body(map);
    }

}
