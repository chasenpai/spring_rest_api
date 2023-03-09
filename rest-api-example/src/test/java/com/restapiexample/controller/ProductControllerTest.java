package com.restapiexample.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiexample.entity.Product;
import com.restapiexample.repository.ProductRepository;
import com.restapiexample.request.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("제품 단건 조회")
    public void getProduct() throws Exception {

        Product product = Product.builder()
                .name("테스트제품")
                .price(1000)
                .stock(10)
                .build();

        Product savedProduct = productRepository.save(product);

        mockMvc.perform(//RestDocumentationRequestBuilders - @PathVariable 사용 시
                        RestDocumentationRequestBuilders.get("/api/example/v1/products/{id}", savedProduct.getId())
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(document("get-product",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("제품 id")
                                ),
                                relaxedResponseFields( //모든 필드를 문서화 하고 싶지 않으면 사용
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("제품 id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("제품명"),
                                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("stock").type(JsonFieldType.NUMBER).description("재고"),
                                        fieldWithPath("createDate").type(JsonFieldType.STRING).description("제품 등록일"),
                                        fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("제품 수정일")
                                ),
                                links( //문서화 되지 않은 링크가 있으면 테스트 실패
                                        halLinks(),
                                        linkWithRel("self").description("제품 정보"),
                                        linkWithRel("list").description("제품 목록")
                                )
                        )
                );

    }

    /**
     * 스니펫 재사용
     */
    private final ResponseFieldsSnippet responseField = relaxedResponseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("제품 id"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("제품명"),
            fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
            fieldWithPath("stock").type(JsonFieldType.NUMBER).description("재고"),
            fieldWithPath("createDate").type(JsonFieldType.STRING).description("제품 등록일"),
            fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("제품 수정일")
    );

    private final RequestFieldsSnippet requestField = relaxedRequestFields(
            fieldWithPath("name").type(JsonFieldType.STRING).description("제품명"),
            fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
            fieldWithPath("stock").type(JsonFieldType.NUMBER).description("재고")
    );

    private final LinksSnippet links = links(
            halLinks(),
            linkWithRel("self").description("제품 정보"),
            linkWithRel("list").description("제품 목록"),
            linkWithRel("delete").description("제품 삭제")
    );

    @Test
    @DisplayName("제품 목록 조회")
    public void getAllProducts() throws Exception {

        mockMvc.perform(
                        get("/api/example/v1/products")
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(document("get-all-products",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                relaxedResponseFields(
                                        //배열 문서화
                                        fieldWithPath("_embedded.productDtoList[].id").type(JsonFieldType.NUMBER).description("제품 id"),
                                        fieldWithPath("_embedded.productDtoList[].name").type(JsonFieldType.STRING).description("제품명"),
                                        fieldWithPath("_embedded.productDtoList[].price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("_embedded.productDtoList[].stock").type(JsonFieldType.NUMBER).description("재고")
                                )

                        )
                );

    }

    @Test
    @DisplayName("제품 등록")
    public void saveProduct() throws Exception {

        ProductRequest request = ProductRequest.builder()
                .name("테스트제품")
                .price(1000)
                .stock(10)
                .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/api/example/v1/products")
                                .content(content)
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andDo(document("save-product",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestField,
                                responseField,
                                links
                        )
                );

    }

    @Test
    @DisplayName("제품 수정")
    public void updateProduct() throws Exception {

        ProductRequest request = ProductRequest.builder()
                .id(2005)
                .name("테스트제품수정")
                .price(2000)
                .stock(10)
                .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        put("/api/example/v1/products")
                                .content(content)
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-product",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestField.and(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("제품 id")
                                ),
                                responseField,
                                links
                        )
                );

    }

    @Test
    @DisplayName("제품 삭제")
    public void deleteProduct() throws Exception {

        Product product = Product.builder()
                .name("테스트제품")
                .price(1000)
                .stock(10)
                .build();

        Product savedProduct = productRepository.save(product);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/example/v1/products/{id}", savedProduct.getId())
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .accept(MediaTypes.HAL_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(document("delete-product",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("제품 id")
                                ),
                                responseField,
                                links(
                                        halLinks(),
                                        linkWithRel("list").description("제품 목록")
                                )
                        )
                );

    }


}
