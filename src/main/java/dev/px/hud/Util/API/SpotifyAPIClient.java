package dev.px.hud.Util.API;

import com.sun.net.httpserver.HttpServer;
import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.SpotifyListener;
import de.labystudio.spotifyapi.SpotifyListenerAdapter;
import de.labystudio.spotifyapi.model.Track;
import dev.px.hud.HUDMod;

public class SpotifyAPIClient {

    SpotifyAPI api;

    public SpotifyAPIClient() {

        api = SpotifyAPIFactory.createInitialized();

        if(!api.isInitialized()) {
            api.initializeAsync();
        }
   }

   public void start() {
        if(api.isInitialized()) {
            api.registerListener(new SpotifyListenerAdapter() {
                @Override
                public void onConnect() {
                    HUDMod.LOG.info("Connected to Spotify!");
                }

                @Override
                public void onTrackChanged(Track track) {
                    HUDMod.LOG.info("Track changed to: " + track);
                }

                @Override
                public void onPositionChanged(int position) {
                    if (!api.hasTrack()) {
                        return;
                    }

                    int length = api.getTrack().getLength();
                    float percentage = 100.0F / length * position;

                    HUDMod.LOG.info("Position changed: %s of %s (%d%%)\n", String.format("%s", position), String.format("%s", (length)), (int) percentage);
                }

                @Override
                public void onPlayBackChanged(boolean isPlaying) {
                    HUDMod.LOG.info(isPlaying ? "Song started playing" : "Song stopped playing");
                }

                @Override
                public void onSync() {

                }

                @Override
                public void onDisconnect(Exception exception) {
                    HUDMod.LOG.info("Disconnected from spotify");
                }
            });
        }
   }

}

 /*
    private SpotifyApi api;
    private Thread listeningThread;

    public static String ID = "3e828637c29840eeaf6a76533e2b2d40";
    public static String SECRET = "00ea674442454a82b749c29204f900a0";
    public static URI REDIRECTURI;
    private ExecutorService service = Executors.newSingleThreadExecutor();

    // public API
    public String CODE_VERIFIER = "NlJx4kD4opk4HY7zBM6WfUHxX7HoF8A2TUhOIPGA74w";
    public String CODE_CHALLENGE = "w6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";
    public AuthorizationCodeUriRequest authCodeUriRequest;

    public boolean loggedIn;
    private int tokenRefreshInterval = 2;
    private CurrentlyPlayingContext currentPlayingContext;
    private Track currentTrack;
    private HttpServer callbackServer;


    // 3e828637c29840eeaf6a76533e2b2d40 CLIENT ID
    // 00ea674442454a82b749c29204f900a0 CLIENT SECRET
    // http://localhost:4030/ REdurect URL

    public SpotifyCallbackHandler callback = code -> {
        Util.sendClientSideMessage("Conecting to spotify...", true);
        AuthorizationCodePKCERequest authCodePKCERequest = api.authorizationCodePKCE(code, CODE_VERIFIER).build();
        try {
            final AuthorizationCodeCredentials authCredentials = authCodePKCERequest.execute();
            api.setAccessToken(authCredentials.getAccessToken());
            api.setRefreshToken(authCredentials.getRefreshToken());
            tokenRefreshInterval = authCredentials.getExpiresIn();
            loggedIn = true;
            new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        TimeUnit.SECONDS.sleep(tokenRefreshInterval - 2);
                        System.out.println("Refreshing token...");
                        final AuthorizationCodeCredentials refreshRequest = api.authorizationCodePKCERefresh().build().execute();
                        api.setAccessToken(refreshRequest.getAccessToken());
                        api.setRefreshToken(refreshRequest.getRefreshToken());
                        tokenRefreshInterval = refreshRequest.getExpiresIn();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        final CurrentlyPlayingContext currentlyPlayingContext = api.getInformationAboutUsersCurrentPlayback().build().execute();
                        final String currentTrackId = currentlyPlayingContext.getItem().getId();
                        this.currentTrack = api.getTrack(currentTrackId).build().execute();
                        this.currentPlayingContext = currentlyPlayingContext;
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void startConnection() {
        Util.sendClientSideMessage("Starting da connection", true);
        if (!loggedIn) {
            try {
                Desktop.getDesktop().browse(authCodeUriRequest.execute());
                HUDMod.getExecutorService().submit(() -> {
                    try {
                        if (callbackServer != null) {
                            // Close the server if the module was disabled and re-enabled, to prevent already bound exception.
                            callbackServer.stop(0);
                        }
                        Util.sendClientSideMessage("Spotify" + EnumChatFormatting.GREEN + "Please allow access to the application.", true);
                        callbackServer = HttpServer.create(new InetSocketAddress(4030), 0);
                        callbackServer.createContext("/", context -> {
                            callback.handleCallback(context.getRequestURI().getQuery().split("=")[1]);
                            final String messageSuccess = context.getRequestURI().getQuery().contains("code")
                                    ? "Successfully authorized.\nYou can now close this window, have fun on Hud Mod!"
                                    : "Unable to Authorize client, re-toggle the module.";
                            context.sendResponseHeaders(200, messageSuccess.length());
                            OutputStream out = context.getResponseBody();
                            out.write(messageSuccess.getBytes());
                            out.close();
                            callbackServer.stop(0);
                        });
                        callbackServer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public SpotifyAPIClient() {

    }

    public void build(String id, String secret) {
        api = new SpotifyApi.Builder().setClientId(id)
                .setClientSecret(secret)
                .setRedirectUri(SpotifyHttpManager.makeUri("http://localhost:4030"))
                .build();

        authCodeUriRequest = api.authorizationCodePKCEUri(CODE_CHALLENGE)
                .code_challenge_method("S256")
                .scope("user-read-playback-state user-read-playback-position user-modify-playback-state user-read-currently-playing")
                .build();
    }

    public void skipToPreviousTrack() {
        try {
            api.skipUsersPlaybackToPreviousTrack().build().execute();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            Util.sendClientSideMessage("Spotify" + EnumChatFormatting.RED + e.getMessage());
        }
    }

    public void skipTrack() {
        try {
            api.skipUsersPlaybackToNextTrack().build().execute();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            Util.sendClientSideMessage("Spotify" + EnumChatFormatting.RED + e.getMessage());
        }
    }

    public void toggleShuffleState() {
        try {
            api.toggleShuffleForUsersPlayback(!currentPlayingContext.getShuffle_state()).build().execute();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            Util.sendClientSideMessage("Spotify" + EnumChatFormatting.RED + e.getMessage());
        }
    }

    public void pausePlayback() {
        try {
            api.pauseUsersPlayback().build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            Util.sendClientSideMessage("Spotify" + EnumChatFormatting.RED + e.getMessage());
        } catch (org.apache.hc.core5.http.ParseException e) {
            e.printStackTrace();
        }
    }

    public void resumePlayback() {
        try {
            api.startResumeUsersPlayback().build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            Util.sendClientSideMessage("Spotify" + EnumChatFormatting.RED + e.getMessage());
        } catch (org.apache.hc.core5.http.ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying(){
        return currentPlayingContext.getIs_playing();
    }

    public SpotifyApi getApi() {
        return api;
    }

    public Thread getListeningThread() {
        return listeningThread;
    }

    public static String getID() {
        return ID;
    }

    public static String getSECRET() {
        return SECRET;
    }

    public static URI getREDIRECTURI() {
        return REDIRECTURI;
    }

    public String getCODE_VERIFIER() {
        return CODE_VERIFIER;
    }

    public String getCODE_CHALLENGE() {
        return CODE_CHALLENGE;
    }

    public AuthorizationCodeUriRequest getAuthCodeUriRequest() {
        return authCodeUriRequest;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getTokenRefreshInterval() {
        return tokenRefreshInterval;
    }

    public CurrentlyPlayingContext getCurrentPlayingContext() {
        return currentPlayingContext;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public HttpServer getCallbackServer() {
        return callbackServer;
    }

    public SpotifyCallbackHandler getCallback() {
        return callback;
    }
    */
