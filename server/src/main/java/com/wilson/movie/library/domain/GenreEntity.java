package com.wilson.movie.library.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Genre JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "GENRE")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @OneToMany(mappedBy = "genre")
    private Collection<TvShowEntity> tvShows = new ArrayList<>();

    @OneToMany(mappedBy = "genre")
    private Collection<MovieEntity> movies = new ArrayList<>();

    public GenreEntity(@NotNull String name) {
        this.name = name;
    }

    public GenreEntity(@NotNull String name, @Null String description) {
        this.name = name;
        this.description = description;
    }

}
