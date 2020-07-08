package org.redisson.spring.starter.repository;

import java.util.Map;
import org.redisson.spring.starter.model.User;

public interface UserRepository {

  void save(User user);

  Map<String, User> findAll();

  User findById(String id);

  void update(User user);

  void delete(String id);
}
