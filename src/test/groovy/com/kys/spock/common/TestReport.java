package com.kys.spock.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 테스트 결과를 저장할 클래스
 * @author kody.kim
 * @since 21/01/2020
 */
@Getter
@Builder
@AllArgsConstructor
@ToString
public class TestReport {

    private String project;

    private String version;

    private String pkg;

    private String testClass;

    private String description;

    private TestStatus status;

    private Throwable throwable;

    private LocalDateTime executeTime;

}
