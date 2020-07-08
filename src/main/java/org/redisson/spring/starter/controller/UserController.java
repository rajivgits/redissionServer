package org.redisson.spring.starter.controller;


import java.util.Map;
import org.redisson.spring.starter.model.User;
import org.redisson.spring.starter.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @RestController
// @RequestMapping("/rest")
public class UserController {

  @Autowired
  private UserRepositoryImpl userRepository;


  /*
   * public UserController(UserRepository userRepository) { this.userRepository = userRepository; }
   */

  @GetMapping("/add/{id}/{name}")
  public User add(@PathVariable("id") final String id, @PathVariable("name") final String name) {
    userRepository.save(new User(id, name, 20000L));
    return userRepository.findById(id);
  }

  @GetMapping("/update/{id}/{name}")
  public User update(@PathVariable("id") final String id, @PathVariable("name") final String name) {
    userRepository.update(new User(id, name, 1000L));
    return userRepository.findById(id);
  }

  @GetMapping("/delete/{id}")
  public Map<String, User> delete(@PathVariable("id") final String id) {
    userRepository.delete(id);
    return all();
  }

  @GetMapping("/all")
  public Map<String, User> all() {
    return userRepository.findAll();
  }
}
