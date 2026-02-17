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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KoreanCharTests {

    static Stream<Arguments> isSyllableTestParameters() {
        return Stream.of(
            arguments('가', true),
            arguments('힣', true),
            arguments('A', false),
            arguments('\uFFE6', false)
        );
    }

    @ParameterizedTest(name = "isSyllable❨{0}❩ returns {1}")
    @MethodSource("isSyllableTestParameters")
    void isSyllableTest(char syllable, boolean expected) {
        assertThat(KoreanChar.isSyllable(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> isChoseongTestParameters() {
        return Stream.of(
            arguments('ᄀ', true),
            arguments('ᄒ', true),
            arguments('ㄱ', false),
            arguments('ㅎ', false),
            arguments('1', false),
            arguments('A', false)
        );
    }

    @ParameterizedTest(name = "isChoseong❨{0}❩ returns {1}")
    @MethodSource("isChoseongTestParameters")
    void isChoseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isChoseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> isJungseongTestParameters() {
        return Stream.of(
            arguments('ᅡ', true),
            arguments('ᅵ', true),
            arguments('ㅏ', false),
            arguments('ㅣ', false),
            arguments('1', false),
            arguments('A', false)
        );
    }

    @ParameterizedTest(name = "isJungseong❨{0}❩ returns {1}")
    @MethodSource("isJungseongTestParameters")
    void isJungseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isJungseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> isJongseongTestParameters() {
        return Stream.of(
            arguments('ᆨ', true),
            arguments('ᇂ', true),
            arguments('ㄱ', false),
            arguments('ㅎ', false),
            arguments('1', false),
            arguments('A', false)
        );
    }

    @ParameterizedTest(name = "isJongseong❨{0}❩ returns {1}")
    @MethodSource("isJongseongTestParameters")
    void isJongseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isJongseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> isCompatChoseongTestParameters() {
        return Stream.of(
            arguments('ㄱ', true),
            arguments('ㅎ', true),
            arguments('ᄀ', false),
            arguments('ᄒ', false),
            arguments('1', false),
            arguments('A', false)
        );
    }

    @ParameterizedTest(name = "isCompatChoseong❨{0}❩ returns {1}")
    @MethodSource("isCompatChoseongTestParameters")
    void isCompatChoseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isCompatChoseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> isCompatJungseongTestParameters() {
        return Stream.of(
            arguments('1', false),
            arguments('ㅏ', true),
            arguments('ㅣ', true),
            arguments('ᅡ', false),
            arguments('ᅵ', false),
            arguments('\uFFE6', false)
        );
    }

    @ParameterizedTest(name = "isCompatJungseong❨{0}❩ returns {1}")
    @MethodSource("isCompatJungseongTestParameters")
    void isCompatJungseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isCompatJungseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> isCompatJongseongTestParameters() {
        return Stream.of(
            arguments('ㄳ', true),
            arguments('ㅄ', true),
            arguments('ㄸ', false),
            arguments('ㅃ', false),
            arguments('ᆨ', false),
            arguments('ᇂ', false),
            arguments('1', false),
            arguments('A', false)
        );
    }

    @ParameterizedTest(name = "isCompatJongseong❨{0}❩ returns {1}")
    @MethodSource("isCompatJongseongTestParameters")
    void isCompatJongseongTest(char c, boolean expected) {
        assertThat(KoreanChar.isCompatJongseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> getChoseongTestParameters() {
        return Stream.of(
            arguments('강', 'ᄀ'),
            arguments('한', 'ᄒ')
        );
    }

    @ParameterizedTest(name = "getChoseong❨{0}❩ returns {1}")
    @MethodSource("getChoseongTestParameters")
    void getChoseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getChoseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getChoseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getChoseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getChoseongExceptionTestParameters")
    void getChoseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getChoseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getJungseongTestParameters() {
        return Stream.of(
            arguments('한', 'ᅡ'),
            arguments('글', 'ᅳ')
        );
    }

    @ParameterizedTest(name = "getJungseong❨{0}❩ returns {1}")
    @MethodSource("getJungseongTestParameters")
    void getJungseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getJungseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getJungseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getJungseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getJungseongExceptionTestParameters")
    void getJungseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getJungseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getJongseongTestParameters() {
        return Stream.of(
            arguments('나', '\u0000'),
            arguments('한', 'ᆫ'),
            arguments('값', 'ᆹ')
        );
    }

    @ParameterizedTest(name = "getJongseong❨{0}❩ returns {1}")
    @MethodSource("getJongseongTestParameters")
    void getJongseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getJongseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getJongseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getJongseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getJongseongExceptionTestParameters")
    void getJongseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getJongseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getCompatChoseongTestParameters() {
        return Stream.of(
            arguments('하', 'ㅎ'),
            arguments('늘', 'ㄴ')
        );
    }

    @ParameterizedTest(name = "getCompatChoseong❨{0}❩ returns {1}")
    @MethodSource("getCompatChoseongTestParameters")
    void getCompatChoseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getCompatChoseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getCompatChoseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getCompatChoseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getCompatChoseongExceptionTestParameters")
    void getCompatChoseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getCompatChoseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getCompatJungseongTestParameters() {
        return Stream.of(
            arguments('한', 'ㅏ'),
            arguments('글', 'ㅡ')
        );
    }

    @ParameterizedTest(name = "getCompatJungseong❨{0}❩ returns {1}")
    @MethodSource("getCompatJungseongTestParameters")
    void getCompatJungseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJungseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getCompatJungseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getCompatJungseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getCompatJungseongExceptionTestParameters")
    void getCompatJungseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getCompatJungseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getCompatJongseongTestParameters() {
        return Stream.of(
            arguments('한', 'ㄴ'),
            arguments('글', 'ㄹ')
        );
    }

    @ParameterizedTest(name = "getCompatJongseong❨{0}❩ returns {1}")
    @MethodSource("getCompatJongseongTestParameters")
    void getCompatJongseongTest(char syllable, char expected) {
        assertThat(KoreanChar.getCompatJongseong(syllable)).isEqualTo(expected);
    }

    static Stream<Arguments> getCompatJongseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "getCompatJongseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("getCompatJongseongExceptionTestParameters")
    void getCompatJongseongExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.getCompatJongseong(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> compatChoseongToChoseongTestParameters() {
        return Stream.of(
            arguments('\u3131', '\u1100'),
            arguments('\u314E', '\u1112')
        );
    }

    @ParameterizedTest(name = "compatChoseongToChoseong❨{0}❩ returns {1}")
    @MethodSource("compatChoseongToChoseongTestParameters")
    void compatChoseongToChoseongTest(char c, char expected) {
        assertThat(KoreanChar.compatChoseongToChoseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> compatChoseongToChoseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "compatChoseongToChoseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("compatChoseongToChoseongExceptionTestParameters")
    void compatChoseongToChoseongExceptionTest(char c) {
        assertThatThrownBy(() -> KoreanChar.compatChoseongToChoseong(c))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> choseongToCompatChoseongTestParameters() {
        return Stream.of(
            arguments('\u1100', '\u3131'),
            arguments('\u1112', '\u314E')
        );
    }

    @ParameterizedTest(name = "choseongToCompatChoseong❨{0}❩ returns {1}")
    @MethodSource("choseongToCompatChoseongTestParameters")
    void choseongToCompatChoseongTest(char c, char expected) {
        assertThat(KoreanChar.choseongToCompatChoseong(c)).isEqualTo(expected);
    }

    static Stream<Arguments> choseongToCompatChoseongExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "choseongToCompatChoseong❨{0}❩ throws IllegalArgumentException")
    @MethodSource("choseongToCompatChoseongExceptionTestParameters")
    void choseongToCompatChoseongExceptionTest(char c) {
        assertThatThrownBy(() -> KoreanChar.choseongToCompatChoseong(c))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> joinJamoTestParameters() {
        return Stream.of(
            // 한글 단자모를 복자모로 결합할 때 빈 문자열을 적법한 자모로 간주하는 것이 코드가 자연스러워짐
            arguments("", '\u0000'),
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

    @ParameterizedTest(name = "joinJamo❨{0}❩ returns {1}")
    @MethodSource("joinJamoTestParameters")
    void joinJamoTest(String jamo, char expected) {
        assertThat(KoreanChar.joinJamo(jamo)).isEqualTo(expected);
    }

    static Stream<Arguments> joinJamoExceptionTestParameters() {
        return Stream.of(
            arguments("A"),
            arguments("ㄱㅎ"),
            arguments("ㄱㄱㅅ")
        );
    }

    @ParameterizedTest(name = "joinJamo❨{0}❩ throws IllegalArgumentException")
    @MethodSource("joinJamoExceptionTestParameters")
    void joinJamoExceptionTest(String s) {
        assertThatThrownBy(() -> KoreanChar.joinJamo(s))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> splitJamoTestParameters() {
        return Stream.of(
            // 한글 복자모를 단자모로 분해할 때 '\u0000'을 적법한 자모로 간주하는 것이 코드가 자연스러워짐
            arguments('\u0000', ""),
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

    @ParameterizedTest(name = "splitJamo❨{0}❩ returns {1}")
    @MethodSource("splitJamoTestParameters")
    void splitJamoTest(char jamo, String expected) {
        assertThat(KoreanChar.splitJamo(jamo)).isEqualTo(expected);
    }

    static Stream<Arguments> splitJamoExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢')
        );
    }

    @ParameterizedTest(name = "splitJamo❨{0}❩ throws IllegalArgumentException")
    @MethodSource("splitJamoExceptionTestParameters")
    void splitJamoExceptionTest(char c) {
        assertThatThrownBy(() -> KoreanChar.splitJamo(c))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> decomposeTestParameters() {
        return Stream.of(
            arguments('하', List.of("\u1112", "\u1161")),
            arguments('늘', List.of("\u1102", "\u1173", "\u11AF")),
            arguments('밝', List.of("\u1107", "\u1161", "\u11AF\u11A8")),
            arguments('꿄', List.of("\u1100\u1100", "\u116E", "\u11AF\u11BA")),
            arguments('쒏', List.of("\u1109\u1109", "\u116E\u1165", "\u11AF\u11C2"))
        );
    }

    @ParameterizedTest(name = "decompose❨{0}❩ returns {1}")
    @MethodSource("decomposeTestParameters")
    void decomposeTest(char syllable, List<String> expected) {
        List<String> actual = Arrays.asList(KoreanChar.decompose(syllable));
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> decomposeExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "decompose❨{0}❩ throws IllegalArgumentException")
    @MethodSource("decomposeExceptionTestParameters")
    void decomposeExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.decompose(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> decomposeToCompatTestParameters() {
        return Stream.of(
            arguments('하', List.of("ㅎ", "ㅏ")),
            arguments('늘', List.of("ㄴ", "ㅡ", "ㄹ")),
            arguments('밝', List.of("ㅂ", "ㅏ", "ㄹㄱ")),
            arguments('꿄', List.of("ㄱㄱ", "ㅜ", "ㄹㅅ")),
            arguments('쒏', List.of("ㅅㅅ", "ㅜㅓ", "ㄹㅎ"))
        );
    }

    @ParameterizedTest(name = "decomposeToCompat❨{0}❩ returns {1}")
    @MethodSource("decomposeToCompatTestParameters")
    void decomposeToCompatTest(char syllable, List<String> expected) {
        List<String> actual = Arrays.asList(KoreanChar.decomposeToCompat(syllable));
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> decomposeToCompatExceptionTestParameters() {
        return Stream.of(
            arguments('A'),
            arguments('1'),
            arguments(' '),
            arguments('!'),
            arguments('漢'),
            arguments('\u0000')
        );
    }

    @ParameterizedTest(name = "decomposeToCompat❨{0}❩ throws IllegalArgumentException")
    @MethodSource("decomposeToCompatExceptionTestParameters")
    void decomposeToCompatExceptionTest(char syllable) {
        assertThatThrownBy(() -> KoreanChar.decomposeToCompat(syllable))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
