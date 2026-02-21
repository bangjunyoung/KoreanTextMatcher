# KoreanTextMatcher 4.0

[![Java CI](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml/badge.svg)](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml)
[![codecov](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher)
[![JitPack](https://jitpack.io/v/bangjunyoung/KoreanTextMatcher.svg)](https://jitpack.io/#bangjunyoung/KoreanTextMatcher)

KoreanTextMatcher는 한글 음절 근사 매칭 및 초성 검색 기능을 제공하는 자바 라이브러리다. 주요 기능과 특징은 다음과 같다:

- 단순 문자열 검색, 한글 음절 근사 매칭, 초성 매칭 모두 지원
- 한 문자열 내에서 여러 개의 패턴 검색 가능
- 정규식 앵커 `^`와 `$`를 이용하여 패턴의 위치를 검색 대상 문자열의 시작과 끝으로 한정할 수 있음
- Unicode Hangul Jamo (U+1100 ~)와 Unicode Hangul Compatibility Jamo (U+3131 ~) 모두 지원
- 초성/중성/종성/자모/음절을 식별하고 가공하는 다양한 종류의 API 제공
- 가비지 컬렉션을 최소화하도록 세심하게 작성된 코드
- 완전 쓰레드 세이프: 모든 타입이 이뮤터블
- 300개 이상의 유닛 테스트 케이스, 99% 이상의 코드 커버리지 등을 통해 검증된 품질
- 오픈 소스: 수정, 배포, 2차 저작 등 어떤 프로그램에도 자유롭게 사용 가능한 BSD 라이선스.
  상용 프로그램에도 물론 아무 제약없이 사용할 수 있다(저작권 문구는 절대 지우지 마세요!).
- JDK 17에서 개발/테스트되었고 안드로이드 프로젝트에도 수정없이 사용 가능

## 4.0에서 달라진 점

- 단순한 초성 매칭이 정교한 음절 근사 매칭으로 대체되었다.
  예를 들어 3.0에서는 '빨래'라는 텍스트에 '빨래'라는 완전 일치어 또는 'ㅃㄹ'이라는 초성 패턴만이 매칭되었지만, 4.0에서는 '빨래', '빠래', 'ㅃ래', 'ㅃㄹ', 'ㅂㄹ' 등 음절 앞부분의 일부 자소만으로도 매칭이 가능해졌다.
- 음절 근사 매칭의 특수한 형태인 도깨비불 매칭도 지원한다. 도깨비불 현상은 두벌식 키보드에서 한글을 입력할 때 불가피하게 생기는 현상으로, 예를 들어 "하늘"이라는 단어를 입력할 때 "ㅎ" -> "하" -> "한" -> "하느" -> "하늘"처럼 뒤음절의 초성 "ㄴ"이 앞음절의 종성 자리에 먼저 달라붙었다가 뒤음절의 중성 "ㅡ"가 입력된 다음에야 제자리로 옮겨가는 것을 말한다.
- 초성/중성/종성/자모/음절을 식별하고 가공하는 다양한 종류의 API가 추가되었다.
- 자바와 안드로이드간 바이너리 호환성 문제 때문에 JDK 17을 타겟으로 하도록 변경.

## 빌드

빌드에 필요한 도구:

- JDK 17 이상
- Gradle 8.9 이상
- JUnit 6.0 이상

필요한 도구들이 모두 설치되었다면 KoreanTextMatcher의 빌드는 매우 쉽다. 최상위 소스 폴더에서 아래 명령을 실행하면 된다:

```shell
./gradlew build
```

빌드가 성공하면 `build/libs` 폴더 밑에 `KoreanTextMatcher-X.xx.jar` 파일이 생성되는데, 이것을 여러분의 프로젝트로 임포트해서 쓰면 된다.

**주의**: 안드로이드 개발 환경에서는 JDK 20 이상의 버전에서 빌드한 JAR 파일을 사용하면 파일 포맷 에러가 발생하는 경우가 있다. 그럴 경우 JDK 17 이하에서 빌드한 JAR 파일을 사용하기 바란다. 참고로 [바이너리 배포 페이지](https://github.com/bangjunyoung/KoreanTextMatcher/releases/tag/4.0)에 있는 JAR 파일은 JDK 17에서 빌드한 것이다.

## 미리 빌드된 바이너리 이용

소스 코드를 직접 빌드하는 대신 미리 빌드된 바이너리를 [JitPack](https://jitpack.io) 서비스를 이용 여러분의 프로젝트로 임포트하는 방법도 있다.
아래 라인들을 `build.gradle` 파일에 추가하면 된다:

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.bangjunyoung:KoreanTextMatcher:v3.999'
}
```

`v:3.999` 부분을 사용하려는 KoreanTextMatcher의 버전으로 바꿔주면 된다.

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
