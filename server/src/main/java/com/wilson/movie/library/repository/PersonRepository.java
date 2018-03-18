package com.wilson.movie.library.repository;

import com.wilson.movie.library.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Repository for {@link PersonEntity}.
 *
 * @author Zach Wilson
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE :first")
    Collection<PersonEntity> findAllByFirstName(@Nonnull @Param("first") String firstName);

    @Query("SELECT p FROM Person p WHERE p.middleName LIKE :middle")
    Collection<PersonEntity> findAllByMiddleName(@Nonnull @Param("middle") String middleName);

    @Query("SELECT p FROM Person p WHERE p.lastName LIKE :last")
    Collection<PersonEntity> findAllByLastName(@Nonnull @Param("last") String lastName);

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE :first AND p.lastName LIKE :last")
    Collection<PersonEntity> findAllByName(@Nonnull @Param("first") String firstName,
                                           @Nonnull @Param("last") String lastName);

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE :first AND p.middleName LIKE :middle AND p.lastName LIKE :last")
    Collection<PersonEntity> findAllByName(@Nonnull @Param("first") String firstName,
                                           @Nonnull @Param("middle") String middleName,
                                           @Nonnull @Param("last") String lastName);

    Collection<PersonEntity> findAllById(@Nonnull Collection<Integer> ids);

    Collection<PersonEntity> findAllByDateOfBirth(@Nonnull LocalDate date);

    Collection<PersonEntity> findAllByDateOfDeath(@Nonnull LocalDate date);

}
