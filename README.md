# KoreanTextMatcher

[![Java CI](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml/badge.svg)](https://github.com/bangjunyoung/KoreanTextMatcher/actions/workflows/java.yml)
[![codecov](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/bangjunyoung/KoreanTextMatcher)
[![JitPack](https://jitpack.io/v/bangjunyoung/KoreanTextMatcher.svg)](https://jitpack.io/#bangjunyoung/KoreanTextMatcher)

KoreanTextMatcher는 문자열 내에서 특정 한글 패턴을 찾아내는 자바 라이브러리로, 이를 이용하면 상용 수준의 강력한 한글 검색 기능을 앱 내에 손쉽게 구현할 수 있다.
주요 기능과 특징은 다음과 같다:

- ```[v4.0]``` 기본 ASCII 문자는 물론 한글 초성에서 한글 음절 근사 매칭까지 하나의 API로 지원.
- ```[v4.0]``` 두벌식 한글 키보드에서 발생하는 도깨비불 현상을 감지하고 매칭 가능.
- ```[v4.0]``` 영문 대소문자를 무시하고 매칭 가능.
- 하나의 텍스트 내에 여러 개 존재하는 패턴을 모두 찾아낼 수 있다.
- 정규식 앵커 `^`와 `$`를 이용하여 패턴의 위치를 대상 텍스트의 시작과 끝으로 한정할 수 있다.
- 한글 자모 (U+1100 ~)와 한글 호환 자모 (U+3131 ~)를 모두 지원하고 두 코드간 변환 API도 제공.
- 매우 간단하고 사용하기 쉬운 API.
- ```[v4.0]``` 초성/중성/종성/자모/음절을 식별하고 가공하는 다양한 종류의 API 제공.
- ```[v4.0]``` 메모리 할당 최적화로 가비지 컬렉션을 최소화해 대량의 데이터 처리시에도 빠르고 안정적.
- 완전한 스레드 안전성: 멀티 스레드 프로그램에서도 별도 처리없이 그대로 사용 가능.
- 300개 이상의 유닛 테스트 케이스, 99% 이상의 코드 커버리지 등을 통해 검증된 품질.
- 오픈 소스: 수정, 배포, 2차 저작 등 어떤 프로그램에도 자유롭게 사용 가능한 BSD 라이선스.
  상용 프로그램에도 물론 아무 제약없이 사용할 수 있다(저작권 문구는 절대 지우지 마세요!).
- JDK 17에서 개발/테스트되었고 안드로이드 프로젝트에도 수정없이 사용 가능하다.

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

유닛 테스트는 다음 명령으로 실행한다:

```shell
./gradlew test
```

Javadoc을 생성해서 API 레퍼런스 매뉴얼을 보려면:

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

KoreanTextMatcher를 이용한 앱을 알리고 싶거나 개선해야할 점을 발견한 분들은 <bang.junyoung@gmail.com>로 연락주시기 바란다.
