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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class KoreanCharTest {

    @ParameterizedTest
    @ValueSource(chars = { '가', '힣' })
    @DisplayName("isSyllable(char) with valid arguments")
    void isSyllable_withValidArguments(char syllable) {
        assertTrue(KoreanChar.isSyllable(syllable));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\uABFF', '\uD7A4' })
    @DisplayName("isSyllable(char) with invalid arguments returns false")
    void isSyllable_withInvalidArguments(char syllable) {
        assertFalse(KoreanChar.isSyllable(syllable));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ᄀ', 'ᄒ' })
    @DisplayName("isChoseong(char) with valid arguments")
    void isChoseong_withValidArguments(char c) {
        assertTrue(KoreanChar.isChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\u10ff', '\u1200' })
    @DisplayName("isChoseong(char) with invalid arguments returns false")
    void isChoseong_withInvalidArguments(char c) {
        assertFalse(KoreanChar.isChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄱ', 'ㅎ' })
    @DisplayName("isCompatChoseong(char) with valid arguments")
    void isCompatChoseong_withValidArguments(char c) {
        assertTrue(KoreanChar.isCompatChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄳ', 'ㅄ' })
    @DisplayName("isCompatChoseong(char) with invalid arguments returns false")
    void isCompatChoseong_withInvalidArguments(char c) {
        assertFalse(KoreanChar.isCompatChoseong(c));
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
