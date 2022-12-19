package com.example.demo.model.entity;

import com.example.demo.model.entity.enumerator.UserGender;
import com.example.demo.model.entity.enumerator.UserRole;
import com.example.demo.model.entity.enumerator.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity {
    private Long id;
    private UserRole role;
    private UserStatus status;

    private String username;
    private String password;

    private UserGender gender;
    private Timestamp birthDate;

    private String fullName;
    private String emailAddress;
    private String contactNumber;

    @Override
    public String toString() {
        return "\nUser{" + "id: " + id +
                ", role: " + role +
                ", status: " + status +
                ", username: " + username +
                ", password: " + password +
                ", gender: " + gender +
                ", birth date: " + birthDate +
                ", fullName: " + fullName +
                ", email address: " + emailAddress +
                ", contact number: " + contactNumber +
                '}';
    }
}
