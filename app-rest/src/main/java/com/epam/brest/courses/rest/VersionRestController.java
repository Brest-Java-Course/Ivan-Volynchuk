package com.epam.brest.courses.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fieldistor on 03.11.14.
 */
@Controller
@RequestMapping("/version")
public class VersionRestController {
    public ResponseEntity<String> getVersion(){
        return new ResponseEntity("1.0", HttpStatus.OK);
    }
}
