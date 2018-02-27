package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.MovieEntity;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Outlines the implementation of a movie service.
 *
 * @author Zach Wilson
 */
public interface MovieService {

    MovieEntity create(MovieEntity movie);

    MovieEntity getById(Integer id);
    MovieEntity getByTitle(String title);
    Collection<MovieEntity> getAllByReleaseDate(LocalDate releaseDate);
    Collection<MovieEntity> getAllByReleaseDate(Integer releaseDateEpochDay);
    Collection<MovieEntity> getAllByStudio(String studio);
    Collection<MovieEntity> getAllById(Collection<Integer> ids);
    Collection<MovieEntity> getAll();

    MovieEntity update(Integer id, MovieEntity movie);

    Integer deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String title);

}
