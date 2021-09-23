//package com.codeup.capstone.models;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name="user")
//public class GameUser {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column
//    private String GameUserName;
//
//    @OneToMany(mappedBy = "user")
//    private List<Game> games;
//
//    public GameUser(long id, String GameUserName, List<Game> games) {
//        this.id = id;
//        this.GameUserName = GameUserName;
//        this.games = games;
//    }
//
//    public GameUser() {
//
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getGameUserName() {
//        return GameUserName;
//    }
//
//    public void setGameUserName(String user_name) {
//        this.GameUserName = GameUserName;
//    }
//
//    public List<Game> getGames() {
//        return games;
//    }
//
//    public void setGames(List<Game> games) {
//        this.games = games;
//    }
//}
package com.codeup.capstone.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "users")
public class GameUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private String GameUserName;

    @OneToMany(mappedBy = "user")
    private List<Game> games;

    public GameUser(long id, String GameUserName, List<Game> games) {
        this.id = id;
        this.GameUserName = GameUserName;
        this.games = games;
    }

    public GameUser() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameUserName() {
        return GameUserName;
    }

    public void setGameUserName(String user_name) {
        this.GameUserName = GameUserName;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}