package net.IFTS11.maquina_Express.maquina_Express.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String mail;
    private String contacto;
    private int type;
    private boolean activo;
}
