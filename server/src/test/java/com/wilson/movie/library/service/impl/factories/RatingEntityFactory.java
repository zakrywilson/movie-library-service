package com.wilson.movie.library.service.impl.factories;

import com.wilson.movie.library.domain.RatingEntity;

/**
 * Factory for creating random {@link RatingEntity}s.
 *
 * @author Zach Wilson
 */
public final class RatingEntityFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private RatingEntityFactory() {
    }

    /**
     * Generates a random string between 1 and 100 characters, inclusive.
     *
     * @return a new string.
     */
    public static String generateRandomRatingName() {
        return RandomValueFactory.generateRandomString(1, 100);
    }

    /**
     * Generates a random {@link RatingEntity}.
     *
     * @return a new rating.
     */
    public static RatingEntity generateRandomRating() {
        RatingEntity rating = new RatingEntity(
                RandomValueFactory.generateRandomString(1, 100),
                RandomValueFactory.generateRandomString(1, 200));
        rating.setId(RandomValueFactory.nextIntId());
        return rating;
    }

}
