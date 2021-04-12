import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import xyz.klenkiven.yygh.hosp.ServiceHospApplication;


/**
 * @author ：klenkiven
 * @date ：2021/4/8 20:49
 */
@SpringBootTest(classes = ServiceHospApplication.class)
public class AppTest {

    @Autowired
    private ApplicationContext ac;

    @Test
    public void test() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String s: beanDefinitionNames)
            System.out.println(s);
    }
}
