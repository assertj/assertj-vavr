package org.assertj.vavr.api;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Objects;

interface AbstractVavrAssert<SELF, ACTUAL> {

    ACTUAL actual();

    SELF withAssertionState(@SuppressWarnings("rawtypes") AbstractVavrAssert assertInstance);

    Objects objects();

    WritableAssertionInfo info();

}
