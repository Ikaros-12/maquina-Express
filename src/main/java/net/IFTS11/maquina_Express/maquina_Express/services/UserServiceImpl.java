package net.IFTS11.maquina_Express.maquina_Express.services;


import net.IFTS11.maquina_Express.maquina_Express.entities.Rol;
import net.IFTS11.maquina_Express.maquina_Express.entities.User;
import net.IFTS11.maquina_Express.maquina_Express.repositories.RolRepository;
import net.IFTS11.maquina_Express.maquina_Express.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {

        Optional<Rol> optionalRolUser = rolRepository.findByName("ROLE_USER");
        List<Rol> roles = new ArrayList<>();

        optionalRolUser.ifPresent(roles::add);
        /*
        if (user.isAdmin()) {
            Optional<Rol> optionalRoleAdmin = rolRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }*/

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }
}
