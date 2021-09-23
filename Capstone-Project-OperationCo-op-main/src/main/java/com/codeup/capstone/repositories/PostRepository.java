package com.codeup.capstone.repositories;

import com.codeup.capstone.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id); // select * from ads where title = ?
//    Post findFirstByTitle(String title); // select * from ads where title = ? limit 1

    // The following method is equivalent to the built in `getOne` method, there's no need to create this example

    @Query("from Post a where a.id = ?1")
    Post getPostById(long id);

//    This is for posting message on group page
    @Query("from Post a where a.group.id =?1 order by a.date DESC")
 List<Post> mostRecentPostsForGroup (long group_id);
}