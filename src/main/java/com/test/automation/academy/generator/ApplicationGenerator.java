package com.test.automation.academy.generator;

import com.test.automation.academy.webservice.payload.Application;

public class ApplicationGenerator {

    public static Application generateValidApplication() {
        Application application = new Application();
        application.setOwner("Oleg");
        application.setName("bug tracker");
        application.setDescription("Bug tracker system");
        return application;
    }
}
