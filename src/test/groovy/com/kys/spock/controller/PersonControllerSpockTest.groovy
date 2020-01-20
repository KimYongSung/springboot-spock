package com.kys.spock.controller

import com.kys.spock.common.constants.ErrorCode
import com.kys.spock.common.result.DataResponse
import com.kys.spock.model.PersonDTO
import com.kys.spock.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 *
 * @author kody.kim
 * @since 20/01/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerSpockTest extends Specification {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PersonService personService;

    private MockMvc mockMvc;

    def setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    def "사용자 등록 요청"(){

        given:
        given(personService.add(any())).willReturn(
                DataResponse.ok(PersonDTO.builder()
                            .name("kody.kim")
                            .age(32)
                            .address("서울시 강북구 수유동")
                            .id(1l)
                            .build()
                )
        )

        expect:
        mockMvc.perform(post("/person")
                .param("name", "kody.kim")
                .param("address", "서울시 강북구 수유동")
                .param("age", "32")
        ).andExpect(status().isOk())
        .andExpect(jsonPath('$.code').value(ErrorCode.CD_0000.getCode()))
        .andExpect(jsonPath('$.message').value(ErrorCode.CD_0000.getMessage()))
        .andExpect(jsonPath('$.data.id').isNumber())

    }
}
