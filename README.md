# KoreanTextMatcher 4.0

[![Java CI](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml/badge.svg)](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml)
[![codecov](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher)
[![JitPack](https://jitpack.io/v/bangjunyoung/KoreanTextMatcher.svg)](https://jitpack.io/#bangjunyoung/KoreanTextMatcher)

KoreanTextMatcher는 문자열 내에서 특정 한글 패턴을 찾아내는 자바 라이브러리로, 이를 이용하면 상용 수준의 강력한 한글 검색 기능을 앱 내에 손쉽게 구현할 수 있다.
주요 기능과 특징은 다음과 같다:

- 기본 ASCII 문자는 물론 한글 초성 매칭에서 한글 음절 근사 매칭까지 하나의 API로 지원
- 두벌식 한글 키보드에서 발생하는 도깨비불 현상도 감지하고 처리 가능
- 하나의 텍스트 내에서 여러 개의 패턴 매칭 가능
- 정규식 앵커 `^`와 `$`를 이용하여 패턴의 위치를 대상 텍스트의 시작과 끝으로 한정할 수 있음
- 유니코드 한글 자모 (U+1100 ~)와 한글 호환 자모 (U+3131 ~) 모두 지원
- 매우 간단하고 사용하기 쉬운 API
- 초성/중성/종성/자모/음절을 식별하고 가공하는 다양한 종류의 API 제공
- 메모리 할당을 줄여 가비지 컬렉션을 최소화하도록 세심하게 작성된 코드
- 100% 스레드 안전성: 멀티 스레드 프로그램에서도 별도 처리없이 사용 가능
- 300개 이상의 유닛 테스트 케이스, 99% 이상의 코드 커버리지 등을 통해 검증된 품질
- 오픈 소스: 수정, 배포, 2차 저작 등 어떤 프로그램에도 자유롭게 사용 가능한 BSD 라이선스.
  상용 프로그램에도 물론 아무 제약없이 사용할 수 있다(저작권 문구는 절대 지우지 마세요!).
- JDK 17에서 개발/테스트되었고 안드로이드 프로젝트에도 수정없이 사용 가능

## 4.0에서 달라진 점

- 단순한 초성 매칭이 정교한 음절 근사 매칭으로 대체되었다.
  예를 들어 3.0에서는 '빨래'라는 텍스트에 '빨래'라는 완전 일치어 또는 'ㅃㄹ'이라는 초성 패턴만이 매칭되었지만, 4.0에서는 '빨래', '빠래', 'ㅃ래', 'ㅃㄹ', 'ㅂㄹ' 등 음절 앞부분의 일부 자소만으로도 매칭이 가능해졌다.
- 음절 근사 매칭의 특수한 형태인 도깨비불 매칭도 지원한다.
  도깨비불 현상은 두벌식 키보드에서 한글을 입력할 때 불가피하게 생기는 현상으로, 예를 들어 "하늘"이라는 단어를 입력할 때 "ㅎ" -> "하" -> "한" -> "하느" -> "하늘"처럼 뒤음절의 초성 "ㄴ"이 앞음절의 종성 자리에 먼저 달라붙었다가 뒤음절의 중성 "ㅡ"가 입력된 다음에야 제자리로 옮겨가는 것을 말한다.
- 초성/중성/종성/자모/음절을 식별하고 가공하는 다양한 종류의 API가 추가되었다.
- 메모리 할당을 줄임으로써 대량의 데이터 처리시 매칭 성능이 대폭 향상되었다.
- 자바와 안드로이드간 바이너리 호환성 문제 때문에 JDK 17을 타겟으로 하도록 변경.

## 빌드

빌드에 필요한 도구:

- JDK 17 이상
- Gradle 8.9 이상
- JUnit 6.0 이상

필요한 도구들이 모두 설치되었다면 빌드는 매우 쉽다. 최상위 소스 폴더에서 아래 명령을 실행하면 된다:

```shell
./gradlew build
```

빌드가 성공하면 `build/libs` 폴더 밑에 `KoreanTextMatcher-X.xx.jar` 파일이 생성되는데, 이것을 여러분의 프로젝트로 임포트해서 쓰면 된다.

유닛 테스트를 실행하려면:

```shell
./gradlew test
```

Javadoc을 생성하려면:

```shell
./gradlew javadoc
```

## 미리 빌드된 바이너리 이용

소스 코드를 직접 빌드하는 대신 미리 빌드된 바이너리를 [JitPack](https://jitpack.io/#bangjunyoung/KoreanTextMatcher)을 이용해 여러분의 프로젝트로 임포트하는 방법도 있다.
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

`v:3.999` 부분을 사용하려는 버전으로 바꿔준다.

## 프로그래밍 예시

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

## API 레퍼런스

자세한 API 용법은 [Javadoc 페이지](http://bangjunyoung.github.io/KoreanTextMatcher/) 참조.

## 연락처

KoreanTextMatcher를 이용한 앱을 알리고 싶거나 개선해야할 점을 제안할 분들은 [저자의 이메일](bang.junyoung@gmail.com)로 연락주시기 바란다.
