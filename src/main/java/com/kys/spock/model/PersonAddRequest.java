package com.kys.spock.model;

import com.kys.spock.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
@Builder
@AllArgsConstructor
@Data
public class PersonAddRequest {

    @NotEmpty(message = "name 은 필수 입니다.")
    private String name;

    @NotEmpty(message = "address 는 필수 입니다.")
    private String address;

    private Integer age;

    @AssertTrue(message = "age 는 필수 값 입니다.")
    public boolean isValidAge(){
        return Objects.nonNull(age) || age > 0;
    }

    public Person toEntity(){
        return Person.builder()
                     .address(this.address)
                     .age(this.age)
                     .name(this.name)
                     .build();
    }
}
