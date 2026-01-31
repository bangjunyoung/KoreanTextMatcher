# KoreanTextMatcher 4.0

[![Java CI](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml/badge.svg)](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml)
[![codecov](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher)

KoreanTextMatcher는 한글 음절 근사 매칭 및 초성 검색 기능을 제공하는 자바 라이브러리다. 주요 기능과 특징은 다음과 같다:

- 단순 문자열 검색, 한글 음절 근사 매칭, 초성 매칭 모두 지원
- 한 문자열 내에서 여러 개의 패턴 검색 가능
- 정규식 앵커 `^`와 `$`를 이용하여 패턴의 위치를 검색 대상 문자열의 시작과 끝으로 한정할 수 있음
- Unicode Hangul Jamo와 Unicode Hangul Compatibility Jamo 모두 지원
- 한글 음절을 자모로 분해하는 API 제공
- 가비지 컬렉션을 최소화하도록 세심하게 작성된 코드
- 완전 쓰레드 세이프: 모든 타입이 이뮤터블
- 100개 이상의 유닛 테스트 케이스, 99% 이상의 코드 커버리지 등을 통해 검증된 품질
- 오픈 소스: 수정, 배포, 2차 저작 등 어떤 프로그램에도 자유롭게 사용 가능한 BSD 라이선스.
  상용 프로그램에도 물론 아무 제약없이 사용할 수 있다(저작권 문구는 절대 지우지 마세요!).
- JDK 11 상에서 개발/테스트되었고 안드로이드 프로젝트에도 수정없이 사용 가능

## 4.0에서 달라진 점

- 초성 매칭이 더 정교한 음절 근사 매칭으로 대체되었다.
  예를 들어 3.0에서는 '빨래'라는 텍스트에 '빨래'라는 완전 일치어 또는 'ㅃㄹ'이라는 초성 패턴만이 매칭되었지만, 4.0에서는 '빨래', '빠래', 'ㅃㄹ', 'ㅂㄹ' 등 음절 앞부분의 일부 자소만으로도 매칭이 가능해졌다.
- 중성/종성 관련 API와 음절을 자모로 분해하는 API가 추가되었다.
- 자바와 안드로이드간 바이너리 호환성 문제 때문에 JDK 17을 타겟으로 하도록 변경.

## 음절 근사 매칭과 초성 매칭

KoreanTextMatcher 4.0에서 구현한 한글 음절 근사 매칭은 두벌식 한글 자판 입력을 기준으로 두 한글 음절간의 유사성을 판단하는 방법이다. 예를 들어 '봤'이라는 음절이 주어질 때 'ㅂ', '보', '봐', '봣', '봤'이라는 패턴은 모두 '봤'에 근사적으로 부합한다. 반면 '와', '밨', '봑' 등의 패턴은 각각 초성, 중성, 종성이 '봤'의 것과 다르므로 근사적으로 부합하지 않는다. 음절 근사 매칭을 이용하면 가능한한 적은 수의 한글 입력만으로 대량의 텍스트 내에서 특정 한글 패턴을 쉽게 찾아낼 수 있다.

초성 매칭은 음절 근사 매칭 중 초성만이 부합하는 특수한 경우라고 볼 수 있기 때문에 KoreanTextMatcher에서는 음절 근사 매칭과 초성 매칭을 구분하지 않는다.

## 빌드

빌드에 필요한 도구:

- JDK 17 이상
- Gradle 8.9 이상
- JUnit 6.0 이상

```
$ cd /path/to/KoreanTextMatcher
$ ./gradlew build

BUILD SUCCESSFUL in 6s
5 actionable tasks: 5 executed
```

빌드가 성공하면 `build/libs` 폴더 밑에 `KoreanTextMatcher-X.xx.jar` 파일이 생성되는데, 이것을 여러분의 프로젝트로 임포트해서 쓰면 된다.

**주의**: 안드로이드 개발 환경에서는 JDK 20 이상의 버전에서 빌드한 JAR 파일을 사용하면 파일 포맷 에러가 발생하는 경우가 있다. 그럴 경우 JDK 17 이하에서 빌드한 JAR 파일을 사용하기 바란다. 참고로 [바이너리 배포 페이지](https://github.com/bangjunyoung/KoreanTextMatcher/releases/tag/4.0)에 있는 JAR 파일은 JDK 17에서 빌드한 것이다.

## 프로그래밍

``` java
import io.github.bangjunyoung.KoreanTextMatch;
import io.github.bangjunyoung.KoreanTextMatcher;

/* ... */

KoreanTextMatcher matcher = new KoreanTextMatcher("음ㅈ 그ㅅ");
KoreanTextMatch match = matcher.match("한글 음절 근사 매칭");
if (match.success()) {
    System.out.format("%s: %s[%d]에서 시작, 길이 %d\n",
        match.value(), text, match.index(), match.length());
}
```

자세한 API 사용법은 [위키 페이지](https://github.com/bangjunyoung/KoreanTextMatcher/wiki) 참조.
