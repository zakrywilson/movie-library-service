package com.wilson.movie.library.resource.utils;

import com.wilson.movie.library.domain.*;
import com.wilson.movie.library.resource.model.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Adapts shared DTO (data transfer objects) to local JPA entities.
 *
 * @author Zach Wilson
 */
public final class Adapters {

    /**
     * No instances of this class should be constructed: all methods intended for external use are
     * <i>static</i>.
     */
    private Adapters() {
    }

    @Nullable
    public static Movie toMovie(@Nullable MovieEntity movie) {
        if (movie == null) {
            return null;
        }

        return Movie.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .studio(movie.getStudio())
                .rating(movie.getRating().getName())
                .genre(movie.getGenre().getName())
                .language(movie.getLanguage().getName())
                .plotSummary(movie.getPlotSummary())
                .notes(movie.getNotes())
                .build();
    }

    @Nullable
    public static MovieEntity toMovie(@Nullable Movie movie, @Nullable Rating rating,
            @Nullable Genre genre, @Nullable Language language) {
        if (movie == null || rating == null || genre == null || language == null) {
            return null;
        }

        return new MovieEntity(movie.getTitle(),
                               movie.getReleaseDate(),
                               movie.getStudio(),
                               toRating(rating),
                               toGenre(genre),
                               toLanguage(language),
                               movie.getPlotSummary(),
                               movie.getNotes());
    }

    @Nonnull
    public static Collection<Movie> toMovies(@Nonnull Collection<MovieEntity> movies) {
        return movies.stream().map(Adapters::toMovie).collect(Collectors.toList());
    }

    @Nullable
    public static TvShow toTvShow(@Nullable TvShowEntity tvShow) {
        if (tvShow == null) {
            return null;
        }

        return TvShow.builder()
                .id(tvShow.getId())
                .title(tvShow.getTitle())
                .dateAired(tvShow.getDateAired())
                .network(tvShow.getNetwork())
                .rating(tvShow.getRating().getName())
                .genre(tvShow.getGenre().getName())
                .language(tvShow.getLanguage().getName())
                .plotSummary(tvShow.getPlotSummary())
                .series(tvShow.isSeries())
                .build();
    }

    @Nullable
    public static TvShowEntity toTvShow(@Nullable TvShow tvShow, @Nullable Rating rating,
            @Nullable Genre genre, @Nullable Language language) {
        if (tvShow == null || rating == null || genre == null || language == null) {
            return null;
        }

        return new TvShowEntity(tvShow.getTitle(),
                                tvShow.getDateAired(),
                                tvShow.getNetwork(),
                                toRating(rating),
                                toGenre(genre),
                                toLanguage(language),
                                tvShow.getPlotSummary(),
                                tvShow.isSeries());
    }

    @Nonnull
    public static Collection<TvShow> toTvShows(@Nonnull Collection<TvShowEntity> tvShows) {
        return tvShows.stream().map(Adapters::toTvShow).collect(Collectors.toList());
    }

    @Nullable
    public static Rating toRating(@Nullable RatingEntity rating) {
        if (rating == null) {
            return null;
        }

        return Rating.builder()
                .id(rating.getId())
                .name(rating.getName())
                .description(rating.getDescription())
                .build();
    }

    @Nullable
    public static RatingEntity toRating(@Nullable Rating rating) {
        if (rating == null) {
            return null;
        }

        return new RatingEntity(rating.getName(), rating.getDescription());
    }

    @Nonnull
    public static Collection<Rating> toRatings(@Nonnull Collection<RatingEntity> ratings) {
        return ratings.stream().map(Adapters::toRating).collect(Collectors.toList());
    }

    @Nullable
    public static Genre toGenre(@Nullable GenreEntity rating) {
        if (rating == null) {
            return null;
        }

        return Genre.builder()
                .id(rating.getId())
                .name(rating.getName())
                .description(rating.getDescription())
                .build();
    }

    @Nullable
    public static GenreEntity toGenre(@Nullable Genre rating) {
        if (rating == null) {
            return null;
        }

        return new GenreEntity(rating.getName(), rating.getDescription());
    }

    @Nonnull
    public static Collection<Genre> toGenres(@Nonnull Collection<GenreEntity> ratings) {
        return ratings.stream().map(Adapters::toGenre).collect(Collectors.toList());
    }

    @Nullable
    public static Language toLanguage(@Nullable LanguageEntity rating) {
        if (rating == null) {
            return null;
        }

        return Language.builder()
                .id(rating.getId())
                .name(rating.getName())
                .description(rating.getDescription())
                .build();
    }

    @Nullable
    public static LanguageEntity toLanguage(@Nullable Language rating) {
        if (rating == null) {
            return null;
        }

        return new LanguageEntity(rating.getName(), rating.getDescription());
    }

    @Nonnull
    public static Collection<Language> toLanguages(@Nonnull Collection<LanguageEntity> ratings) {
        return ratings.stream().map(Adapters::toLanguage).collect(Collectors.toList());
    }

    @Nullable
    public static Person toPerson(@Nullable PersonEntity person) {
        if (person == null) {
            return null;
        }

        return Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .middleName(person.getMiddleName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth())
                .dateOfDeath(person.getDateOfDeath())
                .build();
    }

    @Nullable
    public static PersonEntity toPerson(@Nullable Person person) {
        if (person == null) {
            return null;
        }

        return new PersonEntity(person.getFirstName(),
                                person.getMiddleName(),
                                person.getLastName(),
                                person.getDateOfBirth(),
                                person.getDateOfDeath());
    }

    @Nonnull
    public static Collection<Person> toPersons(@Nonnull Collection<PersonEntity> persons) {
        return persons.stream().map(Adapters::toPerson).collect(Collectors.toList());
    }

}
