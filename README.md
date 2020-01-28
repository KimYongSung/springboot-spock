# spock 적용기

스프링과 spock 을 사용하여 테스트 환경을 구축해보자

현재 1.3이 최종버전이며, 2.0 버전은 마일스톤 상태로 1.3 기준으로 작성합니다.

spock 을 설명하기 앞서서 BDD에 대해서 간략하게 설명을 하겠습니다.

## 1. BDD는 어떤건가?

우선 BDD(Behaviour-Driven Development)와 TDD(Test-Driven Development)는 거의 차이가 없습니다.

차이가 있다면 BDD는 행위기반 테스트로 TDD와 다르게 ``given, when, then`` 구조를 가지는 것을 기본 패턴으로 권장합니다.

* Feature : 테스트에 대상의 기능/책임을 명시한다.
* Scenario : 테스트 목적에 대한 상황을 설명한다.
* Given : 시나리오 진행에 필요한 값을 설정한다.
* When : 시나리오를 진행하는데 필요한 조건을 명시한다.
* Then : 시나리오를 완료했을 때 보장해야 하는 결과를 명시한다.

위의 구조를 기반으로 아래와 같은 시나리오로 테스트 코드 작성이 가능합니다.

> 테스트 대상은 A 상태에서 출발하며(Given) 어떤 상태 변화를 가했을 때(When) 기대하는 상태로 완료되어야 한다. (Then) 또는 Side Effect가 전혀 없는 테스트 대상이라면 테스트 대상의 환경을 A 상태에 두고(Given) 어떤 행동을 요구했을 때(When) 기대하는 결과를 돌려받아야 한다. (Then)

도메인별 BDD 구조를 바탕으로 작성된 테스트 메소드는 그 자체로도 업무를 내포하는 훌륭한 인수인계 문서가 될 수 있습니다.

## 2. 왜 spock 을 사용하는가?

* Spock을 이용하여 테스트 코드를 작성하는 것은 다른 표준 테스트 프레임워크를 사용하는 것보다 시간이 덜 듭니다.(JUnit 과 Mock 프레임워크의 조합)
* Mocking, Stubbing 그리고 Spying 작업들을 매우 간단한 코드로 할 수 있는 쉬운 문법 덕분에 테스트 코드 로직을 변질시키지 않을 수 있습니다.
* Spock은 개발자들이 테스트를 BDD 같은 형식으로 구성할 수 있게 함으로써, 테스트를 더욱 명확하게 할 수 있습니다.
* Groovy를 사용하여 클로저와 맵을 직접 사용할 수 있어서  테스트의 명확성을 더 높일 수 있습니다.

아래와 같이 spock을 사용하여 테스트 메소드 작성시 blocks 단위로 구분하며, 각 블록별로 spock 자체에서 기능을 지원합니다.

#### Block 별로 구성된 테스트 코드

Spock에는 기능 방법의 각 개념 단계를 구현하기위한 지원 기능이 내장되어 있습니다. 이를 위한 피처 메소드는 소위 블로으로 구성되어 있으며, 블록은 레이블로 시작하며 다음 블록의 시작 또는 메소드의 끝으로 확장됩니다.

