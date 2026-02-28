/*
 * Copyright 2019, 2026 Bang Jun-young
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
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanCharApproxMatcherTests {
    static Stream<Arguments> isMatchTestParameters() {
        return Stream.of(
            arguments('h', 'h', true),
            arguments('\u3131', '\u1100', true),
            arguments('\u314E', '\u1112', true),
            arguments('\u1100', '\u3131', true),
            arguments('\u1112', '\u314E', true),
            arguments('또', 'ㄷ', true),
            arguments('또', 'ㄸ', true),
            arguments('광', '고', true),
            arguments('광', '과', true),
            arguments('밝', '발', true),
            arguments('밝', '밝', true),
            arguments('꽜', 'ㄱ', true),
            arguments('꽜', 'ㄲ', true),
            arguments('꽜', '꼬', true),
            arguments('꽜', '꽈', true),
            arguments('꽜', '꽛', true),
            arguments('꽜', '꽜', true),
            arguments('H', 'h', false),
            arguments('하', '한', false),
            arguments('한', 'ㅏ', false),
            arguments('한', '핞', false)
        );
    }

    @ParameterizedTest(name = "isMatch❨{0}, {1}❩ returns {2}")
    @MethodSource("isMatchTestParameters")
    void isMatchTest(char t, char p, boolean expected) {
        assertThat(KoreanCharApproxMatcher.isMatch(t, p)).isEqualTo(expected);
    }
}
