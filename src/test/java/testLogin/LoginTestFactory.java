package testLogin;

import org.testng.annotations.Factory;

public class LoginTestFactory {

    @Factory
    public Object[] createInstances() {
        return new Object[]{
                new LoginTest("user", "pass", true),
                new LoginTest("wrongUser", "wrongPass", false)
        };
    }
}
