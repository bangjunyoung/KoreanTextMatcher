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

import java.util.Arrays;

/**
 * 한글 초성 추출을 위한 클래스
 * <p>
 * Unicode Hangul Jamo와 Unicode Hangul Compatibility Jamo를 모두 지원한다.
 * <p>
 * 이 클래스는 인스턴스 생성이 불가능하다.
 *
 * @author 방준영 &lt;bang.junyoung@gmail.com&gt;
 */
public final class KoreanChar {

    private static final int CHOSEONG_COUNT = 19;
    private static final int JUNGSEONG_COUNT = 21;
    private static final int JONGSEONG_COUNT = 28;
    private static final int HANGUL_SYLLABLE_COUNT = CHOSEONG_COUNT * JUNGSEONG_COUNT * JONGSEONG_COUNT;
    private static final int HANGUL_SYLLABLES_BASE = 0xAC00;
    private static final int HANGUL_SYLLABLES_END = HANGUL_SYLLABLES_BASE + HANGUL_SYLLABLE_COUNT;

    private static final char[] COMPAT_CHOSEONG_COLLECTION = new char[] {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] COMPAT_JUNGSEONG_COLLECTION = new char[]{
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
        'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
        'ㅣ'
    };
    private static final char[] COMPAT_JONGSEONG_COLLECTION = new char[] {
        '\u0000',
        'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ',
        'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ',
        'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    private static final String[] JAMO_STRINGS = new String[] {
        "",

        "ᄀ", "ᄀᄀ", "ᄂ", "ᄃ", "ᄃᄃ", "ᄅ", "ᄆ", "ᄇ", "ᄇᄇ", "ᄉ",
        "ᄉᄉ", "ᄋ", "ᄌ", "ᄌᄌ", "ᄎ", "ᄏ", "ᄐ", "ᄑ", "ᄒ",

        "ᅡ", "ᅢ", "ᅣ", "ᅤ", "ᅥ", "ᅦ", "ᅧ", "ᅨ", "ᅩ", "ᅩᅡ",
        "ᅩᅢ", "ᅩᅵ", "ᅭ", "ᅮ", "ᅮᅥ", "ᅮᅦ", "ᅮᅵ", "ᅲ", "ᅳ", "ᅳᅵ",
        "ᅵ",

        "ᆨ", "ᆨᆨ", "ᆨᆺ", "ᆫ", "ᆫᆽ", "ᆫᇂ", "ᆮ", "ᆯ", "ᆯᆨ", "ᆯᆷ",
        "ᆯᆸ", "ᆯᆺ", "ᆯᇀ", "ᆯᇁ", "ᆯᇂ", "ᆷ", "ᆸ", "ᆸᆺ", "ᆺ", "ᆺᆺ",
        "ᆼ", "ᆽ", "ᆾ", "ᆿ", "ᇀ", "ᇁ", "ᇂ",

        "ㄱ", "ㄱㄱ", "ㄱㅅ", "ㄴ", "ㄴㅈ", "ㄴㅎ", "ㄷ", "ㄷㄷ", "ㄹ", "ㄹㄱ",
        "ㄹㅁ", "ㄹㅂ", "ㄹㅅ", "ㄹㅌ", "ㄹㅍ", "ㄹㅎ", "ㅁ", "ㅂ", "ㅂㅂ", "ㅂㅅ",
        "ㅅ", "ㅅㅅ", "ㅇ", "ㅈ", "ㅈㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ",

        "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅗㅏ",
        "ㅗㅐ", "ㅗㅣ", "ㅛ", "ㅜ", "ㅜㅓ", "ㅜㅔ", "ㅜㅣ", "ㅠ", "ㅡ", "ㅡㅣ",
        "ㅣ"
    };
    private static final char[] JAMO_CHARS = new char[] {
        '\u0000',

        'ᄀ', 'ᄁ', 'ᄂ', 'ᄃ', 'ᄄ', 'ᄅ', 'ᄆ', 'ᄇ', 'ᄈ', 'ᄉ',
        'ᄊ', 'ᄋ', 'ᄌ', 'ᄍ', 'ᄎ', 'ᄏ', 'ᄐ', 'ᄑ', 'ᄒ',

        'ᅡ', 'ᅢ', 'ᅣ', 'ᅤ', 'ᅥ', 'ᅦ', 'ᅧ', 'ᅨ', 'ᅩ', 'ᅪ',
        'ᅫ', 'ᅬ', 'ᅭ', 'ᅮ', 'ᅯ', 'ᅰ', 'ᅱ', 'ᅲ', 'ᅳ', 'ᅴ',
        'ᅵ',

        'ᆨ', 'ᆩ', 'ᆪ', 'ᆫ', 'ᆬ', 'ᆭ', 'ᆮ', 'ᆯ', 'ᆰ', 'ᆱ',
        'ᆲ', 'ᆳ', 'ᆴ', 'ᆵ', 'ᆶ', 'ᆷ', 'ᆸ', 'ᆹ', 'ᆺ', 'ᆻ',
        'ᆼ', 'ᆽ', 'ᆾ', 'ᆿ', 'ᇀ', 'ᇁ', 'ᇂ',

        'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㄺ',
        'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅄ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',

        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
        'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
        'ㅣ'
    };

    private KoreanChar() {} // Can never be instantiated.

    /**
     * 주어진 문자가 Unicode Hangul Jamo 초성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Jamo 초성이면 {@code true},
     *         아니면 {@code false}.
     */
    public static boolean isChoseong(char c) {
        return 0x1100 <= c && c <= 0x1112;
    }

