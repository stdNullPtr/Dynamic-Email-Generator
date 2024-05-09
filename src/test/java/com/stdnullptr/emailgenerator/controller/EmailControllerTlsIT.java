package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.config.WebTestClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(WebTestClientConfig.class)
@ActiveProfiles("test")
public class EmailControllerTlsIT {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void generateEmails_missingQueryParams_shouldReturnBadRequest() {
		webTestClient.get()
				.uri("/app/v1/email/generate")
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$.message")
				.isEqualTo("Invalid request argument(s): {queryParams=[Query parameters cannot be empty]}");
	}

	@Test
	void generateEmails_success_shouldReturnOk() throws IOException {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("str1", "Ivan");
		queryParams.add("str1", "Nikola");
		queryParams.add("str1", "Rado");
		queryParams.add("str2", "gmail");
		queryParams.add("str2", "yahoo");
		queryParams.add("str3", "com");
		queryParams.add("str3", "bg");
		queryParams.add("expression",
				"first(str1,2) ; raw(.) ; lit(str1) ; raw(@) ; first(str2, 4) ; raw(.) ; lit(str3)");

		String expectedResponse = Files.readString(
				ResourceUtils.getFile("classpath:response/testGenerateEmails_success_shouldReturnOk.json").toPath());

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/app/v1/email/generate")
						.queryParams(queryParams)
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.json(expectedResponse);
	}

}
