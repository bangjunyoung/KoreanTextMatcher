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

import java.util.EnumSet;
import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.bangjunyoung.KoreanTextMatcher.MatchingOptions;

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
            arguments("", "", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("", "^", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("", "$", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("", "^$", EnumSet.noneOf(MatchingOptions.class), 1),

            arguments("가나다", "", EnumSet.noneOf(MatchingOptions.class), 4),
            arguments("가나다", "^", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("가나다", "$", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("가나다", "^$", EnumSet.noneOf(MatchingOptions.class), 0),

            // Hangul Compatibility Jamo
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎㄹ", EnumSet.noneOf(MatchingOptions.class), 0),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "하늘", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎ늘", EnumSet.noneOf(MatchingOptions.class), 2),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎ느", EnumSet.noneOf(MatchingOptions.class), 3),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "ㅎㄴ", EnumSet.noneOf(MatchingOptions.class), 4),
            // Hangul Jamo
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒᄅ", EnumSet.noneOf(MatchingOptions.class), 0),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "하늘", EnumSet.noneOf(MatchingOptions.class), 1),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒ늘", EnumSet.noneOf(MatchingOptions.class), 2),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒ느", EnumSet.noneOf(MatchingOptions.class), 3),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒᄂ", EnumSet.noneOf(MatchingOptions.class), 4),

            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "", EnumSet.of(MatchingOptions.DubeolsikInput), 12),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "^", EnumSet.of(MatchingOptions.DubeolsikInput), 1),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "^한", EnumSet.of(MatchingOptions.DubeolsikInput), 1),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "^한", EnumSet.of(MatchingOptions.Default), 0),
            arguments("하늘 ㅎ늘 하느 ㅎㄴ", "$", EnumSet.of(MatchingOptions.DubeolsikInput), 1),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ㅎ", EnumSet.of(MatchingOptions.DubeolsikInput), 4),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "하", EnumSet.of(MatchingOptions.DubeolsikInput), 2),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "한", EnumSet.of(MatchingOptions.DubeolsikInput), 2),
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "학", EnumSet.of(MatchingOptions.DubeolsikInput), 0)
        );
    }

    @ParameterizedTest(name = "matches❨{0}, {1}, {2}❩ returns {3}")
    @MethodSource("matchesTestParameters")
    void matchesTest(String text, String pattern, EnumSet<MatchingOptions> options, int expectedMatchCount) {
        int count = 0;
        for (KoreanTextMatch match : KoreanTextMatcher.matches(text, pattern, options.toArray(MatchingOptions[]::new))) {
            count++;
            assertThat(text).contains(match.value());
        }
        assertThat(count).isEqualTo(expectedMatchCount);
    }

    static Stream<Arguments> isMatchTestParameters() {
        return Stream.of(
            arguments("", "", true),
            arguments("", "^", true),
            arguments("", "$", true),
            arguments("", "^$", true),
            arguments("하늘", "", true),
            arguments("하늘", "^", true),
            arguments("하늘", "$", true),
            arguments("하늘", "^$", false),
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
            arguments("하늘 ", "^하늘$", false)
        );
    }

    @ParameterizedTest(name = "isMatch❨{0}, {1}❩ returns {2}")
    @MethodSource("isMatchTestParameters")
    void isMatchTest(String text, String pattern, boolean expectedResult) {
        assertThat(KoreanTextMatcher.isMatch(text, pattern)).isEqualTo(expectedResult);
    }

    static Stream<Arguments> isMatchIgnoreCaseTestParameters() {
        return Stream.of(
            arguments("ABC", "ABC", EnumSet.noneOf(MatchingOptions.class), true),
            arguments("ABC", "abc", EnumSet.noneOf(MatchingOptions.class), false),
            arguments("ABC", "ab1", EnumSet.noneOf(MatchingOptions.class), false),
            arguments("AB1", "abc", EnumSet.noneOf(MatchingOptions.class), false),
            arguments("ABC", "abc", EnumSet.of(MatchingOptions.IgnoreCase), true),
            arguments("ABC", "abz", EnumSet.of(MatchingOptions.IgnoreCase), false),
            arguments("ABC", "ab1", EnumSet.of(MatchingOptions.IgnoreCase), false),
            arguments("AB1", "ab1", EnumSet.of(MatchingOptions.IgnoreCase), true)
        );
    }

    @ParameterizedTest(name = "isMatch❨{0}, {1}, {2}❩ returns {3}")
    @MethodSource("isMatchIgnoreCaseTestParameters")
    void isMatchIgnoreCaseTest(String text, String pattern, EnumSet<MatchingOptions> options, boolean expectedResult) {
        assertThat(KoreanTextMatcher.isMatch(text, pattern, options.toArray(MatchingOptions[]::new))).isEqualTo(expectedResult);
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

    static Stream<Arguments> matchIgnoreWhitespaceTestParameters() {
        return Stream.of(
            arguments("한글", "A", MatchingOptions.Default, false, "", 0, 0),
            arguments("Apple", "가", MatchingOptions.Default, false, "", 0, 0),
            arguments("Apple", "B", MatchingOptions.Default, false, "", 0, 0),
            arguments("ABC", "ABD", MatchingOptions.Default, false, "", 0, 0),
            arguments("Tik Tak", "", MatchingOptions.Default, true, "", 0, 0),
            arguments("Tik Tak", "tak", MatchingOptions.Default, false, "", 0, 0),
            arguments("Tik Tak", "ta1", MatchingOptions.Default, false, "", 0, 0),
            arguments("Tik Tak", "tak", MatchingOptions.IgnoreCase, true, "Tak", 4, 3),
            arguments("Tik Tak", "tikt", MatchingOptions.IgnoreCase, true, "Tik T", 0, 5),
            arguments(" 저 TV가 내게 약속할 때 ", "tvㄱ내ㄱㅇㅅ", MatchingOptions.Default, false, "", 0, 0),
            arguments(" 저 TV가 내게 약속할 때 ", "TVㄱ ㄴㄱ", MatchingOptions.Default, false, "TV가 내게", 3, 6),
            arguments(" 저 TV가 내게 약속할 때 ", "tvㄱ내ㄱㅇㅅ", MatchingOptions.IgnoreCase, true, "TV가 내게 약속", 3, 9),
            arguments(" 저 TV가\r\n내게\t약속할 때 ", "tvㄱ내ㄱㅇㅅ", MatchingOptions.IgnoreCase, true, "TV가\r\n내게\t약속", 3, 10),
            arguments(" 바 ", "바닥", MatchingOptions.Default, false, "", 0, 0),
            arguments(" 바      ", "바닥", MatchingOptions.Default, false, "", 0, 0),
            arguments(" 바          닥", "바닥", MatchingOptions.Default, true, "바          닥", 1, 12),
            arguments("          바닥", "바닥", MatchingOptions.Default, true, "바닥", 10, 2),
            arguments("아바ㄹ", "아받", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바ㄹ", "받", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바", "받", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바 ㄹ", "받", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바다", "바닥", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥", "남은", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥", "받", MatchingOptions.DubeolsikInput, true, "바닥", 0, 2),
            arguments("바닥", "남", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥", "바ㄹ", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥", "ㅂㄷ", MatchingOptions.DubeolsikInput, true, "바닥", 0, 2),
            arguments("바닥 ", "받", MatchingOptions.DubeolsikInput, true, "바닥", 0, 2),
            arguments("바 닥 ", "받", MatchingOptions.DubeolsikInput, true, "바 닥", 0, 3),
            arguments("바닥에 껍질", "바닥엔", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥에 ㄴ", "바닥엔", MatchingOptions.DubeolsikInput, true, "바닥에 ㄴ", 0, 5),
            arguments("바닥에 ㄹ", "바닥엔", MatchingOptions.DubeolsikInput, false, "", 0, 0),
            arguments("바닥에   ㄴ", "바닥엔", MatchingOptions.DubeolsikInput, true, "바닥에   ㄴ", 0, 7),
            arguments("바닥에 남은", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에 남은", 0, 6),
            arguments("바닥에 남은", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에 남은", 0, 6),
            arguments("바닥에 남은 ", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에 남은", 0, 6),
            arguments(" 바닥에 남은 ", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에 남은", 1, 6),
            arguments("  바닥에 남은  ", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에 남은", 2, 6),
            arguments(" 바닥에  남은 ", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에  남은", 1, 7),
            arguments("  바닥에  남은  ", "ㅂㄷㅇㄴㅇ", MatchingOptions.Default, true, "바닥에  남은", 2, 7),
            arguments("바닥에 남은 차가운 껍질에", "ㅊㄱㅇㄲㅈㅇ", MatchingOptions.Default, true, "차가운 껍질에", 7, 7),
            arguments("바닥에 남은  차가운   껍질에", "ㅊㄱㅇㄲㅈㅇ", MatchingOptions.Default, true, "차가운   껍질에", 8, 9),
            arguments("바닥에 남은 차가운 껍질   ", "ㅊㄱㅇㄲㅈㅇ", MatchingOptions.Default, false, "", 0, 0),
            arguments("바닥에 남은 차가운 껍질", "바닥엔", MatchingOptions.DubeolsikInput, true, "바닥에 남", 0, 5),
            arguments(" 바닥에  남은  차가운", "바닥엔", MatchingOptions.DubeolsikInput, true, "바닥에  남", 1, 6)
        );
    }

    @ParameterizedTest(name = "new KoreanTextMatcher❨{1}❩.match❨{0}❩ returns success={3}, value={4}, index={5}, length={6}")
    @MethodSource("matchIgnoreWhitespaceTestParameters")
    void matchIgnoreWhitespaceTest(String text, String pattern, MatchingOptions option,
            boolean expectedSuccess, String expectedValue, int expectedIndex, int expectedLength) {
        String message = String.format("text: %s, pattern: %s", text, pattern);
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern, MatchingOptions.IgnoreWhitespace, option);
        KoreanTextMatch match = matcher.match(text);
        assertThat(match.success()).as(message).isEqualTo(expectedSuccess);
        if (match.success()) {
            assertThat(match.value()).as(message).isEqualTo(expectedValue);
            assertThat(match.index()).as(message).isEqualTo(expectedIndex);
            assertThat(match.length()).as(message).isEqualTo(expectedLength);
            assertThat(match.length()).as(message).isEqualTo(match.value().length());
            assertThat(text).as(message).contains(match.value());
        }
    }

    static Stream<Arguments> matchTestParameters() {
        return Stream.of(
            arguments("", "", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("", "^", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("", "$", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("", "^$", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("하늘", "", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("하늘", "^", MatchingOptions.DubeolsikInput, true, 0, 0),
            arguments("하늘", "$", MatchingOptions.DubeolsikInput, true, 2, 0),
            arguments("하늘", "^$", MatchingOptions.DubeolsikInput, false, 0, 0),
            arguments("하늘", "ㅎ", MatchingOptions.DubeolsikInput, true, 0, 1),
            arguments("하늘", "하", MatchingOptions.DubeolsikInput, true, 0, 1),
            arguments("하늘", "한", MatchingOptions.DubeolsikInput, true, 0, 2),
            arguments("하늘", "한", MatchingOptions.Default, false, 0, 0),
            arguments("하늘", "한강", MatchingOptions.DubeolsikInput, false, 0, 0)
        );
    }

    @ParameterizedTest(name = "static match❨{0}, {1}❩")
    @MethodSource("matchTestParameters")
    void matchTest(String text, String pattern, MatchingOptions option, boolean expectedSuccess, int expectedIndex, int expectedLength) {
        KoreanTextMatch match = KoreanTextMatcher.match(text, pattern, new MatchingOptions[] {option});
        assertThat(match.success()).isEqualTo(expectedSuccess);
        assertThat(match.index()).isEqualTo(expectedIndex);
        assertThat(match.length()).isEqualTo(expectedLength);
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
