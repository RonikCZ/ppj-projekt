package cz.karelsir.projekt.controllers;

import cz.karelsir.projekt.client.ServerApi;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.Tag;
import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.services.CommentService;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.TagService;
import cz.karelsir.projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ronik on 26. 5. 2017.
 */
@RestController
public class TagController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = ServerApi.TAGS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> showTags() {
        List<Tag> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH + ServerApi.TAGS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> showTagsByImage(@PathVariable("id") int id) {
        List<Tag> tags = tagService.getTagsByImage(imageService.getImage(id));
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.TAG_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteTag(@PathVariable("id") int id, @PathVariable("tag") String tag) {
        Image image = imageService.getImage(id);
        if (tagService.exists(image, tag)) {
            Tag deleted = tagService.getTag(image, tag);
            tagService.delete(deleted);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = ServerApi.TAG_PATH, method = RequestMethod.GET)
    public ResponseEntity<Tag> getTag(@PathVariable("id") int id, @PathVariable("tag") String tag) {
        Tag get = tagService.getTag(imageService.getImage(id), tag);
        return new ResponseEntity<>(get, HttpStatus.OK);
    }

}
