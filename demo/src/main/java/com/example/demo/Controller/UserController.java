package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsers(@RequestParam("nickname") String nickname){
    List<User> users = userService.getNicknameUser(nickname);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

}
