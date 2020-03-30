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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(controllers = TenderController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class TenderControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private TenderService tenderService;

    private MockMvc mockMvc;

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
        final Long ID = 1L;
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
                post("/issuers/1/tenders/").contentType(MediaType.APPLICATION_JSON)
                                           .content(objectMapper.writeValueAsString(submittedTenderDto))
        )
                                                  .andDo(document("testCreateTenderSuccess",
                                                          requestFields(fieldWithPath("id").description("The id of tender"),
                                                                  fieldWithPath("description").description("The description of tender"))))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(returnedTenderDto));
    }

}
