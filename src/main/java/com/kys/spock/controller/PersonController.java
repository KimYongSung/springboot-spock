package com.kys.spock.controller;

import com.kys.spock.common.result.DataResponse;
import com.kys.spock.model.PersonAddRequest;
import com.kys.spock.model.PersonDTO;
import com.kys.spock.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    @PostMapping
    public ResponseEntity<DataResponse<Long>> addPerson(@Valid PersonAddRequest request){

        DataResponse<Long> response = service.add(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<DataResponse<PersonDTO>> findPersonByName(@PathVariable String name){

        DataResponse<PersonDTO> response = service.findByName(name);

        return ResponseEntity.ok(response);
    }
}
