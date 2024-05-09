package com.stdnullptr.emailgenerator.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.http.client.HttpClient;

@TestConfiguration
public class WebTestClientConfig {

	@Bean
	public WebTestClient buildWebTestClientWithSSL() throws Exception {
		SslContext sslContext = SslContextBuilder.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE) // Trust all certificates
				.build();

		HttpClient httpClient = HttpClient.create()
				.secure(spec -> spec.sslContext(sslContext));

		return WebTestClient.bindToServer(new ReactorClientHttpConnector(httpClient))
				.baseUrl("https://localhost:8443")
				.build();
	}
}
