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
 * Rating JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "RATING")
@Entity(name = "Rating")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @OneToMany(mappedBy = "rating")
    private Collection<TvShowEntity> tvShows = new ArrayList<>();

    @OneToMany(mappedBy = "rating")
    private Collection<TvShowEntity> movies = new ArrayList<>();

    public RatingEntity(@NotNull String name) {
        this.name = name;
    }

    public RatingEntity(@NotNull String name, @Null String description) {
        this.name = name;
        this.description = description;
    }

}