![](http://spockframework.org/spock/docs/1.3/images/Blocks2Phases.png)

아래와 같이 block 별로 테스트코드를 구성하여 가독성을 향상시킬 수 있습니다.

```groovy
def "person 이름으로 조회"(){
        
    given: "특정 name으로 조회 요청시 동일한 name 의 사용자 정보를 리턴하겠다."
    given(personRepository.findByName(name))
            .willReturn(Optional.of(new Person(name, address, age)));

    when: "특정 name으로 고객정보 조회"
    DataResponse<PersonDTO> response = personService.findByName(name);

    then: "조회된 고객정보 검증"
    Objects.nonNull(response)
    response.getData().getName() == _name
    response.getData().getAddress() == _address
    response.getData().getAge() == _age

    where: // where 만큼 given , when, then 블러의 반복
    name         | address           | age || _name       | _address           | _age
    "kody.kim"   | "서울시 강북구 수유동" | 32  || "kody.kim"  | "서울시 강북구 수유동"  | 32
    "kody.kim1"  | "서울시 강북구 수유동" | 32  || "kody.kim1" | "서울시 강북구 수유동"  | 32
    "kody.kim2"  | "서울시 강북구 수유동" | 32  || "kody.kim2" | "서울시 강북구 수유동"  | 32

}

```

#### 간결해진 코드

기존에는 junit, mockito, hamcrest, assertJ 등등 library 별로 각각의 기능 별로 코드를 작성해야 했습니다.

spock 을 활용할 경우 junit 을 활용할 때 보다 코드량이 줄어들고 간결하게 테스트 코드 작성이 가능합니다.

#### 직관적인 에러메시지

위의 테스트 메소드에서 where 을 변경하여 실패메시지를 출력해보겠습니다.

```groovy
where: // where 만큼 given , when, then 블러의 반복
name         | address           | age || _name       | _address           | _age
"kody.kim"   | "서울시 강북구 수유동" | 32  || "kody.kim"  | "서울시 강북구 수유동"  | 32
"kody.kim1"  | "서울시 강북구 수유동" | 32  || "kody.kim1" | "서울시 강북구 수유동"  | 32
"kody.kim2"  | "서울시 강북구 수유동" | 32  || "kody.kim" | "서울시 강북구 수유동"  | 32 // 테스트 결과 실패
```
```text
Condition not satisfied:

response.getData().getName() == _name
|        |         |         |  |
|        |         kody.kim2 |  kody.kim
|        |                   false
|        |                   1 difference (88% similarity)
|        |                   kody.kim(2)
|        |                   kody.kim(-)
|        <com.kys.spock.model.PersonDTO@54496c2d id=null name=kody.kim2 address=서울시 강북구 수유동 age=32>
DataResponse(super=com.kys.spock.common.result.DataResponse@6d6f6ca9, data=com.kys.spock.model.PersonDTO@54496c2d)

```

위와 같이 then block 작성된 코드를 기반으로 테스트의 검증로직을 처리하며, 검증이 실패할 경우 실패에 대한 상세 정보가 출력되어 메시지 만으로 파악이 가능합니다.

#### 예외테스트

```groovy
def "등록된 사용자가 아닌 경우"(){

    given: "특정 name으로 조회 요청시 null을 리턴하겠다."
    def name = "kody.kim";
    given(personRepository.findByName(eq(name)))
            .willReturn(Optional.empty());

    when: "특정 name으로 고객정보 조회"
    DataResponse<PersonDTO> response = personService.findByName(name);

    then: "조회된 고객정보 검증"
    def e = thrown(IllegalArgumentException.class)
    e.message == "사용자 정보가 없습니다."
}
```

thrown 을 사용하여 then block 에서 조금 더 직관적으로 예외처리 테스트 케이스 작성이 가능합니다. 

## 3. maven 설정

### 3.1 dependency 설정

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
```

### 3.2 plugin 설정

groovy로 컴파일하기 위한 plugin 설정이 필요합니다.

```xml
<!-- Mandatory plugins for using Spock -->
<plugin>
    <!-- The gmavenplus plugin is used to compile Groovy code. To learn more about this plugin,
    visit https://github.com/groovy/GMavenPlus/wiki -->
    <groupId>org.codehaus.gmavenplus</groupId>
    <artifactId>gmavenplus-plugin</artifactId>
    <version>1.6</version>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>compileTests</goal>
            </goals>
        </execution>
    </executions>
</plugin>
<!-- Optional plugins for using Spock -->
```

### 3.3 repository 설정

spock 을 관리하는 repository 를 설정해줘야 한다.

```xml
<repository>
    <id>spock-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

## 4. 사용법

아래 공식문서와 블로그 참조 

* http://spockframework.org/spock/docs/1.3/index.html
* https://ijbgo.tistory.com/12?category=638175
* https://jojoldu.tistory.com/229 

## 참고

* https://github.com/spockframework/spock
* https://www.baeldung.com/spring-spock-testing
* https://d2.naver.com/helloworld/568425
* http://woowabros.github.io/study/2018/03/01/spock-test.html
* http://woowabros.github.io/experience/2019/12/16/quickly-get-feedback-on-your-test-with-spock-extension-and-es-kibana.html