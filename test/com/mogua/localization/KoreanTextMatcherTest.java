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

import org.junit.Test;

public class KoreanTextMatcherTest {
    @Test(expected = IllegalArgumentException.class)
    public void KoreanTextMatch_ThrowsExceptionOnNullPatternArgument() {
        new KoreanTextMatcher(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isMatch_ThrowsExceptionOnNullTextArgument() {
        KoreanTextMatcher.isMatch(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isMatch_ThrowsExceptionOnNullPatternArgument() {
        KoreanTextMatcher.isMatch("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void static_match_ThrowsExceptionOnNullTextArgument() {
        KoreanTextMatcher.match(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void static_match_ThrowsExceptionOnNullPatternArgument() {
        KoreanTextMatcher.match("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void match_ThrowsExceptionOnNullTextArgument() {
        String pattern = "";
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern);
        matcher.match(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void match_ThrowsExceptionOnNegativeStartIndexArgument() {
        String pattern = "", text = "";
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern);
        matcher.match(text, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void match_ThrowsExceptionOnTooLargeStartIndexArgument() {
        String pattern = "", text = "";
        KoreanTextMatcher matcher = new KoreanTextMatcher(pattern);
        matcher.match(text, text.length() + 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void matches_ThrowsExceptionOnCallingRemove() {
        String pattern = "", text = "";
        Iterable<KoreanTextMatch> matches = KoreanTextMatcher.matches(text, pattern);
        matches.iterator().remove();
    }

    @Test
    public void nextMatch_ReturnsEMPTYIfCurrentMatchIsEMPTY() {
        assertThat(KoreanTextMatch.EMPTY.nextMatch(), is(equalTo(KoreanTextMatch.EMPTY)));
    }
}
