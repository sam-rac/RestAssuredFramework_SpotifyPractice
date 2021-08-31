package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth2.0")
@Feature("Playlist API")
public class PlaylistTests extends  BaseTest{

    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this a testcase description for ShouldBeAbleToCreateAPlaylist test ")
    @Test(description = "should be able to create a playlist")
    public void ShouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        /*Playlist requestPlaylist = playlistBuilder("New Playlist","New playlist description",false);*/
/*        Playlist requestPlaylist = new Playlist()
        .setName("New Playlist")
        .setDescription("New playlist description")
        .setPublic(false);*/
        assertStatusCode(PlaylistApi.post(requestPlaylist), StatusCode.CODE_201);//.code);//201);
/*        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(),equalTo(201));*/
        //Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(PlaylistApi.post(requestPlaylist).as(Playlist.class),requestPlaylist);
/*        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));*/
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("New Playlist","New playlist description",false);
/*        Playlist requestPlaylist = new Playlist()
                .setName("New Playlist")
                .setDescription("New playlist description")
                .setPublic(false);*/
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response,StatusCode.CODE_200);
       // assertThat(response.statusCode(),equalTo(200));*/
        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(PlaylistApi.post(requestPlaylist).as(Playlist.class),requestPlaylist);
/*        assertThat(responsePlaylist.getName(),equalTo("Newly Updated Playlist Name"));
        assertThat(responsePlaylist.getDescription(),equalTo("Newly Updated playlist description"));
        assertThat(responsePlaylist.getPublic(),equalTo(false));*/
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
/*        Playlist requestPlaylist = new Playlist().
        setName("Newly Updated Playlist Name").
        setDescription("Newly Updated playlist description").
        setPublic(false);*/
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response,StatusCode.CODE_200);//getCode()));
    }

    @Story("Create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithName(){
        Playlist requestPlaylist = playlistBuilder("",generateDescription(),false);/*
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("New playlist description").
                setPublic(false);
*/       Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response,StatusCode.CODE_400);
        //assertThat(response.statusCode(),equalTo(400));*/
        //assertError(response.as(Error.class),400,StatusCode.CODE_400.msg);
        assertError(response.as(Error.class),StatusCode.CODE_400);
        /*Error error= response.as(Error.class);
        assertThat(error.getError().getStatus(),equalTo(400));
        assertThat(error.getError().getMessage(),equalTo("Missing required field: name"));*/
    }

    @Story("Create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        String invalidToken = "12345";
/*        Playlist requestPlaylist = new Playlist().
        setName("New Playlist").
        setDescription("New playlist description").
        setPublic(false);*/

        Response response = PlaylistApi.post(invalidToken,requestPlaylist);
        assertStatusCode(response,StatusCode.CODE_401);
        /*Response response = PlaylistApi.post(invalidToken,requestPlaylist);
        assertThat(response.statusCode(),equalTo(401));*/

        assertError(response.as(Error.class),StatusCode.CODE_401);
        /*Error error= response.as(Error.class);

        assertThat(error.getError().getStatus(),equalTo(401));
        assertThat(error.getError().getMessage(),equalTo("Invalid access token"));*/
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
/*        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;*/
/*      return new Playlist()
                .setName(name)
                .setDescription(description)
                .setPublic(_public);*/
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist){
        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(Response response,StatusCode statusCode){//int actualStatusCode){
        assertThat(response.statusCode(),equalTo(statusCode.code));
    }

    @Step
    public void assertError(Error reposnseErr,StatusCode statusCode){//,String expectedMessage){
        assertThat(reposnseErr.getError().getStatus(),equalTo(statusCode.code));
        assertThat(reposnseErr.getError().getMessage(),equalTo(statusCode.msg));
    }
}
