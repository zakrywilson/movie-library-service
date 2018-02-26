package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Repository for {@link MovieEntity}.
 *
 * @author Zach Wilson
 */
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    MovieEntity findByTitleIsLike(@Nonnull @Param("title") String title);
    Collection<MovieEntity> findAllById(@Nonnull @Param("ids") Collection<Integer> ids);
    Collection<MovieEntity> findAllByReleaseDate(@Nonnull @Param("releaseDate") LocalDate releaseDate);
    Collection<MovieEntity> findAllByStudio(@Nonnull @Param("studio") String studio);
    void deleteAllById(@Nonnull @Param("ids") Collection<Integer> ids);

}
