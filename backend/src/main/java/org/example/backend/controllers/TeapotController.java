package org.example.backend.controllers;

import org.example.backend.exceptions.ImATeapotException;
import org.example.backend.exceptions.NotFoundException;
import org.example.backend.exceptions.TeaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("brew")
public class TeapotController {

    @GetMapping("/{beverage}")
    public void iAmATeapot(@PathVariable String beverage) throws ImATeapotException {
        if (!Objects.equals(beverage, "tea")) {
            throw new ImATeapotException(beverage);
        } else {
            throw new TeaNotFoundException();
        }

    }

}
