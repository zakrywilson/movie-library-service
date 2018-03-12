package com.wilson.movie.library.service.impl.factories;

import java.util.Random;

/**
 * Factory for creating random values such as strings and numbers.
 *
 * @author Zach Wilson
 */
public final class RandomValueFactory {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private RandomValueFactory() {
    }

    /**
     * Returns a value between {@code 1} and {@code Integer.MAX_VALUE}.
     *
     * @return the next pseudo-random, uniformly distributed int value between {@code 1} and
     * {@code Integer.MAX_VALUE}
     */
    public static int nextIntId() {
        return rand.nextInt(Integer.MAX_VALUE - 1) + 1;
    }

    /**
     * Simply returns the result of {@link Random#nextInt(int)} without the caller having to
     * initialize {@link Random}.
     *
     * @param bound the upper bound (exclusive). Must be positive.
     * @return the next pseudo-random, uniformly distributed int value between zero (inclusive) and
     * bound (exclusive) from this random number generator's sequence.
     */
    public static int nextInt(int bound) {
        return rand.nextInt(bound);
    }

    /**
     * Simply returns the result of {@link Random#nextBoolean()} without the caller having to
     * initialize {@link Random}.
     *
     * @return the next pseudo-random, uniformly distributed boolean value from this random number
     * generator's sequence
     */
    public static boolean nextBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Generates a random string between {@code maxCharacters} and {@code minCharacters}.
     *
     * @param minCharacters the minimum number of characters in the returned string, inclusive.
     * @param maxCharacters the maximum number of characters in the returned string, inclusive.
     * @return a random string of characters between {@code minCharacters} and {@code maxCharacters}.
     */
    public static String generateRandomString(int minCharacters, int maxCharacters) {
        // Offset by +1 since Random#nextInt(int) is exclusive on the upper bound and this method
        // is inclusive.
        int characterCount = rand.nextInt(maxCharacters - minCharacters + 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < characterCount; i++) {
            sb.append(getRandomCharacter());
        }

        return sb.toString();
    }

    /**
     * Returns a random alphanumeric character (plus some punctuation characters).
     *
     * @return a random character.
     */
    private static char getRandomCharacter() {
        return acceptableChars[rand.nextInt(acceptableChars.length)];
    }

    private static final Random rand = new Random(System.nanoTime());
    private static final char[] acceptableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .,:;-_".toCharArray();

}
