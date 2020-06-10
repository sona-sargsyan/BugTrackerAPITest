package com.test.automation.academy.test.application;

import com.test.automation.academy.generator.ApplicationGenerator;
import com.test.automation.academy.webservice.client.ApplicationClient;
import com.test.automation.academy.webservice.payload.Application;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ApplicationCreationTest {
    Application application;
    ApplicationClient applicationClient;

    @BeforeTest
    public void initTest() {
        RestAssured.baseURI = "http://localhost:8080";
        application = ApplicationGenerator.generateValidApplication();
        applicationClient = new ApplicationClient();

    }

    @Test
    public void ownerIsNotSent() {
        application.setOwner(null);
        applicationClient.createApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 400);
    }

    @Test
    public void applicationNameIsNotSent() {
        application.setName(null);
        applicationClient.createApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 400);
    }

    @Test(priority = 1)
    public void applicationCreated() {
        application.setApplication_id(ThreadLocalRandom.current().nextInt(1, 25));
        application.setOwner(UUID.randomUUID().toString());
        application.setName(UUID.randomUUID().toString());
        applicationClient.createApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 201);
    }

    @Test(priority = 2)
    public void applicationCreationConflict() {
        application.setApplication_id(application.getApplication_id());
        application.setOwner(application.getOwner());
        applicationClient.createApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 409);
    }
}
