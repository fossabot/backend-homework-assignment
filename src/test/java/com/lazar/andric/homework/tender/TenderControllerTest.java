package com.lazar.andric.homework.tender;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(controllers = TenderController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class TenderControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private final Long ID = 1L;

    @MockBean
    private TenderService tenderService;


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
    void testCreateTenderSuccess() throws Exception {

        TenderDto submittedTenderDto = TenderDto.builder()
                                                .description("description")
                                                .build();
        TenderDto returnedTenderDto = TenderDto.builder()
                                               .id(ID)
                                               .description("description")
                                               .build();

        given(tenderService.createTenderForIssuer(ID, submittedTenderDto))
                .willReturn(returnedTenderDto);

        MockHttpServletResponse response = mockMvc.perform(
                post("/issuers/{issuerId}/tenders/", ID).contentType(MediaType.APPLICATION_JSON)
                                           .content(objectMapper.writeValueAsString(submittedTenderDto)))
                                                  .andDo(document("{methodName}", pathParameters(
                                                          parameterWithName("issuerId").description("The id of issuer")
                                                  )))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(returnedTenderDto));
    }

    @Test
    void getAllTendersForIssuers() throws Exception {

        List<TenderDto> tendersForResponse = new ArrayList<>();
        tendersForResponse.add(TenderDto.builder().id(1L).description("description 1").build());
        tendersForResponse.add(TenderDto.builder().id(2L).description("description 2").build());

        given(tenderService.getAllTendersForIssuers(ID))
                .willReturn(tendersForResponse);


        MockHttpServletResponse response = mockMvc.perform(
                get("/issuers/{issuerId}/tenders/", ID).contentType(MediaType.APPLICATION_JSON))
                                                  .andDo(document("{methodName}", pathParameters(
                                                          parameterWithName("issuerId").description("The id of issuer")
                                                  )))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tendersForResponse));
    }

}
