package org.assertj.vavr.api;

abstract class AssumptionRunner<T> {

  protected final T actual;

  AssumptionRunner() {
    this.actual = null;
  }

  AssumptionRunner(T actual) {
    this.actual = actual;
  }

  protected abstract void runFailingAssumption();

  protected abstract void runPassingAssumption();
}