
package com.codeup.capstone.repositories;


import com.codeup.capstone.models.Game;
import com.codeup.capstone.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name); // select * from group where name = ?
    //    Game findFirstByTitle(String Title); // select * from games where title = ? limit 1

    //Following method shows you how to use named parameters in a HQL custom Query
//    @Query("FROM Group gr WHERE gr.name LIKE %:term%")
    @Query("SELECT gr FROM Group gr WHERE  gr.name LIKE %?1%"
            + "OR gr.discordUserId LIKE %?1%"
            + "OR gr.description LIKE %?1%"
    )
    List<Group>searchByNameLike(@Param("groupTerm") String term);

}