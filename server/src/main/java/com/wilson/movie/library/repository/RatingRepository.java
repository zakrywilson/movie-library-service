package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Repository for {@link RatingEntity}.
 *
 * @author Zach Wilson
 */
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    Collection<RatingEntity> findAllById(@Nonnull Collection<Integer> ids);

    @Query("SELECT r FROM RatingEntity r WHERE r.name LIKE :name")
    RatingEntity findByName(@Nonnull @Param("name") String name);

}
