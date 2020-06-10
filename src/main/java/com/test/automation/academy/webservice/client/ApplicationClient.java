package com.test.automation.academy.webservice.client;

import com.google.gson.Gson;
import com.test.automation.academy.webservice.payload.Application;
import io.restassured.response.Response;

import static io.restassured.RestAssured.with;

public class ApplicationClient {

    public Response response;
    public Application application;
    public Gson gson;

    public void createApplication(Application application) {
        gson = new Gson();
        response = with().log().all().body(gson.toJson(application)).contentType("application/json")
                .when().log().all()
                .request("POST", "/v1/application");
    }

    public void updateApplication(Application application) {
        gson = new Gson();
        String bodyJson = gson.toJson(application).replace("application_id", "id");
        response = with().log().all().body(bodyJson).contentType("application/json")
                .when().log().all()
                .put("/v1/application");

    }

    public void getApplicationAsEntity(int applicationId) {
        application = with().log().all().get("/v1/application/" + applicationId).then().extract().as(Application.class);

    }

    public void getApplicationAsResponse(int applicationId) {
        response = with().log().all().get("/v1/application/" + applicationId).andReturn();
    }

    public void deleteApplication(int applicationId) {
        response = with().log().all().delete("/v1/application/" + applicationId).andReturn();
    }
}
