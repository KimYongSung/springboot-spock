package com.kys.spock.service

import com.kys.spock.common.result.DataResponse
import com.kys.spock.domain.Person
import com.kys.spock.domain.repository.PersonRepository
import com.kys.spock.model.PersonDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.eq
import static org.mockito.BDDMockito.given

/**
 *
 * @author kody.kim
 * @since 20/01/2020
 */
@SpringBootTest
class PersonServiceSpockTest extends Specification {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    def "person 이름으로 조회"(){
        
        given: "특정 name으로 조회 요청시 동일한 name 의 사용자 정보를 리턴하겠다."
        given(personRepository.findByName(eq(name)))
                .willReturn(Optional.of(new Person(name, address, age)));

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
        given(personRepository.findByName(eq(name)))
                .willReturn(Optional.empty());

        when: "특정 name으로 고객정보 조회"
        DataResponse<PersonDTO> response = personService.findByName(name);

        then: "조회된 고객정보 검증"
        def e = thrown(IllegalArgumentException.class)
        e.message == "사용자 정보가 없습니다."
    }
}
