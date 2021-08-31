package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;
//import static com.spotify.oauth2.api.TokenManager.renewToken;
import static io.restassured.RestAssured.given;

public class PlaylistApi {
    //static String access_token = "BQCO1tXUip2w1c-R2duZgbfzhXPCdi4lBhfD7A1nQciGFMA-0LYWIZAsWgUZ_Kj65JvTZg20sICaE28CZ4jDKUQRwcO-1OthYBEe8ns1jWdhkIjipI8LH0Z-lRGrnzNte2dTCDWxTRE7T_ul7ewoyIPd-XcV5m9N_tWTfGyOVdvDUoiIZqprkqeWHzpVOg4E558q3u11kkuzS4H9fUW3gQsBA9thqKHNh-We1xfxclIY";

    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(USERS +"/"+ ConfigLoader.getInstance().getUser() + PLAYLISTS,getToken(),requestPlaylist);
/*        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization","Bearer "+access_token).
        when().
                post().
        then().
                spec(getResponseSpec()).
                extract().
                response();*/
    }

    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS +"/"+ ConfigLoader.getInstance().getUser() + PLAYLISTS,token,requestPlaylist);
       // return RestResource.post("/users/1forp5n7125hz6f766bkn7rtl/playlists",token,requestPlaylist);
        /*return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization","Bearer "+token).
                when().
                post("/users/1forp5n7125hz6f766bkn7rtl/playlists").
                then().
                spec(getResponseSpec()).
                extract().
                response();*/
    }

    public static Response get(String playlistID){
        return RestResource.get(PLAYLISTS + "/"+playlistID,getToken());
        /*return   given(getRequestSpec()).
                header("Authorization","Bearer "+access_token).
                        when().
                        get("/playlists/"+playlistID).
                        then().
                        spec(getResponseSpec()).

                        extract().
                        response();*/
    }

    public static  Response update(String playlistID, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/"+playlistID,getToken(),requestPlaylist);
        /*return  given(getRequestSpec()).
                header("Authorization","Bearer "+access_token).
                body(requestPlaylist).
                when().
                put("/playlists/"+playlistID).
                then().
                spec(getResponseSpec()).
                 extract().
                response();*/
    }
}
