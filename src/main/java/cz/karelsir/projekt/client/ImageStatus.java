package cz.karelsir.projekt.client;

/**
 * Created by Ronik on 21. 5. 2017.
 */
public class ImageStatus {

    public enum ImageState {
        READY, PROCESSING
    }

    private ImageState state;

    public ImageStatus(ImageState state) {
        super();
        this.state = state;
    }

    public ImageState getState() {
        return state;
    }
}