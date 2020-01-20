package com.kys.spock.service

import com.kys.spock.common.result.DataResponse
import com.kys.spock.domain.Person
import com.kys.spock.domain.repository.PersonRepository
import com.kys.spock.model.PersonDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification

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
        given:
        String name = "kody.kim";
        given(personRepository.findByName(name))
                .willReturn(Optional.of(new Person("kody.kim","서울시 강북구 수유동", 32)));

        when:
        DataResponse<PersonDTO> response = personService.findByName(name);

        then:
        Objects.nonNull(response)
        response.getData().getName() == "kody.kim"
        response.getData().getAddress() == "서울시 강북구 수유동"
        response.getData().getAge() == 32
    }
}
