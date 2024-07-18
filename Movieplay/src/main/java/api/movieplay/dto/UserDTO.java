package api.movieplay.dto;

import java.util.List;

public class UserDTO {

    public UserDTO(Long id, String firstName, String lastName, String nickname, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.email = email;
	}

	private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String profileImage;
    private List<Long> ratings;
    private List<Long> favorited;
	
    // Constructor
    public UserDTO() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<Long> getRatings() {
        return ratings;
    }

    public void setRatings(List<Long> ratings) {
        this.ratings = ratings;
    }

    public List<Long> getFavorited() {
        return favorited;
    }

    public void setFavorited(List<Long> favorited) {
        this.favorited = favorited;
    }

    // toString() method
    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nickname=" + nickname
                + ", email=" + email + ", profileImage=" + profileImage + ", ratings=" + ratings + ", favorited="
                + favorited + "]";
    }
}
