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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanCharTest {

    static Stream<Arguments> isSyllableTestParameters() {
        return Stream.of(
            arguments('가', true),
            arguments('힣', true),
            arguments('\uABFF', false),
            arguments('\uD7A4', false)
        );
    }

    @ParameterizedTest
    @MethodSource("isSyllableTestParameters")
    @DisplayName("isSyllable(syllable) with various arguments")
    void isSyllable_withVariousArguments(char syllable, boolean expectedResult) {
        String message = String.format("syllable: %c", syllable);
        assertThat(message, KoreanChar.isSyllable(syllable), equalTo(expectedResult));
    }

    static Stream<Arguments> isChoseongTestParameters() {
        return Stream.of(
            arguments('ᄀ', true),
            arguments('ᄒ', true),
            arguments('\u10ff', false),
            arguments('\u1200', false)
        );
    }

    @ParameterizedTest
    @MethodSource("isChoseongTestParameters")
    @DisplayName("isChoseong(c) with various arguments")
    void isChoseong_withVariousArguments(char c, boolean expectedResult) {
        String message = String.format("char: %c", c);
        assertThat(message, KoreanChar.isChoseong(c), equalTo(expectedResult));
    }

    static Stream<Arguments> isCompatChoseongTestParameters() {
        return Stream.of(
            arguments('ㄱ', true),
            arguments('ㅎ', true),
            arguments('\u3130', false),
            arguments('\u318f', false)
        );
    }

    @ParameterizedTest
    @MethodSource("isCompatChoseongTestParameters")
    @DisplayName("isCompatChoseong(c) with various arguments")
    void isCompatChoseong_withVariousArguments(char c, boolean expectedResult) {
        String message = String.format("char: %c", c);
        assertThat(message, KoreanChar.isCompatChoseong(c), equalTo(expectedResult));
    }

    static Stream<Arguments> getChoseongTestParameters() {
        return Stream.of(
            arguments('강', 'ᄀ'),
            arguments('한', 'ᄒ'),
            arguments('A', '\0')
        );
    }

    @ParameterizedTest
    @MethodSource("getChoseongTestParameters")
    @DisplayName("getChoseong(syllable) with various arguments")
    void getChoseong_withValidArguments(char syllable, char expectedResult) {
        String message = String.format("char: %c", syllable);
        assertThat(message, KoreanChar.getChoseong(syllable), equalTo(expectedResult));
    }

    static Stream<Arguments> getCompatChoseongTestParameters() {
        return Stream.of(
            arguments('강', 'ㄱ'),
            arguments('한', 'ㅎ'),
            arguments('A', '\0')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatChoseongTestParameters")
    @DisplayName("getCompatChoseong(syllable) with various arguments")
    void getCompatChoseong_withValidArguments(char syllable, char expectedResult) {
        String message = String.format("char: %c", syllable);
        assertThat(message, KoreanChar.getCompatChoseong(syllable), equalTo(expectedResult));
    }
}
