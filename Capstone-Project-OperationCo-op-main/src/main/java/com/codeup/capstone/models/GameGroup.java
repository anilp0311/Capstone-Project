
package com.codeup.capstone.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="groups1")
public class GameGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private String name;

    @ManyToMany(mappedBy = "groups1")
    private List<Game> games;

    public GameGroup(){

    }

    public GameGroup(long id, String name, List<Game> games) {
        this.id = id;
        this.name = name;
        this.games = games;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}