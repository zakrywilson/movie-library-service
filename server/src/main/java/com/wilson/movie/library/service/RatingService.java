package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.RatingEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a rating service.
 *
 * @author Zach Wilson
 */
public interface RatingService {

    RatingEntity create(RatingEntity rating);

    Optional<RatingEntity> getById(Integer id);
    Optional<RatingEntity> getByName(String name);
    Collection<RatingEntity> getAllWithIds(Collection<Integer> ids);
    Collection<RatingEntity> getAll();

    Optional<RatingEntity> update(Integer id, RatingEntity rating);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String name);

}
