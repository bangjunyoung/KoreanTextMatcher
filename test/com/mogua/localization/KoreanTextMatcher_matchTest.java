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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KoreanTextMatcher_matchTest {

    private String _text;
    private String _pattern;
    private boolean _expectedSuccess;
    private int _expectedIndex;
    private int _expectedLength;

    public KoreanTextMatcher_matchTest(String text, String pattern, boolean expectedSuccess, int expectedIndex, int expectedLength) {
        _text = text;
        _pattern = pattern;
        _expectedSuccess = expectedSuccess;
        _expectedIndex = expectedIndex;
        _expectedLength = expectedLength;
    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            { "", "", true, 0, 0 },
            { "", "^$", true, 0, 0 },
            { "하늘", "", true, 0, 0 },
            { "하늘", "^", true, 0, 0 },
            { "하늘", "$", true, 0, 0 },
            { "하늘", "하", true, 0, 1 },
            { "하늘", "늘", true, 1, 1 },
            { "하늘", "하늘", true, 0, 2 },
            { "하늘", "ㅎㄴ", true, 0, 2 },
            { "하늘", "ㅎ", true, 0, 1 },
            { "하늘", "ㄴ", true, 1, 1 },
            { "푸른 하늘", "하늘", true, 3, 2 },
            { "푸른 하늘", "ㅎㄴ", true, 3, 2 },
            { "하늘", "^$", false, 0, 0 },
            { "푸른 하늘", "^ㅎㄴ", false, 0, 0 },
            { "푸른 하늘", "푸른$", false, 0, 0 },
            { "푸른 하늘", "ㅎㄹ", false, 0, 0 },
        });
    }

    @Test
    public void match_ReturnsExpectedResult() {
        String message = String.format("text: %s, pattern: %s", _text, _pattern);
        KoreanTextMatcher matcher = new KoreanTextMatcher(_pattern);
        KoreanTextMatch match = matcher.match(_text);
        assertThat(message, match.success(), is(equalTo(_expectedSuccess)));
        if (match.success()) {
            assertThat(message, match.index(), is(equalTo(_expectedIndex)));
            assertThat(message, match.length(), is(equalTo(_expectedLength)));
            assertThat(message, match.length(), is(equalTo(match.value().length())));
            assertTrue(message, _text.contains(match.value()));
        }
    }
}
