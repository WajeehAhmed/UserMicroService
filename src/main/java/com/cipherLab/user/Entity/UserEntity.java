package com.cipherLab.user.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    private @Id @GeneratedValue Long id;
    private String name;
    private Integer age;
    private String cellNumber;
    private LocalDateTime lastChangeTs;
}
