package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Repository for {@link LanguageEntity}.
 *
 * @author Zach Wilson
 */
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    Collection<LanguageEntity> findAllById(@Nonnull Collection<Integer> ids);

    @Query("SELECT l FROM Language l WHERE l.name LIKE :name")
    LanguageEntity findByName(@Nonnull @Param("name") String name);

}
