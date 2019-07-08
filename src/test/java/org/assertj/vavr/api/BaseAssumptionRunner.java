package org.assertj.vavr.api;

import java.util.function.Consumer;

class BaseAssumptionRunner<T> extends AssumptionRunner<T> {

    protected final T actual;
    private Consumer<T> failingAssumption;
    private Consumer<T> passingAssumption;

    BaseAssumptionRunner() {
        this.actual = null;
    }

    static <T> AssumptionRunner<T> assumptionRunner(T actual, Consumer<T> passingAssumption, Consumer<T> failingAssumption) {
        return new BaseAssumptionRunner<>(actual, passingAssumption, failingAssumption);
    }

    BaseAssumptionRunner(T actual, Consumer<T> passingAssumption, Consumer<T> failingAssumption) {
        this.actual = actual;
        this.passingAssumption = passingAssumption;
        this.failingAssumption = failingAssumption;
    }

    @Override
    protected void runFailingAssumption() {
        failingAssumption.accept(actual);
    }

    @Override
    protected void runPassingAssumption() {
        passingAssumption.accept(actual);
    }

}