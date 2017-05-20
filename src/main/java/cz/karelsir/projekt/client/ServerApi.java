package cz.karelsir.projekt.client;

import cz.karelsir.projekt.data.User;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Ronik on 20. 5. 2017.
 */
public interface ServerApi {

    public static final String OFFERS_PATH = "/offers";
    public static final String OFFER_PATH = OFFERS_PATH + "/{id}";
    public static final String USERS_PATH = "/users";
    public static final String USER_PATH = USERS_PATH+ "/{id}";

    @GET(USERS_PATH)
    public List<User> showUsers();

}
