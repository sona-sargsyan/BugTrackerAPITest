package com.test.automation.academy.test.application;

import com.test.automation.academy.generator.ApplicationGenerator;
import com.test.automation.academy.webservice.client.ApplicationClient;
import com.test.automation.academy.webservice.payload.Application;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ApplicationUpdateTest {
    Application application;
    ApplicationClient applicationClient;

    @BeforeTest
    public void initTest() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.defaultParser = Parser.JSON;
        application = ApplicationGenerator.generateValidApplication();
        application.setApplication_id(ThreadLocalRandom.current().nextInt(1, 5));
        application.setOwner(UUID.randomUUID().toString());
        application.setName(UUID.randomUUID().toString());
        applicationClient = new ApplicationClient();
        applicationClient.createApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 201);
    }

    @Test(priority = 1)
    public void updateApplicationWithId() {
        application.setOwner("edited");
        application.setName("edited");
        application.setDescription("edited");
        applicationClient.updateApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 200);
    }

    @Test(priority = 2)
    public void noApplicationWithId() {
        application.setApplication_id(application.getApplication_id() + 5);
        application.setOwner("edited");
        application.setName("edited");
        application.setDescription("edited");
        applicationClient.updateApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 500);
    }

    @Test
    public void ownerIsNotSent() {
        application.setOwner(null);
        application.setName("edited");
        application.setDescription("edited");
        applicationClient.updateApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 400);
    }

    @Test
    public void applicationNameIsNotSent() {
        application.setApplication_id(application.getApplication_id());
        application.setOwner("edited");
        application.setName(null);
        application.setDescription("edited");
        applicationClient.updateApplication(application);
        Assert.assertEquals(applicationClient.response.statusCode(), 400);
    }

}
