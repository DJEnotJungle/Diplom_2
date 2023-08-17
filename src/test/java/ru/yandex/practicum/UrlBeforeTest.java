package ru.yandex.practicum;

import io.restassured.RestAssured;
import org.junit.Before;
import static ru.yandex.practicum.constants.Constants.MASTER_URL;

public class UrlBeforeTest {
    @Before
    public void setURL(){
        RestAssured.baseURI=MASTER_URL;
    }
}
