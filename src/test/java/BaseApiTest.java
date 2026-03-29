import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static data.TestData.BASE_URL;
import static steps.UserStep.deleteUser;

public class BaseApiTest {
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }
}
