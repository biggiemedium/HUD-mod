package dev.px.hud.Util.API;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import dev.px.hud.Mixin.Game.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.util.UUID;

public class Sessionutil {

    private static Sessionutil instance;
    private final UserAuthentication auth;

    public static Sessionutil getInstance() {
        if (instance == null) {
            instance = new Sessionutil();
        }

        return instance;
    }

    //Creates a new Authentication Service.
    private Sessionutil() {
        UUID notSureWhyINeedThis = UUID.randomUUID(); //Idk, needs a UUID. Seems to be fine making it random
        AuthenticationService authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), notSureWhyINeedThis.toString());
        auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }


    //Online mode
    //Checks if your already loggin in to the account.
    public void setUser(String email, String password) {
        if(!Minecraft.getMinecraft().getSession().getUsername().equals(email) || Minecraft.getMinecraft().getSession().getToken().equals("0")){

            this.auth.logOut();
            this.auth.setUsername(email);
            this.auth.setPassword(password);
            try {
                this.auth.logIn();
                Session session = new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName());
                setSession(session);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //Sets the session.
    //You need to make this public, and remove the final modifier on the session Object.
    private void setSession(Session session) {
        ((IMixinMinecraft) Minecraft.getMinecraft()).setSession(session);
    }

    //Login offline mode
    //Just like MCP does
    public void setUserOffline(String username) {
        this.auth.logOut();
        Session session = new Session(username, username, "0", "legacy");
        setSession(session);
    }

}
