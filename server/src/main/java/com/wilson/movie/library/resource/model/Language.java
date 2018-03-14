package com.wilson.movie.library.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * Immutable language.
 *
 * @author Zach Wilson
 */
@JsonRootName("language")
@Value
@Builder
public final class Language {

    @JsonProperty("id")
    @Wither
    private final int id;

    @JsonProperty("name")
    @Wither
    private final String name;

    @JsonProperty("description")
    @Wither
    private final String description;

}
