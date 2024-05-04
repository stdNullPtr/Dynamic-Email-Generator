package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.exception.GlobalExceptionHandler;
import com.stdnullptr.emailgenerator.exception.InvalidArgumentException;
import com.stdnullptr.emailgenerator.service.EmailGeneratorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ContextConfiguration(classes = {EmailController.class, EmailGeneratorService.class, GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class EmailControllerTest {

    private MockMvc controllerMockMvc;

    private AutoCloseable mocksCloseable;

    @Autowired
    private EmailController emailController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        mocksCloseable = MockitoAnnotations.openMocks(this);
        controllerMockMvc = MockMvcBuilders.standaloneSetup(emailController).setControllerAdvice(globalExceptionHandler).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocksCloseable.close();
    }

    /**
     * Method being tested: {@link EmailController#generateEmails(MultiValueMap)}
     */
    @Test
    void testGenerateEmails_missingQueryParams_shouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/app/v1/email/generate");

        ResultActions actualPerformResult = controllerMockMvc.perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid request argument(s): {queryParams=[Query parameters must contain at least 1 input parameter and 1 'expression' parameter]}"));
    }

    /**
     * Method being tested: {@link EmailController#generateEmails(MultiValueMap)}
     */
    @Test
    void testGenerateEmails_missingExpressionQueryParam_shouldReturnBadRequest() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("str1", "Millennium");
        queryParams.add("str1", "falcon");
        queryParams.add("str2", "lol");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/app/v1/email/generate")
                .queryParams(queryParams);

        ResultActions actualPerformResult = controllerMockMvc.perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> assertInstanceOf(InvalidArgumentException.class, result.getResolvedException()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The 'expression' query parameter is required."));
    }

    /**
     * Method being tested: {@link EmailController#generateEmails(MultiValueMap)}<p>
     * Logically you can't have less than 2 query parameters: at least one input and exactly one expression.
     */
    @ParameterizedTest
    @MethodSource("com.stdnullptr.emailgenerator.util.TestUtils$TestParamsProvider#tooFewQueryParamsProvider")
    void testGenerateEmails_tooFewQueryParams_shouldReturnBadRequest(MultiValueMap<String, String> queryParams) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/app/v1/email/generate")
                .queryParams(queryParams);

        ResultActions actualPerformResult = controllerMockMvc.perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> assertInstanceOf(HandlerMethodValidationException.class, result.getResolvedException()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid request argument(s): {queryParams=[Query parameters must contain at least 1 input parameter and 1 'expression' parameter]}"));
    }

    /**
     * Method being tested: {@link EmailController#generateEmails(MultiValueMap)}
     */
    @Test
    void testGenerateEmails_invalidInputNameQueryParam_shouldReturnBadRequest() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("str1", "Millennium");
        queryParams.add("expression", "anyExpr");
        queryParams.add("invalid2", "lol");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/app/v1/email/generate")
                .queryParams(queryParams);

        ResultActions actualPerformResult = controllerMockMvc.perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> assertInstanceOf(InvalidArgumentException.class, result.getResolvedException()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The only allowed input prefix is 'strN'."));
    }
}
