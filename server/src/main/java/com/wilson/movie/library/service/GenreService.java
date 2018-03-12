package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.GenreEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a genre service.
 *
 * @author Zach Wilson
 */
public interface GenreService {

    GenreEntity create(GenreEntity genre);

    Optional<GenreEntity> getById(Integer id);
    Optional<GenreEntity> getByName(String name);
    Collection<GenreEntity> getAllWithIds(Collection<Integer> ids);
    Collection<GenreEntity> getAll();

    Optional<GenreEntity> update(Integer id, GenreEntity genre);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String name);

}
