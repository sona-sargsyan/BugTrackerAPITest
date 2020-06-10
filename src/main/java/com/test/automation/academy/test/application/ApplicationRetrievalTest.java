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

public class ApplicationRetrievalTest {
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

    @Test
    public void thereIsApplicationWithId() {
        applicationClient.getApplicationAsResponse(application.getApplication_id());
        Assert.assertEquals(applicationClient.response.statusCode(), 200);
    }

    @Test
    public void noApplicationWithId() {
        applicationClient.getApplicationAsResponse(application.getApplication_id() + 5);
        Assert.assertEquals(applicationClient.response.statusCode(), 404);
    }

    @Test
    public void validateResponse() {
        applicationClient.getApplicationAsEntity(application.getApplication_id());
        Assert.assertEquals(applicationClient.application.getApplication_id(), application.getApplication_id());
        Assert.assertEquals(applicationClient.application.getName(), application.getName());
        Assert.assertEquals(applicationClient.application.getDescription(), application.getDescription());
        Assert.assertEquals(applicationClient.application.getOwner(), application.getOwner());

    }

}
