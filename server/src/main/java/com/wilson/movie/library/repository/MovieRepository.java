package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Repository for {@link MovieEntity}.
 *
 * @author Zach Wilson
 */
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    MovieEntity findByTitleIsLike(@Nonnull String title);
    Collection<MovieEntity> findAllById(@Nonnull Collection<Integer> ids);
    Collection<MovieEntity> findAllByReleaseDate(@Nonnull LocalDate releaseDate);
    Collection<MovieEntity> findAllByStudio(@Nonnull String studio);
    void deleteAllById(@Nonnull Collection<Integer> ids);

}
