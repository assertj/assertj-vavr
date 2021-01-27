/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2017-2021 the original author or authors.
 */
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
