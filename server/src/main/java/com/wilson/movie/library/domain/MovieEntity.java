package com.wilson.movie.library.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

/**
 * Movie JPA entity.
 *
 * @author Zach Wilson
 */
@Table(name = "MOVIE")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity = auto increment
    private int id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "RELEASE_DATE", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "STUDIO", nullable = false, length = 100)
    private String studio;

    @Column(name = "PLOT_SUMMARY", length = 1024)
    private String plotSummary;

    @Column(name = "NOTES", length = 4096)
    private String notes;

    public MovieEntity(@NotNull String title, @NotNull LocalDate releaseDate, @NotNull String studio) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.studio = studio;
    }

    public MovieEntity(@NotNull String title, @NotNull LocalDate releaseDate, @NotNull String studio,
            @Null String plotSummary, @Null String notes) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.studio = studio;
        this.plotSummary = plotSummary;
        this.notes = notes;
    }

}
