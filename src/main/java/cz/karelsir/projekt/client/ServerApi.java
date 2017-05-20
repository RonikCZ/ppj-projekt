package cz.karelsir.projekt.client;

import cz.karelsir.projekt.data.User;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.List;

/**
 * Created by Ronik on 20. 5. 2017.
 */
public interface ServerApi {

    public static final String USERS_PATH = "/users";
    public static final String USER_PATH = USERS_PATH+ "/{id}";
    public static final String IMAGES_PATH = "/images";
    public static final String IMAGE_PATH = IMAGES_PATH + "/{id}";

    @GET(USERS_PATH)
    public List<User> showUsers();

    @POST(USERS_PATH)
    public void addUser(@Body User user);

    @GET(USER_PATH)
    public User getUser(@Path("id") int id);

    @DELETE(USER_PATH)
    public void deleteUser(@Path("id") int id);

}
