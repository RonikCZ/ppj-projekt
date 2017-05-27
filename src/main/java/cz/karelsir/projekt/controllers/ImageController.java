package cz.karelsir.projekt.controllers;

import cz.karelsir.projekt.client.FileManager;
import cz.karelsir.projekt.client.ImageStatus;
import cz.karelsir.projekt.client.ServerApi;
import cz.karelsir.projekt.controllers.exceptions.APIErrorMessage;
import cz.karelsir.projekt.controllers.exceptions.APIException;
import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.services.CommentService;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.TagService;
import cz.karelsir.projekt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Ronik on 21. 5. 2017.
 */
@RestController
public class ImageController {

    private FileManager imageDataMgr;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = ServerApi.IMAGES_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Image>> showImages() {
        List<Image> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.USER_PATH + ServerApi.IMAGES_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Image>> showImagesByUser(@PathVariable("id") int id) {
        List<Image> images = imageService.getUserImages(userService.getUser(id));
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH, method = RequestMethod.GET)
    public ResponseEntity<Image> getImage(@PathVariable("id") int id) {
        Image image = imageService.getImage(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteImage(@PathVariable("id") int id) {
        if (imageService.exists(id)) {
            if (commentService.getCommentsByImage(imageService.getImage(id)).isEmpty() &&
                    tagService.getTagsByImage(imageService.getImage(id)).isEmpty()) {
                imageService.deleteImage(id);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                throw new APIException("Cannot delete image with tag or comment");
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = ServerApi.IMAGES_PATH, method = RequestMethod.POST)
    public ResponseEntity<ImageStatus> uploadImage(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("data") MultipartFile imageFile
    ) {
        if (!userService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.getUser(id);
        int nextId = imageService.getMaxId()+1;
        String fileName = nextId + "_" + name;

        ImageStatus state = new ImageStatus(ImageStatus.ImageState.READY);

        setFileManager();

        try {
            imageDataMgr.saveImageData(fileName, imageFile.getInputStream());
            Image image = new Image(user,fileName,name);
            imageService.create(image);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @RequestMapping(value = ServerApi.IMAGE_PATH + "/data", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpEntity<byte[]> downloadImage(@PathVariable("id") int id,
                                     HttpServletResponse response) {
        if (!imageService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Image requestedImage = imageService.getImage(id);


        byte[] image = new byte[0];
        HttpHeaders headers = new HttpHeaders();

        setFileManager();
        if (imageDataMgr.imageExists(requestedImage.getUrl())) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageDataMgr.copyImageData(requestedImage.getUrl(), bos);
                image = bos.toByteArray();
                headers.setContentLength(image.length);
                String mime = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
                headers.setContentType(MediaType.valueOf(mime));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return new HttpEntity<byte[]>(image, headers);
    }

    public void setFileManager() {
        try {
            imageDataMgr = FileManager.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIErrorMessage> handleAPIException(APIException ex) {
        return new ResponseEntity<>(new APIErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}