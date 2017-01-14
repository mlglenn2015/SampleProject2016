package prv.mark.project;


import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.junit4.SpringRunner;
import prv.mark.project.client.HelloWorldClient;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringWsApplicationTests {

    @Autowired
    private HelloWorldClient helloWorldClient;

    @Test
    public void testSayHello() {
        System.out.println("TEST");
        //assertThat(helloWorldClient.sayHello("John", "Doe"))
        //        .isEqualTo("Hello John Doe!");
    }
}
