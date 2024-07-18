package api.movieplay.model.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String firstName, String lastName, String nickname, String email,String profileImage,String refresh) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.email = email;
		this.refresh = refresh;
		this.profileImage = profileImage;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "refresh")
    private String refresh;
    @ElementCollection
    @CollectionTable(name = "user_ratings", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "movie_id")
    private List<Long> ratings;

    @ElementCollection
    @CollectionTable(name = "user_favorited", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "movie_id")
    private List<Long> favorited;

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
	public String getRefreshToken() {
		// TODO Auto-generated method stub
		return this.refresh;
	}
	public void setRefreshToken(String refresh) {
		this.refresh=refresh;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nickname=" + nickname
				+ ", email=" + email + ", profileImage=" + profileImage + ", ratings=" + ratings + ", favorited="
				+ favorited + "]";
	}


}
