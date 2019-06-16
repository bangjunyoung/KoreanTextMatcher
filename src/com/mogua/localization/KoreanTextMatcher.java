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

package com.mogua.localization;

import java.util.Iterator;

/**
 * 한글 초성 매칭 검색 클래스
 * <p>
 * Unicode Hangul Jamo와 Hangul Compatibility Jamo를 모두 지원한다.
 *
 * @author 방준영 &lt;junyoung@mogua.com&gt;
 */
public final class KoreanTextMatcher {

    private final String _pattern;
    private final boolean _foundStartAnchor, _foundEndAnchor;

    /**
     * KoreanTextMatcher 클래스의 새 인스턴스를 초기화한다.
     * <p>
     * pattern에 한글 초성이 포함되어 있으면 검색 문자열내 해당 위치의 문자와
     * 초성만 대조한다.
     * <p>
     * 정규식 앵커 ^와 $를 사용하여 pattern의 위치를 검색 대상 문자열의 시작과 끝으로
     * 한정할 수 있다.
     *
     * @param pattern 검색할 패턴
     * @throws IllegalArgumentException pattern이 <code>null</code>일 때.
     */
    public KoreanTextMatcher(String pattern) {
        if (pattern == null)
            throw new IllegalArgumentException("pattern: null");

        if (pattern.length() == 0) {
            _foundStartAnchor = _foundEndAnchor = false;
            _pattern = pattern;
        } else {
            _foundStartAnchor = pattern.charAt(0) == '^';
            _foundEndAnchor = pattern.charAt(pattern.length() - 1) == '$';
            _pattern = stripAnchors(pattern);
        }
    }

    /**
     * 주어진 text에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * pattern의 첫번째 출현을 찾는다.
     * <p>
     * text 내 검색 시작 위치를 지정하려면 {@link #match(String, int)}를 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 <code>true</code>일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException text가 <code>null</code>일 때.
     */
    public KoreanTextMatch match(String text) {
        return match(text, 0);
    }

    /**
     * 주어진 text에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * pattern의 첫번째 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 text 내 위치
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 <code>true</code>일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException text가 <code>null</code>일 때,
     *         또는 startIndex가 0보다 작거나 text.length()보다 클 때.
     */
    public KoreanTextMatch match(String text, int startIndex) {
        if (text == null)
            throw new IllegalArgumentException("text: null");
        if (startIndex < 0)
            throw new IllegalArgumentException("startIndex: " + startIndex + " < 0");
        if (startIndex > text.length())
            throw new IllegalArgumentException(
                String.format("startIndex: %d > text.length(): %d", startIndex, text.length()));

        //
        // Optimization: narrow the range of text to be matched for pattern.
        //
        long textRange = getTextRange(text, _pattern.length(), startIndex);
        if (textRange == -1)
            return KoreanTextMatch.EMPTY;
        // textRange is a tuple of (int startIndex, int length).
        startIndex = (int)(textRange >> 32);
        int length = (int)(textRange & 0xFFFFFFF);
        if (length == 0)
            return new KoreanTextMatch(this, text, 0, length);

        return match(text, startIndex, length, _pattern);
    }

    /**
     * 주어진 text에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * pattern의 모든 출현을 찾는다.
     * <p>
     * text 내 검색 시작 위치를 지정하려면 {@link #matches(String, int)}를 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 <code>Iterable&lt;KoreanTextMatch&gt;</code> 인스턴스.
     *         찾은 것이 없으면 빈 리스트를 리턴한다. 
     * @throws IllegalArgumentException text가 <code>null</code>일 때.
     */
    public Iterable<KoreanTextMatch> matches(String text) {
        return matches(text, 0);
    }

