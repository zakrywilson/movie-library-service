package com.wilson.movie.library.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * TV show JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "TV_SHOW")
@Entity(name = "TvShow")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TvShowEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @Column(name = "DATE_AIRED", nullable = false)
    private LocalDate dateAired;

    @Column(name = "NETWORK", nullable = false, length = 100)
    private String network;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "RATING_ID", nullable = false)
    private RatingEntity rating;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "GENRE_ID", nullable = false)
    private GenreEntity genre;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    private LanguageEntity language;

    @Column(name = "PLOT_SUMMARY", nullable = false, length = 4096)
    private String plotSummary;

    @Column(name = "IS_SERIES", nullable = false)
    private boolean series;

    public TvShowEntity(@NotNull String title, @NotNull LocalDate dateAired, @NotNull String network,
            @NotNull RatingEntity rating, @NotNull GenreEntity genre, @NotNull LanguageEntity language,
            @NotNull String plotSummary, @NotNull Boolean series) {
        this.title = title;
        this.dateAired = dateAired;
        this.network = network;
        this.rating = rating;
        this.genre = genre;
        this.language = language;
        this.plotSummary = plotSummary;
        this.series = series;
    }

}
