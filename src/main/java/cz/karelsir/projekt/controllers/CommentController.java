package cz.karelsir.projekt.controllers;

import cz.karelsir.projekt.client.ServerApi;
import cz.karelsir.projekt.controllers.exceptions.APIErrorMessage;
import cz.karelsir.projekt.controllers.exceptions.APIException;
import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.services.CommentService;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ronik on 21. 5. 2017.
 */
@RestController
public class CommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = ServerApi.COMMENTS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> showComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH + ServerApi.COMMENTS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> showCommentsByImage(@PathVariable("id") int id) {
        List<Comment> comments = commentService.getCommentsByImage(imageService.getImage(id));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.USER_PATH + ServerApi.COMMENTS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> showCommentsByUser(@PathVariable("id") int id) {
        List<Comment> comments = commentService.getCommentsByUser(userService.getUser(id));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH + ServerApi.COMMENTS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@PathVariable("id") int id, @RequestBody Comment comment) {
        if(commentService.exists(comment.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            commentService.create(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<Comment> updateComment(@PathVariable("id") int id, @RequestBody Comment comment) {
        Comment com = commentService.getComment(id);
        if(commentService.exists(com.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            commentService.update(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable("id") int id) {
        Comment comment = commentService.getComment(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.COMMENT_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("id") int id) {
        if (commentService.exists(id)) {
            commentService.deleteComment(id);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
