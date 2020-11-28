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
    @DisplayName("isSyllable(char) returns true for a syllable")
    void isSyllable_returnsTrueForSyllable(char syllable) {
        assertTrue(KoreanChar.isSyllable(syllable));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\uABFF', '\uD7A4' })
    @DisplayName("isSyllable(char) returns false for a non-syllable")
    void isSyllable_returnsFalseForNonSyllable(char syllable) {
        assertFalse(KoreanChar.isSyllable(syllable));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ᄀ', 'ᄒ' })
    @DisplayName("isChoseong(char) returns true for a choseong")
    void isChoseong_returnsTrueForChoseong(char c) {
        assertTrue(KoreanChar.isChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\u10ff', '\u1200' })
    @DisplayName("isChoseong(char) returns false for a non-choseong")
    void isChoseong_returnsFalseForNonChoseong(char c) {
        assertFalse(KoreanChar.isChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ᅡ', 'ᅵ' })
    @DisplayName("isJungseong(char) returns true for a jungseong")
    void isJungseong_returnsTrueForJungseong(char c) {
        assertTrue(KoreanChar.isJungseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\u1160', '\u1176' })
    @DisplayName("isJungseong(char) returns false for a non-jungseong")
    void isJungseong_returnsFalseForNonJungseong(char c) {
        assertFalse(KoreanChar.isJungseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ᆨ', 'ᇂ' })
    @DisplayName("isJongseong(char) returns true for a jongseong")
    void isJongseong_returnsTrueForJongseong(char c) {
        assertTrue(KoreanChar.isJongseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\u11A7', '\u11C3' })
    @DisplayName("isJongseong(char) returns false for a non-jongseong")
    void isJongseong_returnsFalseForNonJongseong(char c) {
        assertFalse(KoreanChar.isJongseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄱ', 'ㅎ' })
    @DisplayName("isCompatChoseong(char) returns true for a Compat choseong")
    void isCompatChoseong_returnsTrueForCompatChoseong(char c) {
        assertTrue(KoreanChar.isCompatChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄳ', 'ㅄ' })
    @DisplayName("isCompatChoseong(char) returns false for a non-Compat choseong")
    void isCompatChoseong_returnsFalseForNonCompatChoseong(char c) {
        assertFalse(KoreanChar.isCompatChoseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㅏ', 'ㅣ' })
    @DisplayName("isCompatJungseong(char) returns true for a Compat jungseong")
    void isCompatJungseong_returnsTrueForCompatJungseong(char c) {
        assertTrue(KoreanChar.isCompatJungseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { '\u314E', '\u3164' })
    @DisplayName("isCompatJungseong(char) returns false for a non-Compat jungseong")
    void isCompatJungseong_returnsFalseForNonCompatJungseong(char c) {
        assertFalse(KoreanChar.isCompatJungseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄳ', 'ㅄ' })
    @DisplayName("isCompatJongseong(char) returns true for a Compat jongseong")
    void isCompatJongseong_returnsTrueForCompatJongseong(char c) {
        assertTrue(KoreanChar.isCompatJongseong(c));
    }

    @ParameterizedTest
    @ValueSource(chars = { 'ㄸ', 'ㅃ' })
    @DisplayName("isCompatJongseong(char) returns false for a non-Compat jongseong")
    void isCompatJongseong_returnsFalseForNonCompatJongseong(char c) {
        assertFalse(KoreanChar.isCompatJongseong(c));
    }

    static Stream<Arguments> getChoseongTestParameters() {
        return Stream.of(
            arguments('강', 'ᄀ'),
            arguments('한', 'ᄒ')
        );
    }

    @ParameterizedTest
    @MethodSource("getChoseongTestParameters")
    @DisplayName("getChoseong(char) returns choseong of a syllable")
    void getChoseong_returnsChoseongOfSyllable(char syllable, char expected) {
        assertThat(KoreanChar.getChoseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getChoseong(char) throws an exception for an invalid argument")
    void getChoseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getChoseong('A'));
    }

    static Stream<Arguments> getJungseongTestParameters() {
        return Stream.of(
            arguments('한', 'ᅡ'),
            arguments('글', 'ᅳ')
        );
    }

    @ParameterizedTest
    @MethodSource("getJungseongTestParameters")
    @DisplayName("getJungseong(char) returns jungseong of a syllable")
    void getJungseong_returnsJungseongOfSyllable(char syllable, char expected) {
        assertThat(KoreanChar.getJungseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getJungseong(char) throws an exception for an invalid argument")
    void getJungseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getJungseong('A'));
    }

    static Stream<Arguments> getJongseongTestParameters() {
        return Stream.of(
            arguments('나', '\u0000'),
            arguments('한', 'ᆫ'),
            arguments('값', 'ᆹ')
        );
    }

    @ParameterizedTest
    @MethodSource("getJongseongTestParameters")
    @DisplayName("getJongseong(char) returns jongseong of a syllable")
    void getJongseong_returnsJongseongOfSyllable(char syllable, char expected) {
        assertThat(KoreanChar.getJongseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getJongseong(char) throws an exception for an invalid argument")
    void getJongseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getJongseong('A'));
    }

    static Stream<Arguments> getCompatChoseongTestParameters() {
        return Stream.of(
            arguments('하', 'ㅎ'),
            arguments('늘', 'ㄴ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatChoseongTestParameters")
    @DisplayName("getCompatChoseong(char) returns Compat choseong of a syllable")
    void getCompatChoseong_returnsCompatChoseongOfSyllable(char syllable, char expected) {
        assertThat(KoreanChar.getCompatChoseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatChoseong(char) throws an exception for an invalid argument")
    void getCompatChoseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getCompatChoseong('A'));
    }

    static Stream<Arguments> getCompatJungseongTestParameters() {
        return Stream.of(
            arguments('한', 'ㅏ'),
            arguments('글', 'ㅡ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatJungseongTestParameters")
    @DisplayName("getCompatJungseong(char) returns Compat jungseong of a syllable")
    void getCompatJungseong_returnsCompatJungseongOfSyllable(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJungseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatJungseong(char) throws an exception for an invalid argument")
    void getCompatJungseong_throwsIllegalArgumentExceptionForNonSyllable() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getCompatJungseong('A'));
    }

    static Stream<Arguments> getCompatJongseongTestParameters() {
        return Stream.of(
            arguments('한', 'ㄴ'),
            arguments('글', 'ㄹ')
        );
    }

    @ParameterizedTest
    @MethodSource("getCompatJongseongTestParameters")
    @DisplayName("getCompatJongseong(char) with a valid argument")
    void getCompatJongseong_withValidArguments(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJongseong(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("getCompatJongseong(char) throws an exception for an invalid argument")
    void getCompatJongseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.getCompatJongseong('A'));
    }

    static Stream<Arguments> compatChoseongToChoseongTestParameters() {
        return Stream.of(
            arguments('\u3131', '\u1100'),
            arguments('\u314E', '\u1112')
        );
    }

    @ParameterizedTest
    @MethodSource("compatChoseongToChoseongTestParameters")
    @DisplayName("compatChoseongToChoseong(char) returns the corresponding choseong to a Compat choseong")
    void compatChoseongToChoseong_returnsChoseongForCompatChoseong(char c, char expected) {
        assertThat(KoreanChar.compatChoseongToChoseong(c), equalTo(expected));
    }

    @Test
    @DisplayName("compatChoseongToChoseong(char) throws an exception for an invalid argument")
    void compatChoseongToChoseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.compatChoseongToChoseong('A'));
    }

    static Stream<Arguments> choseongToCompatChoseongTestParameters() {
        return Stream.of(
            arguments('\u1100', '\u3131'),
            arguments('\u1112', '\u314E')
        );
    }

    @ParameterizedTest
    @MethodSource("choseongToCompatChoseongTestParameters")
    @DisplayName("choseongToCompatChoseong(char) returns the corresponding Compat choseong to a choseong")
    void choseongToCompatChoseong_returnsCompatChoseongForChoseong(char c, char expected) {
        assertThat(KoreanChar.choseongToCompatChoseong(c), equalTo(expected));
    }

    @Test
    @DisplayName("choseongToCompatChoseong(char) throws an exception for an invalid argument")
    void choseongToCompatChoseong_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.choseongToCompatChoseong('A'));
    }

    static Stream<Arguments> joinJamoTestParameters() {
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
    @MethodSource("joinJamoTestParameters")
    @DisplayName("joinJamo(String) with a valid argument")
    void joinJamo_withValidArgument(String jamo, char expected) {
        assertThat(KoreanChar.joinJamo(jamo), equalTo(expected));
    }

    @Test
    @DisplayName("joinJamo(String) throws an exception for an invalid argument")
    void joinJamo_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.joinJamo("A"));
    }

    static Stream<Arguments> splitJamoTestParameters() {
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
    @MethodSource("splitJamoTestParameters")
    @DisplayName("splitJamo(char) with a valid argument")
    void splitJamo_withValidArguments(char jamo, String expected) {
        assertThat(KoreanChar.splitJamo(jamo), equalTo(expected));
    }

    @Test
    @DisplayName("splitJamo(char) throws an exception for an invalid argument")
    void splitJamo_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.splitJamo('A'));
    }

    static Stream<Arguments> decomposeTestParameters() {
        return Stream.of(
            arguments('하', new String[] { "\u1112", "\u1161" }),
            arguments('늘', new String[] { "\u1102", "\u1173" ,"\u11AF" }),
            arguments('밝', new String[] { "\u1107", "\u1161" ,"\u11AF\u11A8" }),
            arguments('꿄', new String[] { "\u1100\u1100" ,"\u116E", "\u11AF\u11BA" }),
            arguments('쒏', new String[] { "\u1109\u1109" ,"\u116E\u1165", "\u11AF\u11C2" })
        );
    }

    @ParameterizedTest
    @MethodSource("decomposeTestParameters")
    @DisplayName("decompose(char) with a valid argument")
    void decompose_withValidArgument(char syllable, String[] expected) {
        assertThat(KoreanChar.decompose(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("decompose(char) throws an exception for an invalid argument")
    void decompose_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.decompose('A'));
    }

    static Stream<Arguments> decomposeCompatTestParameters() {
        return Stream.of(
            arguments('하', new String[] { "ㅎ", "ㅏ" }),
            arguments('늘', new String[] { "ㄴ", "ㅡ", "ㄹ" }),
            arguments('밝', new String[] { "ㅂ", "ㅏ", "ㄹㄱ" }),
            arguments('꿄', new String[] { "ㄱㄱ", "ㅜ", "ㄹㅅ" }),
            arguments('쒏', new String[] { "ㅅㅅ", "ㅜㅓ", "ㄹㅎ"})
        );
    }

    @ParameterizedTest
    @MethodSource("decomposeCompatTestParameters")
    @DisplayName("decomposeCompat(char) with a valid argument")
    void decomposeCompat_withValidArgument(char syllable, String[] expected) {
        assertThat(KoreanChar.decomposeCompat(syllable), equalTo(expected));
    }

    @Test
    @DisplayName("decomposeCompat(char) throws an exception for an invalid argument")
    void decomposeCompat_throwsExceptionForInvalidArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> KoreanChar.decomposeCompat('A'));
    }
}
