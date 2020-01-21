package com.kys.spock.repository

import com.kys.spock.domain.Person
import com.kys.spock.domain.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Narrative
import spock.lang.Specification

/**
 *
 * @author kody.kim
 * @since 20/01/2020
 */
@Narrative( value = """
    PersonRepository 테스트 
"""
)
@SpringBootTest
class PersonRepositorySpockTest extends Specification{

    @Autowired
    PersonRepository repository;

    def "이름으로 person 정보가 조회되야 한다."(){

        setup:
        repository.save(new Person("kody.kim", "서울시 강북구 수유동" , 32))
        repository.save(new Person("kody.kim1", "서울시 강북구 수유동1" , 31))
        repository.save(new Person("kody.kim2", "서울시 강북구 수유동2" , 32))

        when:
        Optional<Person> optionalPerson = repository.findByName("kody.kim")

        then:
        optionalPerson.isPresent()

        def person = optionalPerson.get()
        person.getName() == "kody.kim"
        person.getAddress() == "서울시 강북구 수유동"
        person.getAge() == 32;

        cleanup:
        repository.deleteAll()
    }

    def "id로 person 정보가 조회되야 한다."(){

        setup:
        def person1 = repository.save(new Person("kody.kim", "서울시 강북구 수유동", 32))
        repository.save(new Person("kody.kim1", "서울시 강북구 수유동1" , 31))
        repository.save(new Person("kody.kim2", "서울시 강북구 수유동2" , 32))

        when:
        Optional<Person> optionalPerson = repository.findById(person1.getId())

        then:
        optionalPerson.isPresent()

        def person = optionalPerson.get()
        person.getName() == "kody.kim"
        person.getAddress() == "서울시 강북구 수유동"
        person.getAge() == 32;

        cleanup:
        repository.deleteAll()
    }
}
