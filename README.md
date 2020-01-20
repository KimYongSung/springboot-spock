# spock 적용기

스프링과 spock 을 사용하여 테스트 환경을 구축해보자

현재 1.3이 최종버전이며, 2.0 버전은 마일스톤 상태로 1.3 기준으로 작성합니다.

* https://github.com/spockframework/spock
* https://www.baeldung.com/spring-spock-testing
* https://d2.naver.com/helloworld/568425
http://woowabros.github.io/study/2018/03/01/spock-test.html
* http://woowabros.github.io/experience/2019/12/16/quickly-get-feedback-on-your-test-with-spock-extension-and-es-kibana.html

## 1. 왜 spock 을 사용하는가?

spock 을 사용하기에 앞서 BDD에 대해서 간단하게 알아보겠습니다.

BDD(Behaviour-Driven Development)와 TDD(Test-Driven Development)는 거의 차이가 없습니다.

차이가 있다면 TDD 는 조금 더 ``단위 테스트 자체에 집중``하여 개발하는 반면, BDD는 ``비지니스 요구사항에 집중하여 테스트 케이스``를 개발한다는 것 입니다.

특히 일반적인 웹서비스 환경에서 도메인별로 메소드의 기능이 아닌 시나리오에 입각한 테스트 케이스 작성이 가능합니다.

물론 junit에도 BDD 스타일에 테스트 케이스 작성이 가능하지만, spock 은 프레임워크 자체에서 지원이 junit 보다 더 강력하고 소스코드가 직관적입니다.

## 2. maven 설

spring 에서 spock을 사용하기 위해서는 maven 기준으로 아래 의존성이 필요합니다.

```xml
<!-- Mandatory dependencies for using Spock -->
<dependency>
    <groupId>org.spockframework</groupId>
    <artifactId>spock-core</artifactId>
    <version>1.3-groovy-2.5</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.spockframework</groupId>
    <artifactId>spock-spring</artifactId>
    <version>1.3-groovy-2.5</version>
    <scope>test</scope>
</dependency>

<!-- Optional dependencies for using Spock -->
<dependency> <!-- use a specific Groovy version rather than the one specified by spock-core -->
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.5.7</version>
    <type>pom</type>
</dependency>
<dependency> <!-- enables mocking of classes (in addition to interfaces) -->
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy</artifactId>
    <version>1.9.7</version>
</dependency>
<dependency> <!-- enables mocking of classes without default constructor (together with CGLIB) -->
    <groupId>org.objenesis</groupId>
    <artifactId>objenesis</artifactId>
    <version>2.6</version>
    <scope>test</scope>
</dependency>
```

## plugin 설정