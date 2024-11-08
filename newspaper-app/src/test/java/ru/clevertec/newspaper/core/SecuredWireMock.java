package ru.clevertec.newspaper.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.clevertec.newspaper.core.secure.SecureDataTest;

@TestConfiguration
public abstract class SecuredWireMock {
    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().port(1000))
        .build();

    @BeforeEach
    public void configureWireMock() {
        wm.stubFor(WireMock.get("/api/users?username=root")
            .willReturn(WireMock.okJson(SecureDataTest.SECURED_USER_JSON)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));
    }
}
