package com.lms.controller;

import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/trace")
    @Observed(name = "trace-example", contextualName = "trace-example-request")
    public String traceExample() {
        return "Tracing example!";
    }
}