    /**
     * 주어진 문자가 Unicode Hangul Jamo 중성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Jamo 중성이면 {@code true},
     *         아니면 {@code false}.
     */
    public static boolean isJungseong(char c) {
        return 0x1161 <= c && c <= 0x1175;
    }

    /**
     * 주어진 문자가 Unicode Hangul Jamo 종성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Jamo 종성이면 {@code true},
     *         아니면 {@code false}.
     */
    public static boolean isJongseong(char c) {
        return 0x11A8 <= c && c <= 0x11C2;
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 초성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Compatibility Jamo 초성이면
     *         {@code true}, 아니면 {@code false}.
     */
    public static boolean isCompatChoseong(char c) {
        final int index = Arrays.binarySearch(COMPAT_CHOSEONG_COLLECTION, c);
        return index >= 0;
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 중성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Compatibility Jamo 중성이면
     *         {@code true}, 아니면 {@code false}.
     */
    public static boolean isCompatJungseong(char c) {
        final int index = Arrays.binarySearch(COMPAT_JUNGSEONG_COLLECTION, c);
        return index >= 0;
    }

    /**
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 종성인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Compatibility Jamo 종성이면
     *         {@code true}, 아니면 {@code false}.
     */
    public static boolean isCompatJongseong(char c) {
        final int index = Arrays.binarySearch(COMPAT_JONGSEONG_COLLECTION, c);
        return index >= 0;
    }

    /**
     * 주어진 문자가 한글 음절인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 한글 음절이면 {@code true}, 아니면 {@code false}.
     */
    public static boolean isSyllable(char c) {
        return HANGUL_SYLLABLES_BASE <= c && c < HANGUL_SYLLABLES_END;
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Jamo 초성을 추출한다.
     *
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Jamo 초성.
     * @throws IllegalArgumentException {@code syllable}이 한글 음절이 아닐 때.
     */
    public static char getChoseong(char syllable) {
        if (!isSyllable(syllable))
            throw new IllegalArgumentException(String.valueOf(syllable));

        return (char)(0x1100 + getChoseongIndex(syllable));
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 초성을 추출한다.
     *
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 초성.
     * @throws IllegalArgumentException {@code syllable}이 한글 음절이 아닐 때.
     */
    public static char getCompatChoseong(char syllable) {
        if (!isSyllable(syllable))
            throw new IllegalArgumentException(String.valueOf(syllable));

        return COMPAT_CHOSEONG_COLLECTION[getChoseongIndex(syllable)];
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 중성을 추출한다.
     *
     * @param syllable 중성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 중성.
     * @throws IllegalArgumentException {@code syllable}이 한글 음절이 아닐 때.
     */
    public static char getCompatJungseong(char syllable) {
        if (!isSyllable(syllable))
            throw new IllegalArgumentException(String.valueOf(syllable));

        return COMPAT_JUNGSEONG_COLLECTION[getJungseongIndex(syllable)];
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 종성을 추출한다.
     *
     * @param syllable 종성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 종성.
     * @throws IllegalArgumentException {@code syllable}이 한글 음절이 아닐 때.
     */
    public static char getCompatJongseong(char syllable) {
        if (!isSyllable(syllable))
            throw new IllegalArgumentException(String.valueOf(syllable));

        return COMPAT_JONGSEONG_COLLECTION[getJongseongIndex(syllable)];
    }

    public static char compatChoseongToChoseong(char c) {
        final int index = Arrays.binarySearch(COMPAT_CHOSEONG_COLLECTION, c);
        if (index < 0)
            throw new IllegalArgumentException(String.valueOf(c));

        return (char)(0x1100 + index);
    }

    public static char choseongToCompatChoseong(char c) {
        if (!isChoseong(c))
            throw new IllegalArgumentException(String.valueOf(c));

        return COMPAT_CHOSEONG_COLLECTION[(int)c - 0x1100];
    }

    public static char joinJamo(String jamo) {
        final int index = Arrays.binarySearch(JAMO_STRINGS, jamo);
        if (index < 0)
            throw new IllegalArgumentException(jamo);

        return JAMO_CHARS[index];
    }

    public static String splitJamo(char jamo) {
        final int index = Arrays.binarySearch(JAMO_CHARS, jamo);
        if (index < 0)
            throw new IllegalArgumentException(String.valueOf(jamo));

        return JAMO_STRINGS[index];
    }

    public static String decomposeIntoCompatJamo(char syllable) {
        if (!isSyllable(syllable))
            throw new IllegalArgumentException(String.valueOf(syllable));

        String cho = splitJamo(getCompatChoseong(syllable));
        String jung = splitJamo(getCompatJungseong(syllable));
        String jong = splitJamo(getCompatJongseong(syllable));

        return cho + jung + jong;
    }

    private static int getChoseongIndex(char syllable) {
        final int sylIndex = syllable - HANGUL_SYLLABLES_BASE;
        return sylIndex / (JUNGSEONG_COUNT * JONGSEONG_COUNT);
    }

    private static int getJungseongIndex(char syllable) {
        final int sylIndex = syllable - HANGUL_SYLLABLES_BASE;
        return sylIndex % (JUNGSEONG_COUNT * JONGSEONG_COUNT) / JONGSEONG_COUNT;
    }

    private static int getJongseongIndex(char syllable) {
        final int sylIndex = syllable - HANGUL_SYLLABLES_BASE;
        return sylIndex % JONGSEONG_COUNT;
    }
}
