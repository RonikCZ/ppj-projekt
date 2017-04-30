package cz.karelsir.projekt.services;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.repositories.CommentRepository;
import cz.karelsir.projekt.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    public void create(List<Comment> comments) {
        for (Comment comment:comments) {
            create(comment);
        }
    }

    public void changeLikes(Comment comment, boolean like) {
        if (like){
            comment.setLikes(comment.getLikes()+1);
        }else{
            comment.setLikes(comment.getLikes()-1);
        }
        update(comment);
    }

    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    public boolean exists(Integer id) {
        return commentRepository.exists(id);
    }

    public Comment getComment(int id) {
        return commentRepository.findOne(id);
    }

    public Comment getComment(String text) {
        return commentRepository.findByText(text).get(0);
    }

    public List<Comment> getAllComments() {
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteComment(int id) {
        commentRepository.delete(id);
    }

    public void deleteComments() {
        commentRepository.deleteAll();
    }
}
