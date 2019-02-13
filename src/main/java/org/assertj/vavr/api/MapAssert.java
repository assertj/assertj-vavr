package org.assertj.vavr.api;

import io.vavr.collection.Map;

/**
 * Assertions for {@link io.vavr.collection.Map}.
 *
 * @param <KEY>  key type of the {@link Map}.
 * @param <VALUE> value type of the {@link Map}.
 * @author Michał Chmielarz
 */
public class MapAssert<KEY, VALUE> extends AbstractMapAssert<MapAssert<KEY, VALUE>, Map<KEY, VALUE>, KEY, VALUE> {
    MapAssert(Map<KEY, VALUE> actual) {
        super(actual, MapAssert.class);
    }

}
