package org.assertj.vavr.api;

import io.vavr.collection.Multimap;

/**
 * Assertions for {@link io.vavr.collection.Multimap}.
 *
 * @param <KEY>   key type of the {@link Multimap}.
 * @param <VALUE> value type of the {@link Multimap}.
 * @author Michał Chmielarz
 */
public class MultimapAssert<KEY, VALUE> extends AbstractMultimapAssert<MultimapAssert<KEY, VALUE>, Multimap<KEY, VALUE>, KEY, VALUE> {

    MultimapAssert(Multimap<KEY, VALUE> multimap) {
        super(multimap, MultimapAssert.class);
    }

}
