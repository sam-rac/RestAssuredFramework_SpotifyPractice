package com.spotify.oauth2.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.api.Route.BASE_PATH;
import static io.restassured.RestAssured.config;

public class SpecBuilder {


    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("BASE_URI")).
                //setBaseUri("https://api.spotify.com").
                setBasePath(BASE_PATH).//"/v1").
              //  addHeader("Authorization","Bearer "+access_token).
               /*config(config().encoderConfig(EncoderConfig.encoderConfig()
                      .appendDefaultContentCharsetToContentTypeIfUndefined(false))).*/

                setContentType(ContentType.JSON).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL).
                build().config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    }

    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("ACCOUNT_BASE_URI")).
                //setBaseUri("https://accounts.spotify.com").
                //  addHeader("Authorization","Bearer "+access_token).
                        setContentType(ContentType.URLENC).
                        addFilter(new AllureRestAssured()).
                        log(LogDetail.ALL).
                        build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                //       expectContentType(ContentType.JSON).

                        log(LogDetail.ALL).
                        build();

    }
}
