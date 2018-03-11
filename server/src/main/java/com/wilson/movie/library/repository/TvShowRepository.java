package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.TvShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Repository for {@link TvShowEntity}.
 *
 * @author Zach Wilson
 */
public interface TvShowRepository extends JpaRepository<TvShowEntity, Integer> {

    @Query("SELECT t FROM TvShowEntity t WHERE t.title LIKE :title")
    TvShowEntity findByTitle(@Nonnull @Param("title") String title);

    @Query("SELECT t FROM TvShowEntity t WHERE t.network LIKE :network")
    Collection<TvShowEntity> findAllByNetwork(@Nonnull @Param("network") String network);

    Collection<TvShowEntity> findAllById(@Nonnull Collection<Integer> id);

    Collection<TvShowEntity> findAllByDateAired(@Nonnull LocalDate dateAired);

}
