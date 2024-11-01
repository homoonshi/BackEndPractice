package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository userRepository){
    this.repository = userRepository;
  }

  public List<User> getNicknameUser(String nickname){

    List<User> users = repository.findByNickname(nickname);

    return users;
  }

}
