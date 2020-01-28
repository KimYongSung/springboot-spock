package com.kys.spock.service

import com.kys.spock.common.result.DataResponse
import com.kys.spock.domain.Person
import com.kys.spock.domain.repository.PersonRepository
import com.kys.spock.model.PersonDTO
import org.spockframework.spring.SpringBean
import org.spockframework.spring.SpringSpy
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Narrative
import spock.lang.Specification

/**
 *
 * @author kody.kim
 * @since 20/01/2020
 */
@Narrative( value = """
    PersonService 테스트 
"""
)
@SpringBootTest
class PersonServiceSpockTest extends Specification {

    @SpringSpy
    private PersonService personService;

    @SpringBean
    private PersonRepository personRepository = Stub();

    def "person 이름으로 조회"(){
        
        given: "특정 name으로 조회 요청시 동일한 name 의 사용자 정보를 리턴하겠다."
        personRepository.findByName(name) >> Optional.of(new Person(name, address, age))

        when: "특정 name으로 고객정보 조회"
        DataResponse<PersonDTO> response = personService.findByName(name);

        then: "조회된 고객정보 검증"
        Objects.nonNull(response)
        response.getData().getName() == _name
        response.getData().getAddress() == _address
        response.getData().getAge() == _age

        where:
        name         | address           | age || _name       | _address           | _age
        "kody.kim"   | "서울시 강북구 수유동" | 32  || "kody.kim"  | "서울시 강북구 수유동"  | 32
        "kody.kim1"  | "서울시 강북구 수유동" | 32  || "kody.kim1" | "서울시 강북구 수유동"  | 32
        "kody.kim2"  | "서울시 강북구 수유동" | 32  || "kody.kim2" | "서울시 강북구 수유동"  | 32
    }

    def "등록된 사용자가 아닌 경우"(){

        given: "특정 name으로 조회 요청시 null을 리턴하겠다."
        def name = "kody.kim";
        personRepository.findByName(name) >> Optional.empty()

        when: "특정 name으로 고객정보 조회"
        personService.findByName(name);

        then: "조회된 고객정보 검증"
        def e = thrown(IllegalArgumentException.class)
        e.message == "사용자 정보가 없습니다."
    }
}
