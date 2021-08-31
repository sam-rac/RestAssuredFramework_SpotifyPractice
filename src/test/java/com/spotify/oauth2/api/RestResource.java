package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String path,String access_token,Object requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                auth().oauth2(access_token).
                //header("Authorization","Bearer "+access_token).
        when().
                post(path).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String,String> formParams){
        return given(getAccountRequestSpec()).
                      formParams(formParams).
                when().
                      post(API + TOKEN).
                then().
                      spec(getResponseSpec()).
                      extract().response();
    }

    public static Response get(String path,String access_token){
        return   given(getRequestSpec()).
                auth().oauth2(access_token).
                //header("Authorization","Bearer "+access_token).
                        when().
                        get(path).
                        then().
                        spec(getResponseSpec()).

                        extract().
                        response();
    }

    public static  Response update(String path,String access_token,Object requestPlaylist){
        return  given(getRequestSpec()).
                auth().oauth2(access_token).
                //header("Authorization","Bearer "+access_token).
                body(requestPlaylist).
                when().
                put(path).
                then().
                spec(getResponseSpec()).
                 extract().
                response();
    }
}
