package com.cheshtagupta.laundry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;

    @NotNull
    @Length(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String fName;

    @NotNull
    @Length(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String lName;

    @NotNull
    @Email
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, length = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    @JsonIgnore
    private boolean isVerified = false;

    @Column
    private byte notificationDays = 0;

    @Column
    private LocalTime notificationTime;
}
