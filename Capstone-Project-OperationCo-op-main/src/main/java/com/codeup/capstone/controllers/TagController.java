package com.codeup.capstone.controllers;

import com.codeup.capstone.repositories.GameRepository;
import com.codeup.capstone.repositories.GroupRepository;
import com.codeup.capstone.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagController {
    private final UserRepository userDao;
    private final GroupRepository groupDao;
    private final GameRepository gameRepo;

    public TagController(UserRepository userDao, GroupRepository groupDao, GameRepository gameRepo) {
        this.userDao= userDao;
        this.groupDao = groupDao;
        this.gameRepo= gameRepo;
    }

// --------methods for work flow------------

    @GetMapping("/tags")
    public String index(Model model) {
        model.addAttribute("tags", userDao.findAll());
        return "posts/index";
    }


}

// ----------- FOR (PRODUCTION) Uncomment below and  Comment on TOP for file mapping purposes
// ----------- FOR (ORIGIN) Comment below and  UnComment on TOP for file mapping purpose

//package com.codeup.capstone.controllers;
//
//import com.codeup.capstone.repositories.GameRepository;
//import com.codeup.capstone.repositories.GroupRepository;
//import com.codeup.capstone.repositories.UserRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class TagController {
//    private final UserRepository userDao;
//    private final GroupRepository groupDao;
//    private final GameRepository gameRepo;
//
//    public TagController(UserRepository userDao, GroupRepository groupDao, GameRepository gameRepo) {
//        this.userDao= userDao;
//        this.groupDao = groupDao;
//        this.gameRepo= gameRepo;
//    }
//
//// --------methods for work flow------------
//
//    @GetMapping("/tags")
//    public String index(Model model) {
//        model.addAttribute("tags", userDao.findAll());
//        return "posts/index";
//    }
//
//
//}
//
