package formos.demo.executor.cucumber;

import formos.demo.executor.FormosBeerApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = FormosBeerApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
