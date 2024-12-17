package net.IFTS11.maquina_Express.maquina_Express.controllers;

import jakarta.validation.Valid;
import net.IFTS11.maquina_Express.maquina_Express.entities.User;
import net.IFTS11.maquina_Express.maquina_Express.repositories.UserRepository;
import net.IFTS11.maquina_Express.maquina_Express.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }


    @PostMapping("/registrar")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        //user.setAdmin(false);
        return create(user, result);
    }

    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/validation")
    public ResponseEntity<String> validacion(){
        return new ResponseEntity<String>("ok",null,HttpStatus.ACCEPTED);
    }

    @PostMapping("/activar")
    public ResponseEntity<String> activar(@RequestBody User user){
        Optional<User> optionalUser=userRepository.findByUsername(user.getUsername());

        if(optionalUser.isEmpty())
            return new ResponseEntity<String>("nok",null,HttpStatus.ACCEPTED);

        user=optionalUser.get();
        user.setActivo(true);
        user=service.save(user);
        if (user.isActivo())
            return new ResponseEntity<String>("ok",null,HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<String>("nok",null,HttpStatus.ACCEPTED);
    }
}
