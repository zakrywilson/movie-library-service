package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.TvShowEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a TV show service.
 *
 * @author Zach Wilson
 */
public interface TvShowService {

    TvShowEntity create(TvShowEntity tvShow);

    Optional<TvShowEntity> getById(Integer id);
    Optional<TvShowEntity> getByTitle(String title);
    Collection<TvShowEntity> getAllByDateAired(LocalDate dateAired);
    Collection<TvShowEntity> getAllByDateAired(Integer dateAiredEpochDay);
    Collection<TvShowEntity> getAllByNetwork(String network);
    Collection<TvShowEntity> getAllWithIds(Collection<Integer> ids);
    Collection<TvShowEntity> getAll();

    Optional<TvShowEntity> update(Integer id, TvShowEntity tvShow);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String title);

}
