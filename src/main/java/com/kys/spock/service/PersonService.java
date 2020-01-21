package com.kys.spock.service;

import com.kys.spock.common.result.DataResponse;
import com.kys.spock.domain.Person;
import com.kys.spock.domain.repository.PersonRepository;
import com.kys.spock.model.PersonAddRequest;
import com.kys.spock.model.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository repository;

    /**
     * 추가
     * @param request
     * @return
     */
    public DataResponse<Long> add(PersonAddRequest request){

        Optional<Person> optionalPerson = repository.findByName(request.getName());

        if(optionalPerson.isPresent()){
            throw new IllegalArgumentException("이미 등록된 사람입니다.");
        }

        Person person = repository.save(request.toEntity());

        return DataResponse.ok(person.getId());
    }

    /**
     * 이름으로 조회
     * @param name
     * @return
     */
    public DataResponse<PersonDTO> findByName(String name){
        return repository.findByName(name)
                         .map(PersonDTO::of)
                         .map(DataResponse::ok)
                         .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));
    }
}
