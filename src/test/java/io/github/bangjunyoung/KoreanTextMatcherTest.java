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
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KoreanTextMatcherTest {

    @Test
    @DisplayName("new KoreanTextMatcher(null) throws IllegalArgumentException")
    void KoreanTextMatcher_throwsExceptionOnNullPatternArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new KoreanTextMatcher(null));
    }

    @Test
    @DisplayName("static isMatch(null, \"\") throws IllegalArgumentException")
    void isMatch_throwsExceptionOnNullTextArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.isMatch(null, ""));
    }

    @Test
    @DisplayName("static isMatch(\"\", null) throws IllegalArgumentException")
    void isMatch_throwsExceptionOnNullPatternArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.isMatch("", null));
    }

    @Test
    @DisplayName("static match(null, \"\") throws IllegalArgumentException")
    void static_match_throwsExceptionOnNullTextArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.match(null, ""));
    }

    @Test
    @DisplayName("static match(\"\", null) throws IllegalArgumentException")
    void static_match_throwsExceptionOnNullPatternArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            KoreanTextMatcher.match("", null));
    }

    @Test
    @DisplayName("match(null) throws IllegalArgumentException")
    void match_throwsExceptionOnNullTextArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match(null);
        });
    }

    @Test
    @DisplayName("match(text, startIndex) with startIndex < 0 throws IllegalArgumentException")
    void match_throwsExceptionOnNegativeStartIndexArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match("", -1);
        });
    }

    @Test
    @DisplayName("match(text, startIndex) with too large startIndex throws IllegalArgumentException")
    void match_throwsExceptionOnTooLargeStartIndexArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String text = "";
            KoreanTextMatcher matcher = new KoreanTextMatcher("");
            matcher.match(text, text.length() + 1);
        });
    }

    @Test
    @DisplayName("Instance of Iterable<KoreanTextMatch> throws UnsupportedOperationException if remove() is called with it")
    void matches_throwsExceptionOnCallingRemove() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Iterable<KoreanTextMatch> matches = KoreanTextMatcher.matches("", "");
            matches.iterator().remove();
        });
    }

    @Test
    @DisplayName("nextMatch() returns EMPTY if current match is EMPTY")
    void nextMatch_returnsEMPTYIfCurrentMatchIsEMPTY() {
        assertThat(KoreanTextMatch.EMPTY.nextMatch(), equalTo(KoreanTextMatch.EMPTY));
    }
}
