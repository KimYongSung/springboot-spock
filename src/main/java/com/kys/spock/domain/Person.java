package com.kys.spock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
@NoArgsConstructor
@Getter
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String address;

    private Integer age;

    @Builder
    public Person(String name, String address, Integer age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }
}
