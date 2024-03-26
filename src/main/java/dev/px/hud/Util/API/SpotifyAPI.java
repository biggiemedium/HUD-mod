package dev.px.hud.Util.API;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SpotifyAPI {

    private SpotifyApi api;
    private Thread listeningThread;

    public static String ID = "3e828637c29840eeaf6a76533e2b2d40";
    public static String SECRET = "00ea674442454a82b749c29204f900a0";
    public static URI REDIRECTURI;

    // public API
    public String CODE_VERIFIER = "NlJx4kD4opk4HY7zBM6WfUHxX7HoF8A2TUhOIPGA74w";
    public String CODE_CHALLENGE = "w6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";
    public AuthorizationCodeUriRequest authCodeUriRequest;

    public boolean loggedIn = false;

    static { // bruh
        try {
            REDIRECTURI = new URI("http://localhost:4030/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // 3e828637c29840eeaf6a76533e2b2d40 CLIENT ID
    // 00ea674442454a82b749c29204f900a0 CLIENT SECRET
    // http://localhost:4030/ REdurect URL

    public SpotifyAPI(String id, String secret, String redirectURL, String token, String refreshToken) {
        this.api = new SpotifyApi.Builder()
                .setClientId(id)
                .setClientSecret(secret)
                .setRedirectUri(SpotifyHttpManager.makeUri(redirectURL))
                .setAccessToken(token)
                .setRefreshToken(refreshToken)
                .build();

        if(!loggedIn) {
            login();
        }
    }

    public SpotifyAPI() {
        this.api = new SpotifyApi.Builder()
                .setClientId("zyuxhfo1c51b5hxjk09x2uhv5n0svgd6g")
                .setClientSecret("zudknyqbh3wunbhcvg9uyvo7uwzeu6nne")
                .setRedirectUri(SpotifyHttpManager.makeUri("https://example.com/spotify-redirect"))
                .setAccessToken("taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk")
                .setRefreshToken("b0KuPuLw77Z0hQhCsK-GTHoEx_kethtn357V7iqwEpCTIsLgqbBC_vQBTGC6M5rINl0FrqHK-D3cbOsMOlfyVKuQPvpyGcLcxAoLOTpYXc28nVwB7iBq2oKj9G9lHkFOUKn")
                .build();

        if(!loggedIn) {
            login();
        }
    }

    public void login() {

    }

    public void startConnection() {

    }

    public void build(String clientID) {
        api = new SpotifyApi.Builder().setClientId(clientID)
                .setRedirectUri(SpotifyHttpManager.makeUri("http://localhost:4030"))
                .build();

        authCodeUriRequest = api.authorizationCodePKCEUri(CODE_CHALLENGE)
                .code_challenge_method("S256")
                .scope("user-read-playback-state user-read-playback-position user-modify-playback-state user-read-currently-playing")
                .build();

    }


}
