package com.wilson.movie.library.service;

import com.wilson.movie.library.domain.LanguageEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Outlines the implementation of a language service.
 *
 * @author Zach Wilson
 */
public interface LanguageService {

    LanguageEntity create(LanguageEntity language);

    Optional<LanguageEntity> getById(Integer id);
    Optional<LanguageEntity> getByName(String name);
    Collection<LanguageEntity> getAllWithIds(Collection<Integer> ids);
    Collection<LanguageEntity> getAll();

    Optional<LanguageEntity> update(Integer id, LanguageEntity language);

    Optional<Integer> deleteById(Integer id);
    Collection<Integer> deleteAllWithIds(Collection<Integer> ids);
    Collection<Integer> deleteAll();

    boolean exists(Integer id);
    boolean exists(String name);

}
