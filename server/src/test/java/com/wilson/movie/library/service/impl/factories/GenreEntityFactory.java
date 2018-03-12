package com.wilson.movie.library.service.impl.factories;

import com.wilson.movie.library.domain.GenreEntity;

/**
 * Factory for creating random {@link GenreEntity}s.
 *
 * @author Zach Wilson
 */
public final class GenreEntityFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private GenreEntityFactory() {
    }

    /**
     * Generates a random string between 1 and 100 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomGenreName() {
        return RandomValueFactory.generateRandomString(1, 100);
    }

    /**
     * Generates a random {@link GenreEntity}.
     *
     * @return a new rating.
     */
    public static GenreEntity generateRandomGenre() {
        GenreEntity rating = new GenreEntity(
                RandomValueFactory.generateRandomString(1, 100),
                RandomValueFactory.generateRandomString(1, 200));
        rating.setId(RandomValueFactory.nextIntId());
        return rating;
    }

}
