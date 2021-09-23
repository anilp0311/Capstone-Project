package com.codeup.capstone.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    //   ------------------Instance variables-------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "User must have a userName")
    @Size(min = 3, message = "userName must be at least 3 characters.")
    @Column(nullable = false, length = 100, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    //one upper case, one lower case, one digit, one special character, minimum 8 characters in length
//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[0-9]).{8,}$", message = "Password length must be at least 8 characters " +
//            "with one uppercase letter and one digit")
    @Column(nullable = false, length = 100)
    private String password;


    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String pronouns;

    @Column(nullable = false, length = 100)
    private Date birthDate;


    @Column(length = 500)
    private String bio;

    @Column(length = 100)
    @ColumnDefault("true")
    private Boolean isSiteAdmin;

    @Column(length = 100)
    private Boolean isBanned;

    @Column(columnDefinition = "TEXT")
    private String profilePic;

    @Column(length = 500)
    private String twitchInfo;

    @Column(length = 500)
    private String steamInfo;

    @Column(length = 500)
    private String xboxLiveInfo;

    @Column(length = 500)
    private String psnInfo;

    @Column(length = 500)
    private String nintendoInfo;

    @Column(length = 500)
    private String discordInfo;

    // -----------   relationship with user and tag class
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_tags",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<Tag> tags;

    //    creating relationship with group table
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "groups_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<Group> groups1;

    //  ----------  relationship with groups and Posts
    //Owner to messages
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;

    //    //Many users to one group
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Group> groupsOwned;

    //    ** Amaro Terrazas ** Inputting Games Feature
    @OneToMany(mappedBy = "user")
    private List<Game> games;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="favorites",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="game_id")}
    )
    private List<Game> favorites;


    //    for rating creating relationship with user
    @OneToMany(mappedBy = "rating_user")
    Set<UserRating> ratings_given;

    @OneToMany(mappedBy = "rated_user")
    Set<UserRating> ratings_received;

//    ------------constructors----------------------------

    public User() {}

    public User(long id, String userName, String email, String password, String fullName, String pronouns,
                Date birthDate, String bio, Boolean isSiteAdmin, Boolean isBanned, String profilePic,
                String twitchInfo, String steamInfo, String xboxLiveInfo, String psnInfo, String nintendoInfo,
                String discordInfo, List<Tag> tags, List<Group> groups, List<Post> posts, List<Group> groupsOwned,
                List<Game> games, List<Game> favorites, Set<UserRating> ratings_given, Set<UserRating> ratings_received) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.pronouns = pronouns;
        this.birthDate = birthDate;
        this.bio = bio;
        this.isSiteAdmin = isSiteAdmin;
        this.isBanned = isBanned;
        this.profilePic = profilePic;
        this.twitchInfo = twitchInfo;
        this.steamInfo = steamInfo;
        this.xboxLiveInfo = xboxLiveInfo;
        this.psnInfo = psnInfo;
        this.nintendoInfo = nintendoInfo;
        this.discordInfo = discordInfo;
        this.tags = tags;
        this.groups1 = groups;
        this.posts = posts;
        this.groupsOwned = groupsOwned;
        this.games = games;
        this.favorites = favorites;
        this.ratings_given = ratings_given;
        this.ratings_received = ratings_received;
    }

    // implement the Copy Constructor right here in the User model!
    public User(User copy) {
        this.id = copy.id; // VERY IMPORTANT. Many things won't work if you don't include this assignment
        this.email = copy.email;
        this.userName = copy.userName;
        this.password = copy.password;
        this.fullName = copy.fullName;
        this.pronouns = copy.pronouns;
        this.birthDate = copy.birthDate;
        this.bio = copy.bio;
        this.games = copy.games;
        this.favorites = copy.favorites;
        this.groups1 = copy.groups1;
    }


    public List<Game> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Game> favorites) {
        this.favorites = favorites;
    }

    public List<Group> getGroups() {
        return groups1;
    }

    public void setGroups(List<Group> groups) {
        this.groups1 = groups;
    }

    //  ------------ getters and setters-------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName(String fullName) {
        return fullName;
    }

    public void setFirstName(String fullName) {
        this.fullName = fullName;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getSiteAdmin() {
        return isSiteAdmin;
    }

    public void setSiteAdmin(Boolean siteAdmin) {
        isSiteAdmin = siteAdmin;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTwitchInfo() {
        return twitchInfo;
    }

    public void setTwitchInfo(String twitchInfo) {
        this.twitchInfo = twitchInfo;
    }

    public String getSteamInfo() {
        return steamInfo;
    }

    public void setSteamInfo(String steamInfo) {
        this.steamInfo = steamInfo;
    }

    public String getXboxLiveInfo() {
        return xboxLiveInfo;
    }

    public void setXboxLiveInfo(String xboxLiveInfo) {
        this.xboxLiveInfo = xboxLiveInfo;
    }

    public String getPsnInfo() {
        return psnInfo;
    }

    public void setPsnInfo(String psnInfo) {
        this.psnInfo = psnInfo;
    }

    public String getNintendoInfo() {
        return nintendoInfo;
    }

    public void setNintendoInfo(String nintendoInfo) {
        this.nintendoInfo = nintendoInfo;
    }

    public String getDiscordInfo() {
        return discordInfo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDiscordInfo(String discordInfo) {
        this.discordInfo = discordInfo;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Group> getGroupsOwned() {
        return groupsOwned;
    }

    public void setGroupsOwned(List<Group> groupsOwned) {
        this.groupsOwned = groupsOwned;
    }

    public List<Game> getGames(){
        return games;
    }
    public void setGames(List<Game> games){
        this.games = games;
    }

    public Set<UserRating> getRatings_given() {
        return ratings_given;
    }

    public void setRatings_given(Set<UserRating> ratings_given) {
        this.ratings_given = ratings_given;
    }

    public Set<UserRating> getRatings_received() {
        return ratings_received;
    }

    public void setRatings_received(Set<UserRating> ratings_received) {
        this.ratings_received = ratings_received;
    }
}