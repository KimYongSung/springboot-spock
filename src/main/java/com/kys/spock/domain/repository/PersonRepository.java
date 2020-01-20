package com.kys.spock.domain.repository;

import com.kys.spock.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * 사용자 이름으로 조회
     * @param name
     * @return
     */
    Optional<Person> findByName(String name);
}
