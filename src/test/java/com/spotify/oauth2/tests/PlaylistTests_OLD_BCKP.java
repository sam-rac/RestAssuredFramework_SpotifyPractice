package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests_OLD_BCKP {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "BQBkILpobHKhbRXMGMHVkdd0Lt9LecQIu4dqWAWmVPPeKgk4X8ZiWjZz6UqCxfiJ6PtB2pLt0k6xwJRIm1HDjtx9qkC9sMIcis8_PsCktq7ZeeJ1f8cs3sTbn7bIHk7nokumstVWek7wfowDS9T6be369oK2u28r41wcvcNLyswSpkHoCuJHl2BRH91Uo5oxPGQ0b_AyncjOMjuZuxp-Yhtd89UbxN8yxvzpgF-y5GTp";

    @BeforeClass
    public void beforeclass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization","Bearer "+access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
         //       expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void ShouldBeAbleToCreateAPlaylist(){
        String payload = "{\n" +
                "  \"name\": \"New Playlist\",\n" +
                "  \"description\": \"New playlist description\",\n" +
                "  \"public\": false\n" +
                "}";

        given(requestSpecification).
                body(payload).
        when().
               post("/users/1forp5n7125hz6f766bkn7rtl/playlists").
        then().
               spec(responseSpecification).
               assertThat().
               statusCode(201).
               body("name",equalTo("New Playlist"),
                       "description",equalTo("New playlist description"),
                       "public",equalTo(false));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist(){
        given(requestSpecification).

                when().
                get("/playlists/2a54XZ3A2rmfe0kYDpvpB2").
                then().
                spec(responseSpecification).
                assertThat().
                statusCode(200).
                body("name",equalTo("Updated Playlist Name"),
                        "description",equalTo("Updated playlist description"),
                        "public",equalTo(false));
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist(){
        String payload = "{\n" +
                "  \"name\": \"Newly Updated Playlist Name\",\n" +
                "  \"description\": \"Newly Updated playlist description\",\n" +
                "  \"public\": false\n" +
                "}";

        given(requestSpecification).
                body(payload).
                when().
                put("/playlists/2a54XZ3A2rmfe0kYDpvpB2").
                then().
                spec(responseSpecification).
                assertThat().
                statusCode(200);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String payload = "{\n" +
                "  \"name\": New Playlist\"\",\n" +
                "  \"description\": \"New playlist description\",\n" +
                "  \"public\": false\n" +
                "}";

        given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization","Bearer "+"12345").
                contentType(ContentType.JSON).
                log().all().
                body(payload).
        when().
                post("/users/1forp5n7125hz6f766bkn7rtl/playlists").
        then().
                spec(responseSpecification).
                assertThat().
                statusCode(401).
                body("error.status",equalTo(401),
                        "error.message",equalTo("Invalid access token"));
    }

}
