package com.pruebas.holamundo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoControlador {
	@RequestMapping("/")
    String hello() {
        return "Hola mundoooo, Spring Boot!";
    }

}
