package com.kys.spock.controller

import com.kys.spock.common.constants.ErrorCode
import com.kys.spock.common.result.DataResponse
import com.kys.spock.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
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

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    def "사용자 등록 요청"(){

        given:
        given(personService.add(any()))
                .willReturn(DataResponse.ok(1l))

        def param = post("/person")
                .param("name", "kody.kim")
                .param("address", "서울시 강북구 수유동")
                .param("age", "32")

        when:
        def resultAction = mockMvc.perform(param)

        then:
        resultAction.andExpect(status().isOk())
        resultAction.andExpect(jsonPath('$.code').value(ErrorCode.CD_0000.getCode()))
        resultAction.andExpect(jsonPath('$.message').value(ErrorCode.CD_0000.getMessage()))
        resultAction.andExpect(jsonPath('$.data').isNumber())
    }

    def "이미 등록된 사용자 요청"(){

        given:
        given(personService.add(any()))
                .willThrow(new IllegalArgumentException("이미 등록된 사람입니다."))

        def param = post("/person")
                .param("name", "kody.kim")
                .param("address", "서울시 강북구 수유동")
                .param("age", "32")

        when:
        def resultAction = mockMvc.perform(param)

        then:
        resultAction.andExpect(status().is5xxServerError())
        resultAction.andExpect(jsonPath('$.code').value(ErrorCode.CD_S999.getCode()))
        resultAction.andExpect(jsonPath('$.message').value(ErrorCode.CD_S999.getMessage()))
        resultAction.andExpect(jsonPath('$.data').doesNotHaveJsonPath())
    }
}
