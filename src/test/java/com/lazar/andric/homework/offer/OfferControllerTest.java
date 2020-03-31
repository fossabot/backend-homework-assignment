package com.lazar.andric.homework.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazar.andric.homework.tender.Tender;
import com.lazar.andric.homework.tender.TenderRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(controllers = OfferController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class OfferControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private TenderRepository tenderRepository;

    private final List<OfferDto> offersForResponse = new ArrayList<>(Arrays.asList(
            OfferDto.builder().id(1L).amount(200).build(),
            OfferDto.builder().id(2L).amount(300).build())
    );
    private final Long ID = 1L;

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

        given(offerService.getAllOfferForTender(ID))
                .willReturn(offersForResponse);


        MockHttpServletResponse response = mockMvc.perform(
                get("/tenders/1/offers").contentType(MediaType.APPLICATION_JSON))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(offersForResponse));
    }

    @Test
    void testGetOfferForBidder() throws Exception {

        given(offerService.getAllOfferForBidder(ID))
                .willReturn(offersForResponse);

        MockHttpServletResponse response = mockMvc.perform(
                get("/bidders/1/offers").contentType(MediaType.APPLICATION_JSON))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(offersForResponse));
    }

    @Test
    void testGetAllBiddersOfferForSpecificTender() throws Exception {

        given(offerService.getAllBiddersOfferForSpecificTender(ID, ID))
                .willReturn(offersForResponse);

        MockHttpServletResponse response = mockMvc.perform(
                get("/bidders/1/tenders/1/offers").contentType(MediaType.APPLICATION_JSON))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(offersForResponse));
    }

    @Test
    void testSubmitNewOffer() throws Exception {
        OfferDto offerDtoForResponse = offersForResponse.get(0);
        OfferDto offerDtoForRequest = OfferDto.builder().amount(200).build();

        given(tenderRepository.findById(any())).willReturn(Optional.of(new Tender()));
        given(offerService.saveNewOffer(any(), anyLong(), anyLong()))
                .willReturn(offerDtoForResponse);

        MockHttpServletResponse response = mockMvc.perform(
                post("/bidders/1/tenders/1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoForRequest)))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(offerDtoForResponse));
    }

    @Test
    void testSubmitNewOfferReturn400WhenTenderClosed() throws Exception {
        OfferDto offerDtoForRequest = OfferDto.builder().amount(200).build();

        given(tenderRepository.findById(any())).willReturn(Optional.of(Tender.builder().closedForOffers(true).build()));

        MockHttpServletResponse response = mockMvc.perform(
                post("/bidders/1/tenders/1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoForRequest)))
                                                  .andReturn()
                                                  .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
