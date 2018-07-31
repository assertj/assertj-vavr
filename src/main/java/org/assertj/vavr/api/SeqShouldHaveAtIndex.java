package org.assertj.vavr.api;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

import io.vavr.collection.Seq;

/**
 * Builds error message when a given condition is not met at specified index in actual {@link io.vavr.collection.Seq}
 *
 * @author Michał Chmielarz
 */
public class SeqShouldHaveAtIndex extends BasicErrorMessageFactory {

  private <T> SeqShouldHaveAtIndex(Seq<? extends T> actual, Condition<? super T> condition, Index index, T found) {
    super("%nExpecting:%n <%s>%nat index <%s> to have:%n <%s>%nin:%n <%s>%n", found, index.value, condition, actual);
  }

  public static <T> ErrorMessageFactory shouldHaveAtIndex(Seq<? extends T> actual, Condition<? super T> condition, Index index, T found) {
    return new SeqShouldHaveAtIndex(actual, condition, index, found);
  }
}