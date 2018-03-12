package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Repository for {@link GenreEntity}.
 *
 * @author Zach Wilson
 */
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    Collection<GenreEntity> findAllById(@Nonnull Collection<Integer> ids);

    @Query("SELECT g FROM GenreEntity g WHERE g.name LIKE :name")
    GenreEntity findByName(@Nonnull @Param("name") String name);

}
