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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanTextMatcher_matchesTest {

    static Stream<Arguments> getTestParameters() {
        return Stream.of(
            arguments("하늘 ㅎ늘 하ㄴ ㅎㄴ", "ㅎㄹ", 0),
            arguments("하늘 ㅎ늘 하ㄴ ㅎㄴ", "하늘", 1),
            arguments("하늘 ㅎ늘 하ㄴ ㅎㄴ", "ㅎ늘", 2),
            arguments("하늘 ㅎ늘 하ㄴ ㅎㄴ", "ㅎㄴ", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestParameters")
    @DisplayName("matches(text, pattern) with valid arguments")
    void matches_withValidArguments(String text, String pattern, int expectedMatchCount) {
        String message = String.format("text: %s, pattern: %s", text, pattern);

        int count = 0;
        for (KoreanTextMatch match : KoreanTextMatcher.matches(text, pattern)) {
            count++;
            assertThat(message, text.contains(match.value()));
        }
        assertThat(message, count, equalTo(expectedMatchCount));
    }
}
