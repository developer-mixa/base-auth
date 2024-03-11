package com.example.base_auth.model;

import com.example.base_auth.models.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
public class UserDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Column(name = "username", unique = true)
    private String username;

    @Getter
    @Column(name = "password")
    private String password;

    public UserDTO toUserDTO(){
        return new UserDTO(firstName, lastName, username, password);
    }

    public Long getId() {
        return id;
    }
}
