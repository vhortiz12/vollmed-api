package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Anotación para que Spring sepa que esto es un controlador
@RequestMapping("/hello") //Ruta que queremos mapear
public class HelloController {

    @GetMapping //Anotación para mapear un request del tipo GET
    public String helloWorld() { ////Método para mapear request del tipo GET
        return "Hello world from Colombia!";
    }

}
