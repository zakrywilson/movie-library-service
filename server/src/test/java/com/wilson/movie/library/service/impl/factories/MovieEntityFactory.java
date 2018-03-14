package com.wilson.movie.library.service.impl.factories;

import com.wilson.movie.library.domain.MovieEntity;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Provides methods for creating random movie JPA entities.
 *
 * @author Zach Wilson
 */
public final class MovieEntityFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private MovieEntityFactory() {
    }

    /**
     * Generates a new {@code MovieEntity}.
     *
     * @return a new movie.
     *
     * @see #generateRandomMovieTitle()
     * @see #generateRandomStudio()
     * @see IdentityEntityFactory#generateRandomRating()
     * @see IdentityEntityFactory#generateRandomGenre()
     * @see #generateRandomPlotSummary()
     * @see #generateRandomNotes()
     * @see RandomValueFactory#nextIntId()
     */
    public static MovieEntity generateRandomMovie() {
        MovieEntity movie = new MovieEntity(generateRandomMovieTitle(),
                                            LocalDate.now(),
                                            generateRandomStudio(),
                                            IdentityEntityFactory.generateRandomRating(),
                                            IdentityEntityFactory.generateRandomGenre(),
                                            IdentityEntityFactory.generateRandomLanguage(),
                                            generateRandomPlotSummary(),
                                            generateRandomNotes());
        movie.setId(RandomValueFactory.nextIntId());
        return movie;
    }

    /**
     * Generates a random string between 1 and 20 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomMovieTitle() {
        return RandomValueFactory.generateRandomString(1, 20);
    }

    /**
     * Generates a random string between 1 and 1,000 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomStudio() {
        return RandomValueFactory.generateRandomString(1, 1_000);
    }

    /**
     * Return either null or generates a random string between 1 and 1,024 characters, inclusive.
     * <p>
     * A random boolean is generated to determine whether a null should be returned or a string
     * should be generated.
     *
     * @return a new string or null.
     */
    @Nullable
    public static String generateRandomPlotSummary() {
        return RandomValueFactory.nextBoolean()
                ? RandomValueFactory.generateRandomString(0, RandomValueFactory.nextInt(1024))
                : null;
    }

    /**
     * Return either null or generates a random string between 1 and 4,096 characters, inclusive.
     * <p>
     * A random boolean is generated to determine whether a null should be returned or a string
     * should be generated.
     *
     * @return a new string or null.
     */
    @Nullable
    public static String generateRandomNotes() {
        return RandomValueFactory.nextBoolean()
                ? RandomValueFactory.generateRandomString(0, RandomValueFactory.nextInt(4096))
                : null;
    }

}
