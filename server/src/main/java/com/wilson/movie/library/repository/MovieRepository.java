package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT m FROM MovieEntity m WHERE m.title LIKE :title")
    MovieEntity findByTitle(@Nonnull @Param("title") String title);

    @Query("SELECT m FROM MovieEntity m WHERE m.studio LIKE :studio")
    Collection<MovieEntity> findAllByStudio(@Nonnull @Param("studio") String studio);

    Collection<MovieEntity> findAllById(@Nonnull Collection<Integer> ids);

    Collection<MovieEntity> findAllByReleaseDate(@Nonnull LocalDate releaseDate);

}
