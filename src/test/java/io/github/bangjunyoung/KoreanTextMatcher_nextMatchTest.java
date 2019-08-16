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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KoreanTextMatcher_nextMatchTest {

    private String _text;
    private String _pattern;
    private int _expectedMatchCount;

    public KoreanTextMatcher_nextMatchTest(String text, String pattern, int expectedMatchCount) {
        _text = text;
        _pattern = pattern;
        _expectedMatchCount = expectedMatchCount;
    }

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
            { "", "파란", 0 },
            { "파란", "파란", 1 },
            { "파란", "^파란$", 1 },
            { " 파란", "^파란$", 0 },
            { "파란 하늘 파란 나라", "^파란", 1 },
            { "파란 하늘 파란 나라", "파란", 2 },
            { "하얀 나라 파란 나라", "나라$", 1 },
            { "하늘 별 하늘", "하늘", 2 },
            { "하늘 별 하늘 달", "하늘", 2 },
            { "하늘하늘하늘", "ㅎㄴ", 3 },
        });
    }

    @Test
    public void nextMatch_ReturnsExpectedResult() {
        String message = String.format("text: %s, pattern: %s", _text, _pattern);
        KoreanTextMatcher matcher = new KoreanTextMatcher(_pattern);
        KoreanTextMatch match = matcher.match(_text);
        int matchCount = 0;
        while (match.success()) {
            matchCount++;
            int index = match.index();
            int length = match.length();
            String value = match.value();
            assertTrue(message, _text.substring(index, index + length).equals(value));
            match = match.nextMatch();
        }
        assertThat(message, matchCount, is(equalTo(_expectedMatchCount)));
    }
}
