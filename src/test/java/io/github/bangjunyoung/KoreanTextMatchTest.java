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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanTextMatchTest {

    static Stream<Arguments> matchTestParameters() {
        return Stream.of(
            arguments("", "", true, 0, 0),
            arguments("", "^$", true, 0, 0),
            arguments("하늘", "", true, 0, 0),
            arguments("하늘", "^", true, 0, 0),
            arguments("하늘", "$", true, 0, 0),
            arguments("하늘", "ㅎ", true, 0, 1),
            arguments("하늘", "하", true, 0, 1),
            arguments("하늘", "느", true, 1, 1),
            arguments("하늘", "늘", true, 1, 1),
            arguments("하늘", "하느", true, 0, 2),
            arguments("하늘", "하늘", true, 0, 2),
            arguments("하늘", "ㅎㄴ", true, 0, 2),
            arguments("하늘", "ㅎ느", true, 0, 2),
            arguments("하늘", "ㄴ", true, 1, 1),
            arguments("푸른 하늘", "하늘", true, 3, 2),
            arguments("푸른 하늘", "ㅎㄴ", true, 3, 2),
            arguments("하늘", "^$", false, 0, 0),
            arguments("하늘", "한", false, 0, 0),
            arguments("푸른 하늘", "^ㅎㄴ", false, 0, 0),
            arguments("푸른 하늘", "푸른$", false, 0, 0),
            arguments("푸른 하늘", "ㅎㄹ", false, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("matchTestParameters")
    @DisplayName("new KoreanTextMatcher(pattern).match(text) with valid arguments")
    void match_withValidArguments(String text, String pattern,
            boolean expectedSuccess, int expectedIndex, int expectedLength) {
        String message = String.format("text: %s, pattern: %s", text, pattern);
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern);
        KoreanTextMatch match = matcher.match(text);
        assertThat(message, match.success(), equalTo(expectedSuccess));
        if (match.success()) {
            assertThat(message, match.index(), equalTo(expectedIndex));
            assertThat(message, match.length(), equalTo(expectedLength));
            assertThat(message, match.length(), equalTo(match.value().length()));
            assertThat(message, text.contains(match.value()));
        }
    }

    static Stream<Arguments> nextMatchTestParameters() {
        return Stream.of(
            arguments("", "하늘", 0),
            arguments("하늘", "하늘", 1),
            arguments("하늘", "^하늘$", 1),
            arguments(" 하늘", "^하늘$", 0),
            arguments("바다 하늘 바다", "^바다", 1),
            arguments("바다 하늘 바다", "바다", 2),
            arguments("바다 하늘 바다", "바다$", 1),
            arguments("하늘하늘하늘", "ㅎㄴ", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("nextMatchTestParameters")
    @DisplayName("new KoreanTextMatcher(pattern).match(text) with valid arguments")
    void nextMatch_withValidArguments(String text, String pattern, int expectedMatchCount) {
        String message = String.format("text: %s, pattern: %s", text, pattern);
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern);
        KoreanTextMatch match = matcher.match(text);
        int matchCount = 0;
        while (match.success()) {
            matchCount++;
            int index = match.index();
            int length = match.length();
            String value = match.value();
            assertThat(message, text.substring(index, index + length).equals(value));
            match = match.nextMatch();
        }
        assertThat(message, matchCount, equalTo(expectedMatchCount));
    }

    @Test
    @DisplayName("nextMatch() returns EMPTY if current match is EMPTY")
    void nextMatch_returnsEMPTYIfCurrentMatchIsEMPTY() {
        assertThat(KoreanTextMatch.EMPTY.nextMatch(), equalTo(KoreanTextMatch.EMPTY));
    }
}
