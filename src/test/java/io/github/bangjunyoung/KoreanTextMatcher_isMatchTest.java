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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KoreanTextMatcher_isMatchTest {

    private String _text;
    private String _pattern;
    private boolean _expectedResult;

    public KoreanTextMatcher_isMatchTest(String text, String pattern, boolean expectedResult) {
        _text = text;
        _pattern = pattern;
        _expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            { "하늘", "", true },
            { "하늘", "^", true },
            { "하늘", "$", true },
            { "", "^$", true },
            { "하늘", "하늘", true },
            { " 하늘", "하늘", true },
            { "하늘 ", "하늘", true },
            { " 하늘 ", "하늘", true },
            { "하늘", "^하늘", true },
            { "하늘 ", "^하늘", true },
            { "하늘", "하늘$", true },
            { " 하늘", "하늘$", true },
            { "하늘", "^하늘$", true },
            { "하늘", "하ㄴ", true },
            { "하늘", "^하ㄴ", true },
            { "하늘", "하ㄴ$", true },
            { "하늘", "^하ㄴ$", true },
            { "하늘", "ㅎ늘", true },
            { "하늘", "^ㅎ늘", true },
            { "하늘", "ㅎ늘$", true },
            { "하늘", "^ㅎ늘$", true },
            { "하늘", "ㅎㄴ", true },
            { "하늘 ", "ㅎㄴ", true },
            { " 하늘", "ㅎㄴ", true },
            { " 하늘 ", "ㅎㄴ", true },
            { "하늘", "^ㅎㄴ", true },
            { "하늘", "ㅎㄴ$", true },
            { "하늘", "^ㅎㄴ$", true },
            { " 방준영 ", "ㅂㅈㅇ", true },
            { "방ㅈㅇ", "ㅂㅈㅇ", true },
            { "방ㅈㅇ", "ㅂㅈㅇ", true },
            { " 방ㅈㅇ ", "ㅂㅈㅇ", true },
            { "방ㅈㅇ", "^ㅂㅈㅇ", true },
            { " 방ㅈㅇ", "ㅂㅈㅇ$", true },
            { "방준영", "\u1107\u110C\u110B", true },
            { "\u1107준영", "\u1107\u110C\u110B", true },
            { "하늘", "^$", false },
            { "하 늘", "하늘", false },
            { " 하 늘", "하늘", false },
            { "하 늘 ", "하늘", false },
            { " 하 늘 ", "하늘", false },
            { "하늘", "하를", false },
            { " 하늘", "^하늘", false },
            { " 하늘 ", "^하늘", false },
            { "하늘 ", "하늘$", false },
            { " 하늘 ", "하늘$", false },
            { " 하늘", "^하늘$", false },
            { "하늘 ", "^하늘$", false },
            { "하늘", "ㅎ느", false },
            { "하늘", "^ㅎ느", false },
            { "하늘", "ㅎ느$", false },
            { "하늘", "^ㅎ느$", false },
            { "하늘", "하ㄹ", false },
            { "하늘", "^하ㄹ", false },
            { "하늘", "하ㄹ$", false },
            { "하늘", "^하ㄹ$", false },
            { "하늘", "하ㄹ", false },
            { "하늘", "^ㅎㄹ", false },
            { "하늘", "ㅎㄹ$", false },
            { "하늘", "^ㅎㄹ$", false },
            { "방준영", "ㅂㅇㅈ", false },
            { " 방준영 ", "ㅂㅇㅈ", false },
            { "방ㅈㅇ", "ㅂㅇㅈ", false },
            { "방준", "ㅂㅈㅇ", false },
        });
    }

    @Test
    public void isMatch_ReturnsExpectedResult() {
        assertThat(String.format("text: %s, pattern: %s", _text, _pattern),
            KoreanTextMatcher.isMatch(_text, _pattern), is(equalTo(_expectedResult)));
    }
}
