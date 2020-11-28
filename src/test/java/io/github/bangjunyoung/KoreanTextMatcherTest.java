/*
 * Copyright 2014 Bang Jun-young
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanTextMatcherTest {

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
            arguments("하늘 ᄒ늘 하느 ᄒᄂ", "ᄒᄂ", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("matchesTestParameters")
    @DisplayName("matches(text, pattern) with valid arguments")
    void matches_withValidArguments(String text, String pattern, int expectedMatchCount) {
        int count = 0;
        for (KoreanTextMatch match : KoreanTextMatcher.matches(text, pattern)) {
            count++;
            assertThat(text, containsString(match.value()));
        }
        assertThat(count, equalTo(expectedMatchCount));
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
            arguments("하늘", "한", false),
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

    @ParameterizedTest
    @MethodSource("isMatchTestParameters")
    @DisplayName("isMatch(text, pattern) with valid arguments")
    void isMatch_withValidArguments(String text, String pattern, boolean expectedResult) {
        assertThat(KoreanTextMatcher.isMatch(text, pattern), equalTo(expectedResult));
    }

    @Test
    @DisplayName("new KoreanTextMatcher(null) throws IllegalArgumentException")
    void KoreanTextMatcher_throwsExceptionOnNullPatternArgument() {
        assertThrows(IllegalArgumentException.class, () ->
            new KoreanTextMatcher(null));
    }

    @Test
    @DisplayName("static isMatch(null, \"\") throws IllegalArgumentException")
    void isMatch_throwsExceptionOnNullTextArgument() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.isMatch(null, ""));
    }

    @Test
    @DisplayName("static isMatch(\"\", null) throws IllegalArgumentException")
    void isMatch_throwsExceptionOnNullPatternArgument() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.isMatch("", null));
    }

    @Test
    @DisplayName("static match(null, \"\") throws IllegalArgumentException")
    void static_match_throwsExceptionOnNullTextArgument() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.match(null, ""));
    }

    @Test
    @DisplayName("static match(\"\", null) throws IllegalArgumentException")
    void static_match_throwsExceptionOnNullPatternArgument() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.match("", null));
    }

    @Test
    @DisplayName("match(null) throws IllegalArgumentException")
    void match_throwsExceptionOnNullTextArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match(null);
        });
    }

    @Test
    @DisplayName("match(text, startIndex) throws IllegalArgumentException if startIndex < 0")
    void match_throwsExceptionOnNegativeStartIndexArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match("", -1);
        });
    }

    @Test
    @DisplayName("match(text, startIndex) throws IllegalArgumentException if startIndex is too large")
    void match_throwsExceptionOnTooLargeStartIndexArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            String text = "";
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match(text, text.length() + 1);
        });
    }

    @Test
    @DisplayName("Instance of Iterable<KoreanTextMatch> throws UnsupportedOperationException if remove() is called with it")
    void matches_throwsExceptionOnCallingRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Iterable<KoreanTextMatch> matches = KoreanTextMatcher.matches("", "");
            matches.iterator().remove();
        });
    }
}
