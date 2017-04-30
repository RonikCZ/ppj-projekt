package cz.karelsir.projekt.services;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
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
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void create(Image image) {
        imageRepository.save(image);
    }

    public void create(List<Image> images) {
        for (Image image:images) {
            create(image);
        }
    }

    public void changeLikes(Image image, boolean like) {
        if (like){
            image.setLikes(image.getLikes()+1);
        }else{
            image.setLikes(image.getLikes()-1);
        }
        update(image);
    }

    public void update(Image image) {
        imageRepository.save(image);
    }

    public boolean exists(Integer id) {
        return imageRepository.exists(id);
    }

    public Image getImage(int id) {
        return imageRepository.findOne(id);
    }

    public Image getImage(String url) {
        return imageRepository.findByUrl(url).get(0);
    }

    public List<Image> getUserImages(User user) {
        if(user.getId() <= 0) {
            return null;
        }
        List<Image> images = imageRepository.findByUser(user.getId());

        if(images.size() == 0) {
            return null;
        }
        return images;
    }

    public List<Image> getAllImages() {
        return StreamSupport.stream(imageRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteImage(int id) {
        imageRepository.delete(id);
    }

    public void deleteImages() {
        imageRepository.deleteAll();
    }
}
