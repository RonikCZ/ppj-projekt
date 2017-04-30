package cz.karelsir.projekt.services;

import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.Tag;
import cz.karelsir.projekt.data.TagId;
import cz.karelsir.projekt.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public void create(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return StreamSupport.stream(tagRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Tag> getTagsByImage(Image image) {
        return StreamSupport.stream(tagRepository.findByImage(image).spliterator(), false).collect(Collectors.toList());
    }

    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    public Tag getTag(Image image, String title) {
        TagId tagId = new TagId(image, title);
        return tagRepository.findOne(tagId);
    }

    public void deleteTags() {
        tagRepository.deleteAll();
    }

}
