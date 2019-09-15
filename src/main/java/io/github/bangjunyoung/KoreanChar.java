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
     * 주어진 문자가 Unicode Hangul Compatibility Jamo 초성인지 검사한다.
     * 
     * @param c 검사할 문자
     * @return {@code c}가 Unicode Hangul Compatibility Jamo 초성이면
     *         {@code true}, 아니면 {@code false}.
     */
    public static boolean isCompatChoseong(char c) {
        return 0x3131 <= c && c <= 0x314E;
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
     *         {@code syllable}이 한글 음절이 아니면 {@code '\0'}.
     */
    public static char getChoseong(char syllable) {
        if (!isSyllable(syllable))
            return '\0';

        return (char)(0x1100 + getChoseongIndex(syllable));
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 초성을 추출한다.
     * 
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 초성.
     *         {@code syllable}이 한글 음절이 아니면 {@code '\0'}.
     */
    public static char getCompatChoseong(char syllable) {
        if (!isSyllable(syllable))
            return '\0';

        return COMPAT_CHOSEONG_COLLECTION[getChoseongIndex(syllable)];
    }

    private static int getChoseongIndex(char syllable) {
        final int sylIndex = syllable - HANGUL_SYLLABLES_BASE;
        return sylIndex / (JUNGSEONG_COUNT * JONGSEONG_COUNT);
    }
}
