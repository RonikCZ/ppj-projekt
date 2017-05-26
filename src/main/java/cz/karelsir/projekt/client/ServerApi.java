package cz.karelsir.projekt.client;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * Created by Ronik on 20. 5. 2017.
 */
public interface ServerApi {

    public static final String USERS_PATH = "/users";
    public static final String USER_PATH = USERS_PATH+ "/{id}";
    public static final String IMAGES_PATH = "/images";
    public static final String IMAGE_PATH = IMAGES_PATH + "/{id}";
    public static final String COMMENTS_PATH = "/comments";
    public static final String COMMENT_PATH = COMMENTS_PATH + "/{id}";

    @GET(USERS_PATH)
    public List<User> showUsers();

    @POST(USERS_PATH)
    public void addUser(@Body User user);

    @GET(USER_PATH)
    public User getUser(@Path("id") int id);

    @DELETE(USER_PATH)
    public void deleteUser(@Path("id") int id);


    @GET(COMMENTS_PATH)
    public List<Comment> showComments();

    @GET(IMAGE_PATH + COMMENTS_PATH)
    public List<Comment> showCommentsByImage(@Path("id") int id);

    @GET(USER_PATH + COMMENTS_PATH)
    public List<Comment> showCommentsByUser(@Path("id") int id);

    @POST(IMAGE_PATH + COMMENTS_PATH)
    public void addComment(@Body User user);

    @POST(COMMENT_PATH)
    public User updateComment(@Path("id") int id, @Body Comment comment);

    @GET(COMMENT_PATH)
    public User getComment(@Path("id") int id);

    @DELETE(COMMENT_PATH)
    public void deleteComment(@Path("id") int id);


    @GET(IMAGES_PATH)
    public List<Image> showImages();

    @GET(USER_PATH + IMAGES_PATH)
    public List<Image> showImagesByUser(@Path("id") int id);

    @GET(IMAGE_PATH)
    public Image getImage(@Path("id") int id);

    @DELETE(IMAGE_PATH)
    public void deleteImage(@Path("id") int id);

    @Multipart
    @POST(IMAGE_PATH)
    public ImageStatus uploadImage(@Path("name") String name, @Part("data") TypedFile imageData);

    @Streaming
    @GET(IMAGE_PATH)
    Response downloadImage(@Path("name") String name);



}
