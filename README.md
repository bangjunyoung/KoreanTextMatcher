# KoreanTextMatcher 3.0

[![Build Status](https://travis-ci.org/bangjunyoung/KoreanTextMatcher.svg?branch=master)](https://travis-ci.org/bangjunyoung/KoreanTextMatcher)
[![codecov](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher)

KoreanTextMatcher는 자바/안드로이드에서 한글 초성 매칭 검색을 가능하게 해주는 라이브러리다.
주요 기능과 특징은 다음과 같다:

- 단순 문자열 검색과 초성 매칭 검색 모두 지원
- 한 문자열 내에서 여러 개의 패턴 검색 가능
- 정규식 앵커 `^`와 `$`를 이용하여 패턴의 위치를 검색 대상 문자열의 시작과 끝으로 한정할 수 있음
- Unicode Hangul Jamo와 Unicode Hangul Compatibility Jamo 모두 지원
- 사용하기 쉬운 API
- 가비지 컬렉션을 최소화하도록 세심하게 작성된 코드
- 완전 쓰레드 세이프: 모든 타입이 이뮤터블
- 100개 이상의 유닛 테스트 케이스, 99% 이상의 코드 커버리지 등을 통해 검증된 품질
- 오픈 소스: 수정, 배포, 2차 저작 등 어떤 프로그램에도 자유롭게 사용 가능한 BSD 라이선스.
  상용 프로그램에도 물론 아무 제약없이 사용할 수 있다(저작권 문구는 절대 지우지 마세요!).

## 사용법

KoreanTextMatcher는 `KoreanChar`, `KoreanTextMatcher`, `KoreanTextMatch` 세 클래스로 이루어져 있다.

### KoreanChar

`KoreanChar`는 한글 문자에서 초성을 추출하는 데 쓰는 클래스다. `isSyllable()`은 입력 문자가
한글 음절인지, `isCompatChoseong()`/`isChoseong()`은 입력 문자가 초성인지 여부를
알려준다. `getCompatChoseong()`/`getChoseong()`은 한글 음절에서 초성을 추출한다.
사용예는 다음과 같다:

``` java
char c = '한';
boolean isHangulChar = KoreanChar.isSyllable(c);
char compatChoseong = KoreanChar.getCompatChoseong(c);
char choseong = KoreanChar.getChoseong(c);
boolean isCompatChoseong = KoreanChar.isCompatChoseong(compatChoseong);
boolean isChoseong = KoreanChar.isChoseong(choseong);
```

`getCompatChoseong()`/`getChoseong()`은 입력이 한글 음절이 아니면 `'\0'`을 리턴한다.

자음/모음으로만 구분되어 있는 Hangul Compatibility Jamo와 별개로 초성/중성/종성으로 구분되는
Hangul Jamo가 있지만 최소한 Windows와 Android에서는 쓰는 경우가 거의 없는 것 같다. 따라서
대부분은 `xxxCompatChoseong()` API를 쓰면 될 것이다.

### KoreanTextMatcher/KoreanTextMatch

한글 초성 매칭 검색을 수행하는 클래스다. `KoreanTextMatcher`에 매칭 API가 들어 있고
매칭 결과가 `KoreanTextMatch` 인스턴스로 리턴된다. 사용예는 다음과 같다:

``` java
KoreanTextMatcher matcher = new KoreanTextMatcher("ㅊㅅ");
KoreanTextMatch match = matcher.match("한글 초성 매칭");
if (match.success()) {
    System.out.format("%s: %s[%d]에서 시작, 길이 %d\n",
        match.value(), text, match.index(), match.length());
}
```

1회성으로 쓸 때에는 `static` 버전의 `match()`를 쓰는 쪽이 간편하다:

``` java
KoreanTextMatch match = KoreanTextMatcher.match("한글 초성 매칭", "ㅊㅅ");
if (match.success()) {
    System.out.format("%s: %s[%d]에서 시작, 길이 %d\n",
        match.value(), text, match.index(), match.length());
}
```

패턴이 문자열의 시작 또는 끝에 위치하는 경우만 매칭하려면 정규식에서 널리 쓰이는 `^`와 `$`를
각각 쓰면 된다. 단순히 매칭 성공 여부만을 알려주는 `isMatch()`와 함께 쓰면 다음과 같다:

``` java
KoreanTextMatcher.isMatch("하늘", "^ㅎㄴ");  // true
KoreanTextMatcher.isMatch(" 하늘", "^ㅎㄴ"); // false
KoreanTextMatcher.isMatch("하늘", "ㅎㄴ$");  // true
KoreanTextMatcher.isMatch("하늘 ", "ㅎㄴ$"); // false
```

`^`와 `$`는 각각 `String.startsWith()`와 `String.endsWith()`를 대체하는 목적으로 쓸 수 있다.
둘을 한꺼번에 쓰면 `String.equals()`를 쓴 것과 동일한 효과를 얻을 수 있다:

``` java
KoreanTextMatcher.isMatch("하늘", "^ㅎㄴ$");  // true
KoreanTextMatcher.isMatch(" 하늘 ", "^ㅎㄴ$"); // false
```

`KoreanTextMatcher.matches()`는 문자열 내에서 매칭되는 패턴을 모두 찾아
`Iterable<KoreanTextMatch>`로 돌려준다. 검색 결과에서 검색어만 하일라이트할 필요가 있을 때 쓰면
편리하다. 아래는 안드로이드에서 사용한 예다:

``` java
String text = "바닥에 남은 차가운 껍질에 뜨거운 눈물을 부어";

TextView tv = new TextView(getBaseContext());
tv.setTextSize(25F);
tv.setText(text, TextView.BufferType.SPANNABLE);

Spannable spannedText = (Spannable) tv.getText();

KoreanTextMatcher matcher = new KoreanTextMatcher("ㄱㅇ");
for (KoreanTextMatch match : matcher.matches(text)) {
    spannedText.setSpan(new ForegroundColorSpan(Color.RED),
        match.index(), match.index() + match.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
}
```

`matches()` 역시 `match()`처럼 인스턴스 버전과 `static` 버전이 있으므로 필요에 따라 골라 쓰면 된다.
대개는 `static` 쪽이 쓰기 편하다.