    /**
     * 주어진 text에 대해 {@link #KoreanTextMatcher(String)}에 지정해 둔
     * pattern의 모든 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 text 내 위치
     * @return 검색 결과를 담은 <code>Iterable&lt;KoreanTextMatch&gt;</code> 인스턴스.
     * @throws IllegalArgumentException text가 <code>null</code>일 때,
     *         또는 startIndex가 0보다 작거나 text.length()보다 클 때.
     */
    public Iterable<KoreanTextMatch> matches(final String text, final int startIndex) {
        return new Iterable<KoreanTextMatch>() {
            public Iterator<KoreanTextMatch> iterator() {
                return new Iterator<KoreanTextMatch>() {
                    KoreanTextMatch _match = match(text, startIndex);

                    @Override
                    public boolean hasNext() {
                        return _match.success();
                    }

                    @Override
                    public KoreanTextMatch next() {
                        KoreanTextMatch result = _match;
                        _match = _match.nextMatch();
                        return result;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    private KoreanTextMatch match(String text, int startIndex, int length, String pattern) {
        if (pattern.length() == 0)
            return new KoreanTextMatch(this, text, 0, pattern.length());

        for (int i = startIndex; i < startIndex + length - pattern.length() + 1; i++) {
            for (int j = 0; j < pattern.length(); j++) {
                if (!choseongMatches(text.charAt(i + j), pattern.charAt(j)))
                    break;

                if (j == pattern.length() - 1)
                    return new KoreanTextMatch(this, text, i, pattern.length());
            }
        }
        return KoreanTextMatch.EMPTY;
    }

    /**
     * 주어진 text 내에 주어진 pattern이 존재하는지 여부를 조사한다. 
     * <p>
     * pattern에 한글 초성이 포함되어 있으면 검색 문자열내 해당 위치의 문자와
     * 초성만 대조한다.
     * <p>
     * 정규식 앵커 ^와 $를 사용하여 pattern의 위치를 검색 대상 문자열의 시작과 끝으로
     * 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return text 내에 pattern이 존재하면 <code>true</code>, 그렇지 않으면 <code>false</code>.
     * @throws IllegalArgumentException text 또는 pattern이 <code>null</code>일 때.
     */
    public static boolean isMatch(String text, String pattern) {
        return match(text, pattern).success();
    }

    /**
     * 주어진 text 내에서 주어진 pattern의 첫번째 출현을 찾는다.
     * <p>
     * 모든 출현을 찾으려면 {@link #matches(String, String)}를 사용한다.
     * <p>
     * pattern에 한글 초성이 포함되어 있으면 검색 문자열내 해당 위치의 문자와
     * 초성만 대조한다.
     * <p>
     * 정규식 앵커 ^와 $를 사용하여 pattern의 위치를 검색 대상 문자열의 시작과 끝으로
     * 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 <code>true</code>일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException text 또는 pattern이 <code>null</code>일 때. 
     */
    public static KoreanTextMatch match(String text, String pattern) {
        return new KoreanTextMatcher(pattern).match(text);
    }

    /**
     * 주어진 text 내에서 주어진 pattern의 모든 출현을 찾는다.
     * <p>
     * 첫번째 출현만 찾으려면 {@link #match(String, String)}를 사용한다.
     * <p>
     * pattern에 한글 초성이 포함되어 있으면 검색 문자열내 해당 위치의 문자와
     * 초성만 대조한다.
     * <p>
     * 정규식 앵커 ^와 $를 사용하여 pattern의 위치를 검색 대상 문자열의 시작과 끝으로
     * 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return 검색 결과를 담은 <code>Iterable&lt;KoreanTextMatch&gt;</code> 인스턴스.
     * @throws IllegalArgumentException text 또는 pattern이 <code>null</code>일 때. 
     */
    public static Iterable<KoreanTextMatch> matches(String text, String pattern) {
        return new KoreanTextMatcher(pattern).matches(text);
    }

    private static boolean choseongMatches(char a, char b) {
        if (KoreanChar.isCompatChoseong(a) || KoreanChar.isChoseong(a))
            return a == b;

        char c;
        if (KoreanChar.isCompatChoseong(b))
            c = KoreanChar.getCompatChoseong(a);
        else if (KoreanChar.isChoseong(b))
            c = KoreanChar.getChoseong(a);
        else
            c = a;
        return c == b;
    }

    private String stripAnchors(String pattern) {
        if (!_foundStartAnchor && !_foundEndAnchor)
            return pattern;

        int startIndex = _foundStartAnchor ? 1 : 0;
        int length = pattern.length() - (_foundStartAnchor ? 1 : 0) - (_foundEndAnchor ? 1 : 0);
        return pattern.substring(startIndex, startIndex + length);
    }

    private long getTextRange(String text, int hintLength, int startIndex) {
        boolean trimStart = _foundEndAnchor, trimEnd = _foundStartAnchor;

        int length = text.length() - startIndex;

        if (length < hintLength)
            return -1;

        if (trimStart && trimEnd) {
            if (text.length() != hintLength)
                return -1;
        } else if (trimStart) {
            startIndex = text.length() - hintLength;
            length = hintLength;
        } else if (trimEnd) {
            if (startIndex != 0)
                return -1;
            length = hintLength;
        }
        return (long)startIndex << 32 | length;
    }
}
