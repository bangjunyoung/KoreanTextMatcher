/*
 * Copyright 2019 Bang Jun-young
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanCharApproxMatcherTest {
    static Stream<Arguments> matchMatchedTestParameters() {
        return Stream.of(
            arguments('h', 'h'),
            arguments('\u3131', '\u1100'),
            arguments('\u314E', '\u1112'),
            arguments('\u1100', '\u3131'),
            arguments('\u1112', '\u314E'),
            arguments('또', 'ㄷ'),
            arguments('또', 'ㄸ'),
            arguments('광', '고'),
            arguments('광', '과'),
            arguments('밝', '발'),
            arguments('밝', '밝'),
            arguments('꽜', 'ㄱ'),
            arguments('꽜', 'ㄲ'),
            arguments('꽜', '꼬'),
            arguments('꽜', '꽈'),
            arguments('꽜', '꽛'),
            arguments('꽜', '꽜')
        );
    }

    @ParameterizedTest
    @MethodSource("matchMatchedTestParameters")
    @DisplayName("match() with matched arguments")
    void match_withMatchedArguments(char t, char p) {
        assertTrue(KoreanCharApproxMatcher.isMatch(t, p));
    }

    static Stream<Arguments> matchUnmatchedTestParameters() {
        return Stream.of(
            arguments('H', 'h'),
            arguments('하', '한'),
            arguments('한', 'ㅏ'),
            arguments('한', '핞')
        );
    }

    @ParameterizedTest
    @MethodSource("matchUnmatchedTestParameters")
    @DisplayName("match() with unmatched arguments")
    void match_withUnmatchedArguments(char t, char p) {
        assertFalse(KoreanCharApproxMatcher.isMatch(t, p));
    }
}
