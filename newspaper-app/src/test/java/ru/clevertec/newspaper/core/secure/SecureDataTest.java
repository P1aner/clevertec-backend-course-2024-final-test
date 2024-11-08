package ru.clevertec.newspaper.core.secure;

public class SecureDataTest {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic cm9vdDpyb290";
    public final static String SECURED_USER_JSON = """
        {
            "id": 1,
            "username": "root",
            "password": "$2a$12$bG26jPcfWQEh6sOjfCnraOplnwcn.v0kZ9ca3bfOJK0.U4AWABk2a",
            "roles": [
                {
                    "id": 1,
                    "roleName": "ROLE_ADMIN"
                }
            ]
        }
        """;
}
