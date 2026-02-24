/*
 * Copyright 2014, 2026 Bang Jun-young
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.bangjunyoung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanTextMatcherTests {

    static Stream<Arguments> constructorTestParameters() {
        return Stream.of(
            arguments((ThrowingCallable) () -> new KoreanTextMatcher(null), "pattern = null")
        );
    }

    @ParameterizedTest(name = "new KoreanTextMatcher() with {1} throws IllegalArgumentException")
    @MethodSource("constructorTestParameters")
    void constructorExceptionTest(ThrowingCallable func, String description) {
        assertThatThrownBy(func).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> matchesTestParameters() {
        return Stream.of(
            // Hangul Compatibility Jamo
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎㄹ", 0),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "하늘", 1),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎ늘", 2),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎ느", 3),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎㄴ", 4),
            // Hangul Jamo
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒᄅ", 0),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "하늘", 1),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒ늘", 2),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒ느", 3),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒᄂ", 4),

            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "한", 2)
        );
    }

    @ParameterizedTest(name = "matches❨{0}, {1}❩ returns {2}")
    @MethodSource("matchesTestParameters")
    void matchesTest(String text, String pattern, int expectedMatchCount) {
        int count = 0;
        for (KoreanTextMatch match : KoreanTextMatcher.matches(text, pattern)) {
            count++;
            assertThat(text).contains(match.value());
        }
        assertThat(count).isEqualTo(expectedMatchCount);
    }

    static Stream<Arguments> isMatchTestParameters() {
        return Stream.of(
            arguments("", "^$", true),
            arguments("하늘", "", true),
            arguments("하늘", "^", true),
            arguments("하늘", "$", true),
            arguments("하늘", "하늘", true),
            arguments(" 하늘", "하늘", true),
            arguments("하늘 ", "하늘", true),
            arguments(" 하늘 ", "하늘", true),
            arguments("하늘", "^하늘", true),
            arguments("하늘 ", "^하늘", true),
            arguments("하늘", "하늘$", true),
            arguments(" 하늘", "하늘$", true),
            arguments("하늘", "^하늘$", true),
            arguments("하늘", "하ㄴ", true),
            arguments("하늘", "^하ㄴ", true),
            arguments("하늘", "하ㄴ$", true),
            arguments("하늘", "^하ㄴ$", true),
            arguments("하늘", "ㅎ늘", true),
            arguments("하늘", "^ㅎ늘", true),
            arguments("하늘", "ㅎ늘$", true),
            arguments("하늘", "^ㅎ늘$", true),
            arguments("하늘", "ㅎㄴ", true),
            arguments("하늘 ", "ㅎㄴ", true),
            arguments(" 하늘", "ㅎㄴ", true),
            arguments(" 하늘 ", "ㅎㄴ", true),
            arguments("하늘", "^ㅎㄴ", true),
            arguments("하늘", "ㅎㄴ$", true),
            arguments("하늘", "^ㅎㄴ$", true),
            arguments("하늘", "ㅎ느", true),
            arguments("하늘", "^ㅎ느", true),
            arguments("하늘", "ㅎ느$", true),
            arguments("하늘", "^ㅎ느$", true),
            arguments("하늘", "^$", false),
            arguments("하늘", "ㅎㄴㅎㄴ", false),
            arguments("하 늘", "하늘", false),
            arguments(" 하 늘", "하늘", false),
            arguments("하 늘 ", "하늘", false),
            arguments(" 하 늘 ", "하늘", false),
            arguments("하늘", "하를", false),
            arguments(" 하늘", "^하늘", false),
            arguments(" 하늘 ", "^하늘", false),
            arguments("하늘 ", "하늘$", false),
            arguments(" 하늘 ", "하늘$", false),
            arguments(" 하늘", "^하늘$", false),
            arguments("하늘 ", "^하늘$", false),
            arguments("하늘", "한", true),
            arguments("바람", "발", true)
        );
    }

    @ParameterizedTest(name = "isMatch❨{0}, {1}❩ returns {2}")
    @MethodSource("isMatchTestParameters")
    void isMatchTest(String text, String pattern, boolean expectedResult) {
        assertThat(KoreanTextMatcher.isMatch(text, pattern)).isEqualTo(expectedResult);
    }

    static Stream<Arguments> isMatchExceptionTestParameters() {
        return Stream.of(
            arguments((ThrowingCallable) () -> KoreanTextMatcher.isMatch(null, ""), "text = null"),
            arguments((ThrowingCallable) () -> KoreanTextMatcher.isMatch("", null), "pattern = null")
        );
    }

    @ParameterizedTest(name = "static isMatch() with {1} throws IllegalArgumentException")
    @MethodSource("isMatchExceptionTestParameters")
    void isMatchExceptionTest(ThrowingCallable func, String description) {
        assertThatThrownBy(func).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> matchExceptionTestParameters() {
        return Stream.of(
            arguments((ThrowingCallable) () -> KoreanTextMatcher.match(null, ""), "text = null"),
            arguments((ThrowingCallable) () -> KoreanTextMatcher.match("", null), "pattern = null"),
            arguments((ThrowingCallable) () -> new KoreanTextMatcher("").match(null), "text = null"),
            arguments((ThrowingCallable) () -> new KoreanTextMatcher(null).match(""), "pattern = null"),
            arguments((ThrowingCallable) () -> new KoreanTextMatcher("").match("", -1), "startIndex < 0"),
            arguments((ThrowingCallable) () -> new KoreanTextMatcher("").match("", 1), "startIndex > text.length❨ ❩")
        );
    }

    @ParameterizedTest(name = "match❨ ❩ with {1} throws IllegalArgumentException")
    @MethodSource("matchExceptionTestParameters")
    void matchExceptionTest(ThrowingCallable func, String description) {
        assertThatThrownBy(func).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> matchesExceptionTestParameters() {
        return Stream.of(
            arguments((ThrowingCallable) () -> KoreanTextMatcher.matches("", "").iterator().remove())
        );
    }

    @ParameterizedTest(name = "Iterable<KoreanTextMatch>.remove❨ ❩ throws UnsupportedOperationException")
    @MethodSource("matchesExceptionTestParameters")
    void matchesExceptionTest(ThrowingCallable func) {
        assertThatThrownBy(func).isInstanceOf(UnsupportedOperationException.class);
    }
}
