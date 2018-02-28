package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.MovieEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a movie service.
 *
 * @author Zach Wilson
 */
public interface MovieService {

    MovieEntity create(MovieEntity movie);

    Optional<MovieEntity> getById(Integer id);
    Optional<MovieEntity> getByTitle(String title);
    Collection<MovieEntity> getAllByReleaseDate(LocalDate releaseDate);
    Collection<MovieEntity> getAllByReleaseDate(Integer releaseDateEpochDay);
    Collection<MovieEntity> getAllByStudio(String studio);
    Collection<MovieEntity> getAllWithIds(Collection<Integer> ids);
    Collection<MovieEntity> getAll();

    Optional<MovieEntity> update(Integer id, MovieEntity movie);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String title);

}
