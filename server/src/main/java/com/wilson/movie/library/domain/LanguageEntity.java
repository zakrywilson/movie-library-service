package com.wilson.movie.library.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Language JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "LANGUAGE")
@Entity(name = "Language")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LanguageEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    public LanguageEntity(@NotNull String name) {
        this.name = name;
    }

    public LanguageEntity(@NotNull String name, @Null String description) {
        this.name = name;
        this.description = description;
    }

}
