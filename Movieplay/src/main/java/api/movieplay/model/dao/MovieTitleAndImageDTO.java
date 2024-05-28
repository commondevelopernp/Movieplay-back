package api.movieplay.model.dao;

public class MovieTitleAndImageDTO {
    private String title;
    private String image;

    public MovieTitleAndImageDTO(String title, String image) {
        this.title = title;
        this.image = image;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
