package cz.karelsir.projekt.controllers;

import cz.karelsir.projekt.client.ServerApi;
import cz.karelsir.projekt.controllers.exceptions.APIErrorMessage;
import cz.karelsir.projekt.controllers.exceptions.APIException;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.services.CommentService;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ronik on 20. 5. 2017.
 */
public class UserController {
    private UserService userService;
    private ImageService imageService;
    private CommentService commentService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }


    @RequestMapping(value = ServerApi.USERS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<User>> showUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.USERS_PATH, method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (userService.getUser(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.create(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.USER_PATH, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        if (userService.exists(id)) {
            User user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.USER_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
        if (userService.exists(id)) {
            if (!imageService.getUserImages(userService.getUser(id)).isEmpty() &&
                    !commentService.getCommentsByUser(userService.getUser(id)).isEmpty()) {
                userService.deleteUser(id);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                throw new APIException("Cannot delete user with image or comment");
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
