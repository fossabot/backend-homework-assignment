package com.lazar.andric.homework.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazar.andric.homework.tender.TenderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(controllers = OfferController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class OfferControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .apply(documentationConfiguration(restDocumentation))
                                      .alwaysDo(document("{methodName}",
                                              preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                                      .build();
    }

    @Test
    void testGetAllOfferForTender() throws Exception {
        List<OfferDto> offersForResponse = new ArrayList<>();
        offersForResponse.add(OfferDto.builder().id(1L).amount(200).build());
        offersForResponse.add(OfferDto.builder().id(2L).amount(300).build());

        given(offerService.getAllOfferForTender(1L))
                .willReturn(offersForResponse);


        MockHttpServletResponse response = mockMvc.perform(
                get("/tenders/1/offers").contentType(MediaType.APPLICATION_JSON))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(offersForResponse));
    }
}
