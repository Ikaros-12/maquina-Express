package net.IFTS11.maquina_Express.maquina_Express.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import net.IFTS11.maquina_Express.maquina_Express.validation.ExistsbyUsername;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ExistsbyUsername
    @Column(unique=true)
    private String username;
    private String password;
    @Column(unique=true)
    private String mail;

    private String celular;
    private boolean activo;

    @JsonIgnoreProperties({"users","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","rol_id"})}
    )
    private List<Rol> roles;
    public User(String username, String password, String mail, String celular, boolean activo) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.celular = celular;
        this.activo = activo;
    }

    public User(String username, String password, String mail, String celular) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.celular = celular;
    }

    public User(){
        roles = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> rols) {
        this.roles = rols;
    }
}
