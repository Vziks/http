import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HttpServerTest.class,
        SocketHandlerTest.class,
        TextBodyTest.class,
        StatusCodeTest.class,
        DirectoryControllerTest.class,
        FileControllerTest.class,
        InputControllerTest.class,
        OptionsControllerTest.class,
        ParamControllerTest.class,
        AccessControllerTest.class
})

public class AppSuiteTestClass {

}