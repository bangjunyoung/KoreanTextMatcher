# KoreanTextMatcher 변경 사항

## 4.0 (2026)

- 초성 검색을 대체하는 한글 음절 근사 매칭 구현.
- 두벌식 한글 입력시 발생하는 도깨비불 현상을 처리하는 기능 구현(`MatchingOptions.DubeolsikInput`)
- 대소문자를 구별하지 않는 영문 매칭 구현(`MatchingOptions.IgnoreCase`)
- 텍스트내 공백을 건너뛰고 매칭하는 기능 구현(`MatchingOptions.IgnoreWhitespace`)
- `KoreanCharApproxMatcher`를 메모리 할당 최소화한 고성능 버전으로 새롭게 구현.
- `KoreanChar` 타입에 새로 추가된 API:
  - 음절 조합: `compose`
  - 음절 분해: `decompose`, `decomposeCompat`
  - 음절에서 중성/종성 구하기: `getCompatJungseong`, `getCompatJongseong`, `getJungseong`, `getJongseong`
  - 중성/종성인지 판별하기: `isCompatJungseong`, `isCompatJongseong`, `isJungseong`, `isJongseong`
  - 한글 자모와 한글 호환 자모간 변환: `convertCompatToChoseong`, `convertChoseongToCompat`, `convertJungseongToCompat`, `convertCompatToJungseong`, `convertCompatToJongseong`, `convertJongseongToCompat`
- ```[BREAKING CHANGE]```: `KoreanCharApproxMatcher.match`를 `isMatch`로 이름 바꿈
- ```[BREAKING CHANGE]```: `KoreanChar.decomposeIntoCompatJamo`를 삭제하고 `decomposeCompat`으로 대체.
- [JDK](https://developers.redhat.com/products/openjdk) 버전을 17로 상향.
- [Hamcrest](https://hamcrest.org/) 기반 테스트를 [AssertJ](https://github.com/assertj/assertj-core)로 이전.
- [Junit](https://junit.org/) 버전을 6으로 상향.
- [Gradle](https://gradle.org) 버전을 8.9로 상향.

## 3.0 (2019)

- ```[BREAKING CHANGE]```: `KoreanChar.xxxCompatibilityChoseong`을 `xxxCompatChoseong`으로 이름 바꿈.
- ```[BREAKING CHANGE]```: `KoreanTextMatcher.matches`의 리턴 타입을 `List<KoreanTextMatch>`에서 `Iterable<KoreanTextMatch>`로 변경.
- [Gradle](https://gradle.org) 기반 빌드 지원.

## 2.0 (2014)

- 최초의 공개 버전
