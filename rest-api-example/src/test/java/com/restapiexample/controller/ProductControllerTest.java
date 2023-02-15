package com.restapiexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void checkStock() throws Exception {

        long productId = 2L;

        mockMvc.perform(
                        get("/api/example/v1/products")
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        /**
         * perform - 요청을 전송하는 역할
         * andExpect - 응답을 검증
         * contentType - http 요청과 응답 데이터의 형식
         * accept - 브라우저에서 서버로 요청시 메세지에 담기는 헤더(설정한 데이터 형식으로 응답 해달라고 하는것)
         * andDo - 실행 결과를 처리
         * andExpect - 실행 결과를 검증
         *   ㄴ status - HTTP 상태 코드 검증
         *   ㄴ header - 응답 헤더의 상태 검증
         *   ㄴ cookie - 쿠키 상태 검증
         *   ㄴ content - 응답 본문 내용 검증
         *   ㄴ view - 컨트롤러가 반환한 뷰 이름 검증
         *   ㄴ forwardedUrl - 이동 대상의 경로를 검증
         *   ㄴ redirectedUrl - 리다이렉트 대상의 경로를 검증
         *   ㄴ model - spring mvc model 상태 검증
         *   ㄴ flash - 플래시 스코프의 상태 검증
         *   ㄴ request - 비동기 처리의 상태나 요청 스코프의 상태, 세션 스코프 상태 검증
         *   ㄴ jsonPath - json 응답 결과 검증
         * andReturn - 실행 결과를 반환
         */

    }

}
