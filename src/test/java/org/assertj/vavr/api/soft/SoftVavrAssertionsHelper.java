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
package org.assertj.vavr.api.soft;

import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.vavr.api.VavrAssertions;
import org.opentest4j.MultipleFailuresError;

import static java.lang.String.format;
import static java.util.Comparator.naturalOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;

class SoftVavrAssertionsHelper {

    static void verifyErrors(Double evaluatedVal, MultipleFailuresError e) {
        List<String> errors = List.ofAll(e.getFailures()).map(Object::toString);
        VavrAssertions.assertThat(errors).hasSize(107);

        assertThat(errors.get(0))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Some(value)>%nto contain:%n  <\"\">%nbut did not."));
        assertThat(errors.get(1))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Some>%nto contain a value that is an instance of:%n <java.lang.Integer>%nbut did contain an instance of:%n <java.lang.String>"));
        assertThat(errors.get(2))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Some(value)>%nto contain the instance (i.e. compared with ==):%n  <\"value\">%nbut did not."));
        assertThat(errors.get(3))
                .contains(format("org.opentest4j.AssertionFailedError: %nExpecting:%n <\"value\">%nto be equal to:%n <\"xyz\">%nbut was not."));
        assertThat(errors.get(4))
                .contains(format("java.lang.AssertionError: %nExpecting an Option to be empty but was <\"value\">."));
        assertThat(errors.get(5))
                .contains(format("java.lang.AssertionError: %nExpecting Option to contain a value but it didn't."));
        assertThat(errors.get(6))
                .contains(format("java.lang.AssertionError: %nExpecting Lazy to be not evaluated but it was <Lazy(" + evaluatedVal + ")>"));
        assertThat(errors.get(7))
                .contains(format("java.lang.AssertionError: %nExpecting Lazy to be evaluated, but it was not"));
        assertThat(errors.get(8))
                .contains(format("java.lang.AssertionError: %nExpecting all elements of:%n  <HashSet(Frodo, Bilbo)>%nto satisfy given requirements, but these elements did not:%n%n  <\"Bilbo\">%nerror: %nExpecting:%n <\"Bilbo\">%nto start with:%n <\"F\">%n"));
        assertThat(errors.get(9))
                .contains(format("java.lang.AssertionError: %nExpected size:<1> but was:<2> in:%n<HashSet(Frodo, Bilbo)>"));
        assertThat(errors.get(10))
                .contains(format("java.lang.AssertionError: %nExpecting empty but was:<HashSet(Frodo, Bilbo)>"));
        assertThat(errors.get(11))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <\"Frodo\">%nat index <1> but found:%n <\"Bilbo\">%nin:%n <List(Frodo, Bilbo)>%n"));
        assertThat(errors.get(12))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <List(Frodo, Bilbo)>%nnot to contain:%n <\"Frodo\">%nat index <0>%n"));
        assertThat(errors.get(13))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <\"Bilbo\">%nat index <1> to have:%n <starts with some>%nin:%n <List(Frodo, Bilbo)>%n"));
        assertThat(errors.get(14))
                .contains(format("java.lang.AssertionError: %nsequence is not sorted because element 0:%n <\"Frodo\">%nis not less or equal than element 1:%n <\"Bilbo\">%nsequence was:%n <List(Frodo, Bilbo)>"));
        assertThat(errors.get(15))
                .contains(format("java.lang.AssertionError: %nsequence is not sorted according to INSTANCE comparator because element 0:%n <\"Frodo\">%nis not less or equal than element 1:%n <\"Bilbo\">%nsequence was:%n <List(Frodo, Bilbo)>"));
        assertThat(errors.get(16))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <\"Frodo\">%nto start with:%n <\"some\">%n"));
        assertThat(errors.get(17))
                .contains(format("java.lang.AssertionError: %nExpecting a Validation to be invalid but was valid <\"ok\">."));
        assertThat(errors.get(18))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Valid(ok)>%nto contain:%n  <\"not ok\">%nbut did not."));
        assertThat(errors.get(19))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Valid>%nto contain a value that is an instance of:%n <java.lang.Integer>%nbut did contain an instance of:%n <java.lang.String>"));
        assertThat(errors.get(20))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Valid(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"not ok\">%nbut did not."));
        assertThat(errors.get(21))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Valid(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"not ok\">%nbut did not."));
        assertThat(errors.get(22))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Valid(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"not ok\">%nbut did not."));
        assertThat(errors.get(23))
                .contains(format("java.lang.AssertionError: %nExpecting a Validation to be valid but was invalid <\"not ok\">."));
        assertThat(errors.get(24))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Invalid(not ok)>%nto contain:%n  <\"ok\">%nbut did not."));
        assertThat(errors.get(25))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Invalid>%nto contain a value that is an instance of:%n <java.lang.Integer>%nbut did contain an instance of:%n <java.lang.String>"));
        assertThat(errors.get(26))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Invalid(not ok)>%nto contain the instance (i.e. compared with ==):%n  <\"ok\">%nbut did not."));
        assertThat(errors.get(27))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Invalid(not ok)>%nto contain the instance (i.e. compared with ==):%n  <\"ok\">%nbut did not."));
        assertThat(errors.get(28))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Invalid(not ok)>%nto contain the instance (i.e. compared with ==):%n  <\"ok\">%nbut did not."));
        assertThat(errors.get(29))
                .contains(format("java.lang.AssertionError: %nExpecting Try to be a Failure, but wasn't"));
        assertThat(errors.get(30))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Success(ok)>%nto contain:%n  <\"not ok\">%nbut did not."));
        assertThat(errors.get(31))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Success(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"nok\">%nbut did not."));
        assertThat(errors.get(32))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Success(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"nok\">%nbut did not."));
        assertThat(errors.get(33))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Success>%nto contain a value that is an instance of:%n <java.lang.Integer>%nbut did contain an instance of:%n <java.lang.String>"));
        assertThat(errors.get(34))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Success(ok)>%nto contain the instance (i.e. compared with ==):%n  <\"not same\">%nbut did not."));
        assertThat(errors.get(35))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <\"ok\">%nto be <starts with some>"));
        assertThat(errors.get(36))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <\"ok\">%nto start with:%n <\"some\">%n"));
        assertThat(errors.get(37))
                .startsWith(format("java.lang.AssertionError: %nExpecting Try to be a Success, but was a Failure:%n- exception class: java.lang.IllegalStateException%n- message: not ok"));
        assertThat(errors.get(38))
                .startsWith(format("java.lang.AssertionError: %nExpecting:%n  <java.lang.IllegalStateException: not ok>%nto be an instance of:%n  <java.lang.NullPointerException>"));
        assertThat(errors.get(39))
                .startsWith(format("org.opentest4j.AssertionFailedError: %nExpecting message to be:%n  <\"another message\">%nbut was:%n  <\"not ok\">"));
        assertThat(errors.get(40))
                .contains(format("java.lang.AssertionError: %nExpecting an Either to be left but was <Right(2)>."));
        assertThat(errors.get(41))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Right(2)>%nto contain:%n  <1> on the [RIGHT]%nbut did not."));
        assertThat(errors.get(42))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Right(2)>%nto contain the instance (i.e. compared with ==):%n  <1>%nbut did not."));
        assertThat(errors.get(43))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Right>%nto contain a value that is an instance of:%n <java.lang.String>%nbut did contain an instance of:%n <java.lang.Integer>"));
        assertThat(errors.get(44))
                .contains(format("org.opentest4j.AssertionFailedError: %nExpecting:%n <2>%nto be equal to:%n <0>%nbut was not."));
        assertThat(errors.get(45))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Right(2)>%nto contain:%n  <3> on the [RIGHT]%nbut did not."));
        assertThat(errors.get(46))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Right(2)>%nto contain:%n  <3> on the [RIGHT]%nbut did not."));
        assertThat(errors.get(47))
                .contains(format("java.lang.AssertionError: %nExpecting an Either to be right but was <Left(not ok)>."));
        assertThat(errors.get(48))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Left(not ok)>%nto contain:%n  <\"ok\"> on the [LEFT]%nbut did not."));
        assertThat(errors.get(49))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <Left(not ok)>%nto contain the instance (i.e. compared with ==):%n  <\"ok\">%nbut did not."));
        assertThat(errors.get(50))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <Left>%nto contain a value that is an instance of:%n <java.lang.Integer>%nbut did contain an instance of:%n <java.lang.String>"));
        assertThat(errors.get(51))
                .contains(format("java.lang.AssertionError: %nExpected size:<1> but was:<6> in:%n<\"not ok\">"));
        assertThat(errors.get(52))
                .contains(format("java.lang.AssertionError: %nExpecting an Either to be left but was <Right(2)>."));
        assertThat(errors.get(53))
                .contains(format("java.lang.AssertionError: %nExpecting an Either to be left but was <Right(2)>."));
        assertThat(errors.get(54))
                .contains(format("java.lang.AssertionError: %nExpecting actual not to be empty"));
        assertThat(errors.get(55))
                .contains(format("java.lang.AssertionError: %nExpecting empty but was:<HashMap((Frodo, Baggins))>"));
        assertThat(errors.get(56))
                .startsWith(format("java.lang.AssertionError: %nExpecting:%n <\"Frodo\">%nto start with:%n <\"key\">"));
        assertThat(errors.get(57))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nto contain at least one of the following elements:%n  <[(Bilbo, Baggins)]>%nbut none were found "));
        assertThat(errors.get(58))
                .contains(format("java.lang.AssertionError: %nExpecting HashMap:%n <HashMap((Frodo, Baggins))>%nto contain:%n <[(Peregrin, Took)]>%nbut could not find the following element(s):%n <HashSet((Peregrin, Took))>%n"));
        assertThat(errors.get(59))
                .contains(format("java.lang.AssertionError: %nExpecting HashMap:%n <HashMap((Frodo, Baggins))>%nto contain:%n <[(Bilbo, Baggins)]>%nbut could not find the following element(s):%n <HashSet((Bilbo, Baggins))>%n"));
        assertThat(errors.get(60))
                .contains(format("java.lang.AssertionError: %nExpecting HashMap:%n <HashMap((Frodo, Baggins))>%nto contain:%n <[(Bilbo, Baggins)]>%nbut could not find the following element(s):%n <HashSet((Bilbo, Baggins))>%n"));
        assertThat(errors.get(61))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nto contain exactly (and in same order):%n  <List((Peregrin, Took))>%nbut some elements were not found:%n  <LinkedHashMap((Peregrin, Took))>%nand others were not expected:%n  <HashMap((Frodo, Baggins))>%n"));
        assertThat(errors.get(62))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <HashMap((Frodo, Baggins))>%nto contain key:%n <\"Peregrin\">"));
        assertThat(errors.get(63))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <HashMap((Frodo, Baggins))>%nto contain keys:%n <[\"Bilbo\", \"Peregrin\"]>"));
        assertThat(errors.get(64))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nto contain value:%n  <\"Took\">"));
        assertThat(errors.get(65))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nto contain values:%n  <[\"Took\", \"Brandybuck\"]>"));
        assertThat(errors.get(66))
                .contains(format("java.lang.AssertionError: %nExpecting%n <HashMap((Frodo, Baggins))>%nnot to contain%n <[(Frodo, Baggins)]>%nbut found%n <HashSet((Frodo, Baggins))>%n"));
        assertThat(errors.get(67))
                .contains(format("java.lang.AssertionError: %nExpecting%n <HashMap((Frodo, Baggins))>%nnot to contain%n <[(Frodo, Baggins)]>%nbut found%n <HashSet((Frodo, Baggins))>%n"));
        assertThat(errors.get(68))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nnot to contain key:%n  <\"Frodo\">"));
        assertThat(errors.get(69))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nnot to contain key:%n  <\"Frodo\">"));
        assertThat(errors.get(70))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nnot to contain value:%n  <\"Baggins\">"));
        assertThat(errors.get(71))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMap((Frodo, Baggins))>%nnot to contain values:%n  <[\"Baggins\"]>"));
        assertThat(errors.get(72))
                .startsWith(format("java.lang.AssertionError: %nExpecting elements:%n<Some(Baggins)>%n of %n<HashMap((Frodo, Baggins))>%n to be <starts with some>"));
        assertThat(errors.get(73))
                .contains(format("java.lang.AssertionError: %nActual and expected should have same size but actual size is:%n <1>%nwhile expected size is:%n <0>%nActual was:%n HashMap((Frodo, Baggins))%nExpected was:%n []"));
        assertThat(errors.get(74))
                .contains(format("java.lang.AssertionError: %nActual and expected should have same size but actual size is:%n <1>%nwhile expected size is:%n <0>%nActual was:%n HashMap((Frodo, Baggins))%nExpected was:%n List()"));
        assertThat(errors.get(75))
                .contains(format("java.lang.AssertionError: %nExpected size:<0> but was:<1> in:%n<HashMap((Frodo, Baggins))>"));
        assertThat(errors.get(76))
                .contains(format("java.lang.AssertionError: %nExpecting size of:%n  <HashMap((Frodo, Baggins))>%nto be greater than 2 but was 1"));
        assertThat(errors.get(77))
                .contains(format("java.lang.AssertionError: %nExpecting size of:%n  <HashMap((Frodo, Baggins))>%nto be less than 1 but was 1"));
        assertThat(errors.get(78))
                .contains(format("org.opentest4j.AssertionFailedError: %nExpecting:%n <HashMap((Frodo, Baggins))>%nto be equal to:%n <HashMap()>%nbut was not."));
        assertThat(errors.get(79))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <HashMap((Frodo, Baggins))>%nnot to be equal to:%n <HashMap((Frodo, Baggins))>%n"));
        assertThat(errors.get(80))
                .contains(format("java.lang.AssertionError: %nExpecting null or empty but was:<HashMap((Frodo, Baggins))>"));
        assertThat(errors.get(81))
                .contains(format("java.lang.AssertionError: %nExpecting actual not to be empty"));
        assertThat(errors.get(82))
                .contains(format("java.lang.AssertionError: %nExpecting empty but was:<HashMultimap[List]((Frodo, Baggins))>"));
        assertThat(errors.get(83))
                .startsWith(format("java.lang.AssertionError: %nExpecting:%n <\"Frodo\">%nto start with:%n <\"key\">"));
        assertThat(errors.get(84))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nto contain at least one of the following elements:%n  <[(Bilbo, Baggins)]>%nbut none were found "));
        assertThat(errors.get(85))
                .contains(format("java.lang.AssertionError: %nExpecting HashMultimap:%n <HashMultimap[List]((Frodo, Baggins))>%nto contain:%n <[(Peregrin, Took)]>%nbut could not find the following element(s):%n <HashSet((Peregrin, Took))>%n"));
        assertThat(errors.get(86))
                .contains(format("java.lang.AssertionError: %nExpecting HashMultimap:%n <HashMultimap[List]((Frodo, Baggins))>%nto contain:%n <[(Bilbo, Baggins)]>%nbut could not find the following element(s):%n <HashSet((Bilbo, Baggins))>%n"));
        assertThat(errors.get(87))
                .contains(format("java.lang.AssertionError: %nExpecting HashMultimap:%n <HashMultimap[List]((Frodo, Baggins))>%nto contain:%n <[(Bilbo, Baggins)]>%nbut could not find the following element(s):%n <HashSet((Bilbo, Baggins))>%n"));
        assertThat(errors.get(88))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nto contain exactly (and in same order):%n  <List((Peregrin, Took))>%nbut some elements were not found:%n  <LinkedHashMultimap[List]((Peregrin, Took))>%nand others were not expected:%n  <HashMultimap[List]((Frodo, Baggins))>%n"));
        assertThat(errors.get(89))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <HashMultimap[List]((Frodo, Baggins))>%nto contain key:%n <\"Peregrin\">"));
        assertThat(errors.get(90))
                .contains(format("java.lang.AssertionError: %nExpecting:%n <HashMultimap[List]((Frodo, Baggins))>%nto contain keys:%n <[\"Bilbo\", \"Peregrin\"]>"));
        assertThat(errors.get(91))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nto contain value:%n  <\"Took\">"));
        assertThat(errors.get(92))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nto contain values:%n  <[\"Took\", \"Brandybuck\"]>"));
        assertThat(errors.get(93))
                .contains(format("java.lang.AssertionError: %nExpecting%n <HashMultimap[List]((Frodo, Baggins))>%nnot to contain%n <[(Frodo, Baggins)]>%nbut found%n <HashSet((Frodo, Baggins))>%n"));
        assertThat(errors.get(94))
                .contains(format("java.lang.AssertionError: %nExpecting%n <HashMultimap[List]((Frodo, Baggins))>%nnot to contain%n <[(Frodo, Baggins)]>%nbut found%n <HashSet((Frodo, Baggins))>%n"));
        assertThat(errors.get(95))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nnot to contain key:%n  <\"Frodo\">"));
        assertThat(errors.get(96))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nnot to contain key:%n  <\"Frodo\">"));
        assertThat(errors.get(97))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nnot to contain value:%n  <\"Baggins\">"));
        assertThat(errors.get(98))
                .contains(format("java.lang.AssertionError: %nExpecting:%n  <HashMultimap[List]((Frodo, Baggins))>%nnot to contain values:%n  <[\"Baggins\"]>"));
        assertThat(errors.get(99))
                .startsWith(format("java.lang.AssertionError: %nExpecting elements:%n<Some(List(Baggins))>%n of %n<HashMultimap[List]((Frodo, Baggins))>%n to be <starts with some>"));
        assertThat(errors.get(100))
                .contains(format("java.lang.AssertionError: %nActual and expected should have same size but actual size is:%n <1>%nwhile expected size is:%n <0>%nActual was:%n HashMultimap[List]((Frodo, Baggins))%nExpected was:%n []"));
        assertThat(errors.get(101))
                .contains(format("java.lang.AssertionError: %nActual and expected should have same size but actual size is:%n <1>%nwhile expected size is:%n <0>%nActual was:%n HashMultimap[List]((Frodo, Baggins))%nExpected was:%n List()"));
        assertThat(errors.get(102))
                .contains(format("java.lang.AssertionError: %nExpected size:<0> but was:<1> in:%n<HashMultimap[List]((Frodo, Baggins))>"));
        assertThat(errors.get(103))
                .contains(format("java.lang.AssertionError: %nExpecting size of:%n  <HashMultimap[List]((Frodo, Baggins))>%nto be greater than 2 but was 1"));
        assertThat(errors.get(104))
                .contains(format("java.lang.AssertionError: %nExpecting size of:%n  <HashMultimap[List]((Frodo, Baggins))>%nto be less than 1 but was 1"));
        assertThat(errors.get(105))
                .contains(format("org.opentest4j.AssertionFailedError: %nExpecting:%n <HashMultimap[List]((Frodo, Baggins))>%nto be equal to:%n <HashMap()>%nbut was not."));
        assertThat(errors.get(106))
                .contains(format("java.lang.AssertionError: %nExpecting null or empty but was:<HashMultimap[List]((Frodo, Baggins))>"));
    }

    static void runSoftlyAssertions(Lazy<Double> evaluated, SoftVavrAssertions softly) {
        Option<String> option = Option.some("value");
        softly.assertThat(option).contains("");
        softly.assertThat(option).containsInstanceOf(Integer.class);
        softly.assertThat(option).containsSame(new String("value"));
        softly.assertThat(option).hasValueSatisfying(v -> Assertions.assertThat(v).isEqualTo("xyz"));
        softly.assertThat(option).isEmpty();

        softly.assertThat(Option.none()).isDefined();
        softly.assertThat(evaluated).isNotEvaluated();

        Lazy<Double> notEvaluated = Lazy.of(Math::random);
        softly.assertThat(notEvaluated).isEvaluated();

        Set<String> set = HashSet.of("Bilbo", "Frodo");
        softly.assertThat(set).allSatisfy(name -> assertThat(name).startsWith("F"));
        softly.assertThat(set).hasSize(1);
        softly.assertThat(set).isEmpty();

        Seq<String> seq = List.of("Frodo", "Bilbo");
        softly.assertThat(seq).contains("Frodo", atIndex(1));
        softly.assertThat(seq).doesNotContain("Frodo", atIndex(0));
        softly.assertThat(seq).has(new Condition<>(str -> str.startsWith("some"), "starts with some"), atIndex(1));
        softly.assertThat(seq).isSorted();
        softly.assertThat(seq).isSortedAccordingTo(naturalOrder());
        softly.assertThat(seq).satisfies(str -> assertThat(str).startsWith("some"), atIndex(0));

        Validation<String, String> valid = Validation.valid("ok");
        softly.assertThat(valid).isInvalid();
        softly.assertThat(valid).containsValid("not ok");
        softly.assertThat(valid).containsValidInstanceOf(Integer.class);
        softly.assertThat(valid).containsValidSame("not ok");
        softly.assertThat(valid).usingValueComparator(naturalOrder()).containsValidSame("not ok");
        softly.assertThat(valid).usingFieldByFieldValueComparator().containsValidSame("not ok");

        Validation<String, String> invalid = Validation.invalid("not ok");
        softly.assertThat(invalid).isValid();
        softly.assertThat(invalid).containsInvalid("ok");
        softly.assertThat(invalid).containsInvalidInstanceOf(Integer.class);
        softly.assertThat(invalid).containsInvalidSame("ok");
        softly.assertThat(invalid).usingValueComparator(naturalOrder()).containsInvalidSame("ok");
        softly.assertThat(invalid).usingFieldByFieldValueComparator().containsInvalidSame("ok");

        Try<String> success = Try.success("ok");
        softly.assertThat(success).isFailure();
        softly.assertThat(success).contains("not ok");
        softly.assertThat(success).usingValueComparator(naturalOrder()).containsSame("nok");
        softly.assertThat(success).usingFieldByFieldValueComparator().containsSame("nok");
        softly.assertThat(success).containsInstanceOf(Integer.class);
        softly.assertThat(success).containsSame("not same");
        softly.assertThat(success).hasValueSatisfying(new Condition<>(str -> str.startsWith("some"), "starts with some"));
        softly.assertThat(success).hasValueSatisfying(str -> assertThat(str).startsWith("some"));

        Try<String> failure = Try.failure(new IllegalStateException("not ok"));
        softly.assertThat(failure).isSuccess();
        softly.assertThat(failure).failBecauseOf(NullPointerException.class);
        softly.assertThat(failure).failReasonHasMessage("another message");

        Either<String, Integer> right = Either.right(2);
        softly.assertThat(right).isLeft();
        softly.assertThat(right).containsOnRight(1);
        softly.assertThat(right).containsRightSame(1);
        softly.assertThat(right).containsRightInstanceOf(String.class);
        softly.assertThat(right).hasRightValueSatisfying(v -> assertThat(v).isEqualTo(0));
        softly.assertThat(right).usingValueComparator(naturalOrder()).containsOnRight(3);
        softly.assertThat(right).usingFieldByFieldValueComparator().containsOnRight(3);

        Either<String, Integer> left = Either.left("not ok");
        softly.assertThat(left).isRight();
        softly.assertThat(left).containsOnLeft("ok");
        softly.assertThat(left).containsLeftSame("ok");
        softly.assertThat(left).containsLeftInstanceOf(Integer.class);
        softly.assertThat(left).hasLeftValueSatisfying(v -> assertThat(v).hasSize(1));
        softly.assertThat(right).usingValueComparator(naturalOrder()).containsOnLeft("ok");
        softly.assertThat(right).usingFieldByFieldValueComparator().containsOnLeft("ok");

        softly.assertThat(HashMap.empty()).isNotEmpty();

        Map<String, String> map = HashMap.of("Frodo", "Baggins");
        softly.assertThat(map).isEmpty();
        softly.assertThat(map).allSatisfy((key, value) -> {
            assertThat(key).startsWith("key");
            assertThat(value).startsWith("value");
        });
        softly.assertThat(map).containsAnyOf(Tuple.of("Bilbo", "Baggins"));
        softly.assertThat(map).contains(Tuple.of("Peregrin", "Took"));
        softly.assertThat(map).containsAllEntriesOf(List.of(Tuple.of("Bilbo", "Baggins")));
        softly.assertThat(map).containsEntry("Bilbo", "Baggins");
        softly.assertThat(map).containsExactly(Tuple.of("Peregrin", "Took"));
        softly.assertThat(map).containsKey("Peregrin");
        softly.assertThat(map).containsKeys("Peregrin", "Bilbo");
        softly.assertThat(map).containsValue("Took");
        softly.assertThat(map).containsValues("Took", "Brandybuck");
        softly.assertThat(map).doesNotContain(Tuple.of("Frodo", "Baggins"));
        softly.assertThat(map).doesNotContainEntry("Frodo", "Baggins");
        softly.assertThat(map).doesNotContainKey("Frodo");
        softly.assertThat(map).doesNotContainKeys("Frodo");
        softly.assertThat(map).doesNotContainValue("Baggins");
        softly.assertThat(map).doesNotContainValues("Baggins");
        softly.assertThat(map).hasEntrySatisfying("Frodo", new Condition<>(str -> str.startsWith("some"), "starts with some"));
        softly.assertThat(map).hasSameSizeAs(new String[0]);
        softly.assertThat(map).hasSameSizeAs(List.empty());
        softly.assertThat(map).hasSize(0);
        softly.assertThat(map).hasSizeGreaterThan(2);
        softly.assertThat(map).hasSizeLessThan(1);
        softly.assertThat(map).isEqualTo(HashMap.empty());
        softly.assertThat(map).isNotEqualTo(HashMap.of("Frodo", "Baggins"));
        softly.assertThat(map).isNullOrEmpty();

        softly.assertThat(HashMultimap.withSeq().empty()).isNotEmpty();

        Multimap<String, String> multimap = HashMultimap.withSeq().of("Frodo", "Baggins");
        softly.assertThat(multimap).isEmpty();
        softly.assertThat(multimap).allSatisfy((key, value) -> {
            assertThat(key).startsWith("key");
            assertThat(value).startsWith("value");
        });
        softly.assertThat(multimap).containsAnyOf(Tuple.of("Bilbo", "Baggins"));
        softly.assertThat(multimap).contains(Tuple.of("Peregrin", "Took"));
        softly.assertThat(multimap).containsAllEntriesOf(List.of(Tuple.of("Bilbo", "Baggins")));
        softly.assertThat(multimap).containsEntry("Bilbo", "Baggins");
        softly.assertThat(multimap).containsExactly(Tuple.of("Peregrin", "Took"));
        softly.assertThat(multimap).containsKey("Peregrin");
        softly.assertThat(multimap).containsKeys("Peregrin", "Bilbo");
        softly.assertThat(multimap).containsValue("Took");
        softly.assertThat(multimap).containsValues("Took", "Brandybuck");
        softly.assertThat(multimap).doesNotContain(Tuple.of("Frodo", "Baggins"));
        softly.assertThat(multimap).doesNotContainEntry("Frodo", "Baggins");
        softly.assertThat(multimap).doesNotContainKey("Frodo");
        softly.assertThat(multimap).doesNotContainKeys("Frodo");
        softly.assertThat(multimap).doesNotContainValue("Baggins");
        softly.assertThat(multimap).doesNotContainValues("Baggins");
        softly.assertThat(multimap).hasEntrySatisfying("Frodo", new Condition<>(str -> str.startsWith("some"), "starts with some"));
        softly.assertThat(multimap).hasSameSizeAs(new String[0]);
        softly.assertThat(multimap).hasSameSizeAs(List.empty());
        softly.assertThat(multimap).hasSize(0);
        softly.assertThat(multimap).hasSizeGreaterThan(2);
        softly.assertThat(multimap).hasSizeLessThan(1);
        softly.assertThat(multimap).isEqualTo(HashMap.empty());
        softly.assertThat(multimap).isNotEqualTo(HashMap.of("Frodo", "Baggins"));
        softly.assertThat(multimap).isNullOrEmpty();
    }


}
