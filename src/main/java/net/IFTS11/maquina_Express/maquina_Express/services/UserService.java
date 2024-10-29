package net.IFTS11.maquina_Express.maquina_Express.services;

import net.IFTS11.maquina_Express.maquina_Express.entities.User;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username);

    List<User> findAll();

    User save(User user);
}
