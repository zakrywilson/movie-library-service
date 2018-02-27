package com.wilson.movie.library.resource.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDate;

/**
 * De-serializes shared JSON objects to their local Java equivalents.
 *
 * @author Zach Wilson
 */
public final class Deserializers {

    /**
     * No instances of this class should be constructed: all methods and classes intended for
     * external use are <i>static</i>.
     */
    private Deserializers() {
    }

    public static final class LocalDateDeserializer extends StdDeserializer<LocalDate> {

        public LocalDateDeserializer() {
            this(null);
        }

        public LocalDateDeserializer(@Nullable Class<?> aClass) {
            super(aClass);
        }

        @Override
        public LocalDate deserialize(@Nonnull JsonParser jsonParser, @Nonnull DeserializationContext context) throws IOException {
            return LocalDate.parse(jsonParser.getText());
        }
    }
}
