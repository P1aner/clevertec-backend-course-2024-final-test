package ru.clevertec.newspaper.core.secure;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class SecuredWireMock {

    public static WireMockExtension wm = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().port(1000))
        .build();

    public static void configureWireMock() {
        wm.stubFor(WireMock.get("/api/users?username=root")
            .willReturn(WireMock.okJson(SecureData.SECURED_USER_JSON)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));
    }
}
