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
import org.junit.jupiter.api.Test;
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

    static Stream<Arguments> getChoseong_TestParameters() {
        return Stream.of(
            arguments('강', 'ᄀ'),
            arguments('한', 'ᄒ')
        );
    }

    @ParameterizedTest
    @MethodSource("getChoseong_TestParameters")
    @DisplayName("getChoseong(char) with valid arguments")
    void getChoseong_withValidArguments(char syllable, char expected) {
        assertThat(KoreanChar.getChoseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getChoseong with invalid arguments throws IllegalArgumentException")
    void getChoseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanChar.getChoseong('A');
        });
    }

    static Stream<Arguments> getCompatChoseong_TestParameters() {
        return Stream.of(
            arguments('하', 'ㅎ'),
            arguments('늘', 'ㄴ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatChoseong_TestParameters")
    @DisplayName("getCompatChoseong(char) with valid arguments")
    void getCompatChoseong_withValidArguments(char syllable, char expected) {
        assertThat(KoreanChar.getCompatChoseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatChoseong with invalid arguments throws IllegalArgumentException")
    void getCompatChoseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanChar.getCompatChoseong('A');
        });
    }

    static Stream<Arguments> getCompatJungseong_TestParameters() {
        return Stream.of(
            arguments('한', 'ㅏ'),
            arguments('글', 'ㅡ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatJungseong_TestParameters")
    @DisplayName("getCompatJungseong(char) with valid arguments")
    void getCompatJungseong_withValidArguments(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJungseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatJungseong with invalid arguments throws IllegalArgumentException")
    void getCompatJungseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanChar.getCompatJungseong('A');
        });
    }

    static Stream<Arguments> getCompatJongseong_TestParameters() {
        return Stream.of(
            arguments('한', 'ㄴ'),
            arguments('글', 'ㄹ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatJongseong_TestParameters")
    @DisplayName("getCompatJongseong(char) with valid arguments")
    void getCompatJongseong_withValidArguments(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJongseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatJongseong with invalid arguments throws IllegalArgumentException")
    void getCompatJongseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanChar.getCompatJongseong('A');
        });
    }

    static Stream<Arguments> compatChoseongToChoseong_TestParameters() {
        return Stream.of(
            arguments('\u3131', '\u1100'),
            arguments('\u314E', '\u1112')
        );
    }

    @ParameterizedTest
    @MethodSource("compatChoseongToChoseong_TestParameters")
    @DisplayName("compatChoseongToChoseong(char) with valid arguments")
    void compatChoseongToChoseong_withValidArguments(char c, char expected) {
        assertThat(KoreanChar.compatChoseongToChoseong(c), equalTo(expected));
    }

    @Test
    @DisplayName("compatChoseongToChoseong(char) with invalid arguments throws IllegalArgumentException")
    void compatChoseongToChoseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanChar.compatChoseongToChoseong('A'));
    }

    static Stream<Arguments> choseongToCompatChoseong_TestParameters() {
        return Stream.of(
            arguments('\u1100', '\u3131'),
            arguments('\u1112', '\u314E')
        );
    }

    @ParameterizedTest
    @MethodSource("choseongToCompatChoseong_TestParameters")
    @DisplayName("choseongToCompatChoseong(char) with valid arguments")
    void choseongToCompatChoseong_withValidArguments(char c, char expected) {
        assertThat(KoreanChar.choseongToCompatChoseong(c), equalTo(expected));
    }

    @Test
    @DisplayName("choseongToCompatChoseong(char) with invalid arguments throws IllegalArgumentException")
    void choseongToCompatChoseong_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanChar.choseongToCompatChoseong('A'));
    }

    static Stream<Arguments> joinJamo_TestParameters() {
        return Stream.of(
            // Hangul Compatibility Jamo
            arguments("ㄱ", 'ㄱ'),
            arguments("ㅎ", 'ㅎ'),
            arguments("ㄱㄱ", 'ㄲ'),
            arguments("ㄱㅅ", 'ㄳ'),
            // Hangul Jamo
            arguments("ᄀ", 'ᄀ'),
            arguments("ᇂ", 'ᇂ'),
            arguments("ᄀᄀ", 'ᄁ'),
            arguments("ᆨᆺ", 'ᆪ')
        );
    }

    @ParameterizedTest
    @MethodSource("joinJamo_TestParameters")
    @DisplayName("joinJamo(String) with valid arguments")
    void joinJamo_withValidArguments(String jamo, char expected) {
        assertThat(KoreanChar.joinJamo(jamo), equalTo(expected));
    }

    @Test
    @DisplayName("joinJamo(String) with invalid arguments throws IllegalArgumentException")
    void joinJamo_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanChar.joinJamo("A"));
    }

    static Stream<Arguments> splitJamo_TestParameters() {
        return Stream.of(
            // Hangul Compatibility Jamo
            arguments('ㄱ', "ㄱ"),
            arguments('ㅎ', "ㅎ"),
            arguments('ㄲ', "ㄱㄱ"),
            arguments('ㄳ', "ㄱㅅ"),
            // Hangul Jamo
            arguments('ᄀ', "ᄀ"),
            arguments('ᇂ', "ᇂ"),
            arguments('ᄁ', "ᄀᄀ"),
            arguments('ᆪ', "ᆨᆺ")
        );
    }

    @ParameterizedTest
    @MethodSource("splitJamo_TestParameters")
    @DisplayName("splitJamo(char) with valid arguments")
    void splitJamo_withValidArguments(char jamo, String expected) {
        assertThat(KoreanChar.splitJamo(jamo), equalTo(expected));
    }

    @Test
    @DisplayName("splitJamo(char) with invalid arguments throws IllegalArgumentException")
    void splitJamo_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
            KoreanChar.splitJamo('A'));
    }

    static Stream<Arguments> decomposeIntoCompatJamo_TestParameters() {
        return Stream.of(
            arguments('하', "ㅎㅏ"),
            arguments('늘', "ㄴㅡㄹ"),
            arguments('밝', "ㅂㅏㄹㄱ"),
            arguments('꿄', "ㄱㄱㅜㄹㅅ"),
            arguments('쒏', "ㅅㅅㅜㅓㄹㅎ")
        );
    }

    @ParameterizedTest
    @MethodSource("decomposeIntoCompatJamo_TestParameters")
    @DisplayName("decomposeIntoCompatJamo(char) with valid arguments")
    void decomposeIntoCompatJamo_withValidArguments(char syllable, String expected) {
        assertThat(KoreanChar.decomposeIntoCompatJamo(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("decomposeIntoCompatJamo with invalid arguments throws IllegalArgumentException")
    void decomposeIntoCompatJamo_withInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            KoreanChar.decomposeIntoCompatJamo('A');
        });
    }
}
