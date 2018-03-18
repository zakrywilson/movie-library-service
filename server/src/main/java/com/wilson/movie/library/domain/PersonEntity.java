package com.wilson.movie.library.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

/**
 * Person JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "PERSON")
@Entity(name = "Person")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 100)
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "DATE_OF_DEATH")
    private LocalDate dateOfDeath;

    public PersonEntity(@NotNull String firstName, @NotNull String lastName, @NotNull LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public PersonEntity(@NotNull String firstName, @Null String middleName, @NotNull String lastName,
            @NotNull LocalDate dateOfBirth, @Null LocalDate dateOfDeath) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

}
