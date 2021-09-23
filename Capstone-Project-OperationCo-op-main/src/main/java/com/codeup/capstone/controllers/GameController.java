//package com.codeup.capstone.controllers;
//
//import com.codeup.capstone.models.Game;
//import com.codeup.capstone.models.Group;
//import com.codeup.capstone.models.Tag;
//import com.codeup.capstone.models.User;
//import com.codeup.capstone.repositories.GameRepository;
//import com.codeup.capstone.repositories.GroupRepository;
//import com.codeup.capstone.repositories.TagRepository;
//import com.codeup.capstone.repositories.UserRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Controller
//public class GameController {
//    private final GameRepository gameRepo;
//    private final UserRepository userRepo;
//    private final TagRepository tagRepo;
//    private final GroupRepository groupRepo;
//
//
//    public GameController(GameRepository gameRepo, UserRepository userRepo, TagRepository tagRepo, GroupRepository groupRepo) {
//        this.userRepo = userRepo;
//        this.gameRepo = gameRepo;
//        this.tagRepo = tagRepo;
//        this.groupRepo = groupRepo;
//
//    }
//    @GetMapping("/games")
//    public String showGames(Model model) {
//        List<Game> games = gameRepo.findAll();
//        model.addAttribute("games", games);
//
//        return "/games/games";
//    }
//    @GetMapping("/games/search")
//    public String showGame(@RequestParam String term, Model model){
//        List<Game> games = gameRepo.searchByTitleLike(term);
//        model.addAttribute("games", games);
//        return "/games/search";
//    }
//}

// ----------- FOR (PRODUCTION) Uncomment below and  Comment on TOP for file mapping purposes
// ----------- FOR (ORIGIN) Comment below and  UnComment on TOP for file mapping purpose
package com.codeup.capstone.controllers;

        import com.codeup.capstone.models.Game;
        import com.codeup.capstone.models.Group;
        import com.codeup.capstone.models.Tag;
        import com.codeup.capstone.models.User;
        import com.codeup.capstone.repositories.GameRepository;
        import com.codeup.capstone.repositories.GroupRepository;
        import com.codeup.capstone.repositories.TagRepository;
        import com.codeup.capstone.repositories.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.repository.query.Param;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;
        import java.util.Optional;
        import java.util.stream.Collectors;
        import java.util.stream.IntStream;


@Controller
public class GameController {
    private final GameRepository gameRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    private final GroupRepository groupRepo;


    public GameController(GameRepository gameRepo, UserRepository userRepo, TagRepository tagRepo, GroupRepository groupRepo) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
        this.tagRepo = tagRepo;
        this.groupRepo = groupRepo;

    }
    @GetMapping("/games")
    public String showGames(Model model) {
        List<Game> games = gameRepo.findAll();
        model.addAttribute("games", games);
        return "games/games";
      
    }
    @GetMapping("/games/search")
    public String showGame(@RequestParam String term, Model model){
        List<Game> games = gameRepo.searchByTitleLike(term);
        model.addAttribute("games", games);
        return "games/search";

    }

}