package com.codeup.capstone.controllers;

import com.codeup.capstone.models.*;
import com.codeup.capstone.models.Game;
import com.codeup.capstone.models.Group;
import com.codeup.capstone.models.Tag;
import com.codeup.capstone.models.User;
import com.codeup.capstone.repositories.GameRepository;
import com.codeup.capstone.repositories.GroupRepository;
import com.codeup.capstone.repositories.TagRepository;
import com.codeup.capstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final TagRepository tagDao;
    private final GroupRepository groupDao;
    private final GameRepository gameRepo;


    //---------constructor
    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, TagRepository tagDao, GroupRepository groupDao, GameRepository gameRepo) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.tagDao = tagDao;
        this.groupDao = groupDao;
        this.gameRepo = gameRepo;
    }

// ---------methods for work flow------------

    // Want the user to be able to access a sign-up page
    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signup";
    }

    // What happens when a new user submits the register form?
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, @RequestParam(name = "confirmPassword") String confirmPassword,
                           @RequestParam(name = "password") String password) {
        if (!user.getPassword().equals(confirmPassword)) {
            return "redirect:/sign-up";
        }
        String hash = passwordEncoder.encode(user.getPassword()); // ~plaintext password
        user.setPassword(hash); // It's hashed
        user.setProfilePic("/img/default-profile-icon.jpg");
        if (user.getId() == 0) {
            userDao.save(user);
            return "redirect:/login";
        } else {
            userDao.save(user);
            return "redirect:/profile";
        }
    }

    // redirecting login user into their profile page
    @GetMapping("/profile")
    public String profilePage(Model model) {
        User getUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userDao.getOne(getUser.getId()));
        model.addAttribute("photoUrl", userDao.getOne(getUser.getId()).getProfilePic());
        return "users/profile";
    }

    @GetMapping("/profile/{id}")
    public String profilePage(Model model, @PathVariable long id) {
        User getUser = userDao.getOne(id);
        model.addAttribute("user", userDao.getOne(getUser.getId()));
        model.addAttribute("photoUrl", userDao.getOne(getUser.getId()).getProfilePic());
        return "users/profile";
    }

    //user's Profile pic  area
    @PostMapping("/profile/pic")
    public String saveProfile(@RequestParam long userId, @RequestParam String url, @ModelAttribute User user) {
        User saveProfile = userDao.getOne(userId);
        saveProfile.setProfilePic(url);
        userDao.save(saveProfile);
        return "redirect:/profile";
    }

    //    editing user profile information like bio, tags and other usernames
    @GetMapping("/users/edit/{id}")
    public String EditProfile(@PathVariable long id, Model model) {
        model.addAttribute("editUser", userDao.getOne(id));
        List<Tag> tagsList = tagDao.findAll();
        model.addAttribute("tagsList", tagsList);
        return "users/editProfile";
    }


    //Used (required false) because every user will not have all gaming accounts so it is optional for user
    @PostMapping("/users/edit/{id}")
    public String postEditGroup(@PathVariable long id, @RequestParam List<Long> tags,
                                @RequestParam String bio,
                                @RequestParam(required = false) String psnInfo, @RequestParam(required = false) String steamInfo,
                                @RequestParam(required = false) String twitchInfo, @RequestParam(required = false) String xboxLiveInfo,
                                @RequestParam(required = false) String nintendoInfo, @RequestParam(required = false) String discordInfo) {
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            Tag thisTag = tagDao.getOne(tags.get(i));
            tagList.add(thisTag);
        }
        User user = userDao.getOne(id);
        user.setBio(bio);
        user.setTags(tagList);
        user.setPsnInfo(psnInfo);
        user.setSteamInfo(steamInfo);
        user.setTwitchInfo(twitchInfo);
        user.setXboxLiveInfo(xboxLiveInfo);
        user.setNintendoInfo(nintendoInfo);
        user.setDiscordInfo(discordInfo);
        userDao.save(user);
        return "redirect:/profile";
    }

    //    user can delete their account
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userDao.getOne(id);
        userDao.delete(user);
        return "redirect:/sign-up";
    }

    //    user rating stars
    @PostMapping("/users/rating/{id}")
    public String userRating(@RequestParam long userId, @RequestParam int rating, @ModelAttribute User user) {
        User userRated = userDao.getOne(userId);
        User userRating = userDao.getOne(((User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getId());
        Set<UserRating> ratingUser = userRated.getRatings_received();
        ratingUser.add(new UserRating(rating, userRating, userRated));
        userRated.setRatings_received(ratingUser);
        userDao.save(userRated);
        return "redirect:/profile";
    }


    //    ----------Favorite a Game
    @PostMapping("/users/favorite")
    public String userFavorite(@RequestParam long gameId, @ModelAttribute User user) {
        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFav = (userDao.getOne(tempUser.getId()));
        Game gameFavorite = gameRepo.getOne(gameId);
        List<Game> favorites = userFav.getFavorites();
        favorites.add(gameFavorite);
        userFav.setFavorites(favorites);
        userDao.save(userFav);
        return "redirect:/profile";
    }


    //    ---------------- UnFavor a Game

    @GetMapping("/users/userDelete/{id}")
    public String deleteFavGame(@PathVariable long id, @RequestParam long UserId) {
        User userUnFav = userDao.getOne(id);
        Game gameUnFavorite = gameRepo.getOne(UserId);
        List<Game> favorites = userUnFav.getFavorites();
        favorites.remove(gameUnFavorite);
        userUnFav.setFavorites(favorites);
        userDao.save(userUnFav);
        return "redirect:/profile/" + id;
    }




    //-------------------------Search for Users
    @GetMapping("/users/search")
    public String showUser(@RequestParam String userTerm, Model model) {

        List<User> users = userDao.findByUserNameLike(userTerm);
        model.addAttribute("users", users);
        return "users/search";
    }

    //        ----------------------Leave Group
    @GetMapping("/users/userLeave/{id}")
    public String leaveGroup(@PathVariable long id, @RequestParam long UserId) {
        User userLeave = userDao.getOne(id);
        Group groupLeave = groupDao.getOne(UserId);
        List<Group> groups = userLeave.getGroups();
        groups.remove(groupLeave);
        userLeave.setGroups(groups);
        userDao.save(userLeave);
        return "redirect:/profile/" ;
    }


}


// ----------- FOR (PRODUCTION) Uncomment below and  Comment on TOP for file mapping purposes
// ----------- FOR (ORIGIN) Comment below and  UnComment on TOP for file mapping purpose
//
//package com.codeup.capstone.controllers;
//
//import com.codeup.capstone.models.*;
//
//import com.codeup.capstone.models.Game;
//import com.codeup.capstone.models.Group;
//import com.codeup.capstone.models.Tag;
//import com.codeup.capstone.models.User;
//import com.codeup.capstone.repositories.GameRepository;
//import com.codeup.capstone.repositories.GroupRepository;
//import com.codeup.capstone.repositories.TagRepository;
//import com.codeup.capstone.repositories.UserRepository;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Controller
//public class UserController {
//    private final UserRepository userDao;
//    private final PasswordEncoder passwordEncoder;
//    private final TagRepository tagDao;
//    private final GroupRepository groupDao;
//    private final GameRepository gameRepo;
//
//
//    //---------constructor
//    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, TagRepository tagDao, GroupRepository groupDao, GameRepository gameRepo) {
//        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//        this.tagDao = tagDao;
//        this.groupDao = groupDao;
//        this.gameRepo = gameRepo;
//    }
//
//// ---------methods for work flow------------
//
//    // Want the user to be able to access a sign-up page
//    @GetMapping("/sign-up")
//    public String showSignupForm(Model model) {
//        model.addAttribute("user", new User());
//        return "users/signup";
//    }
//
//    // What happens when a new user submits the register form?
//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user, @RequestParam(name = "confirmPassword") String confirmPassword,
//                           @RequestParam(name = "password") String password) {
//        if (!user.getPassword().equals(confirmPassword)) {
//            return "users/signup";
//        }
//        String hash = passwordEncoder.encode(user.getPassword()); // ~plaintext password
//        user.setPassword(hash); // It's hashed
//        if (user.getId() == 0) {
//            userDao.save(user);
//            return "redirect:/login";
//        } else {
//            userDao.save(user);
//            return "users/profile";
//        }
//    }
//
//    // redirecting login user into their profile page
//    @GetMapping("/profile")
//    public String profilePage(Model model) {
//        User getUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("user", userDao.getOne(getUser.getId()));
//        model.addAttribute("photoUrl", userDao.getOne(getUser.getId()).getProfilePic());
//        return "users/profile";
//    }
//
//    @GetMapping("/profile/{id}")
//    public String profilePage(Model model, @PathVariable long id) {
//        User getUser = userDao.getOne(id);
//        model.addAttribute("user", userDao.getOne(getUser.getId()));
//        model.addAttribute("photoUrl", userDao.getOne(getUser.getId()).getProfilePic());
//        return "users/profile";
//    }
//
//    //user's Profile pic  area
//    @PostMapping("/profile/pic")
//    public String saveProfile(@RequestParam long userId, @RequestParam String url, @ModelAttribute User user) {
//        User saveProfile = userDao.getOne(userId);
//        saveProfile.setProfilePic(url);
//        userDao.save(saveProfile);
//        return "redirect:profile";
//    }
//
//    //    editing user profile information like bio, tags and other usernames
//    @GetMapping("/users/edit/{id}")
//    public String EditProfile(@PathVariable long id, Model model) {
//        model.addAttribute("editUser", userDao.getOne(id));
//        List<Tag> tagsList = tagDao.findAll();
//        model.addAttribute("tagsList", tagsList);
//        return "users/editProfile";
//    }
//
//
//    //Used (required false) because every user will not have all gaming accounts so it is optional for user
//    @PostMapping("/users/edit/{id}")
//    public String postEditGroup(@PathVariable long id, @RequestParam List<Long> tags,
//                                @RequestParam String bio ,
//                                @RequestParam (required = false) String psnInfo ,@RequestParam (required = false) String steamInfo ,
//                                @RequestParam (required = false) String twitchInfo,@RequestParam(required = false) String xboxLiveInfo,
//                                @RequestParam (required = false) String nintendoInfo) {
//        List<Tag> tagList = new ArrayList<>();
//        for(int i= 0; i< tags.size(); i++){
//            Tag thisTag = tagDao.getOne(tags.get(i));
//            tagList.add(thisTag);
//        }
//        User user = userDao.getOne(id);
//        user.setBio(bio);
//        user.setTags(tagList);
//        user.setPsnInfo(psnInfo);
//        user.setSteamInfo(steamInfo);
//        user.setTwitchInfo(twitchInfo);
//        user.setXboxLiveInfo(xboxLiveInfo);
//        user.setNintendoInfo(nintendoInfo);
//        userDao.save(user);
//        return "redirect:profile";
//    }
//
//    //    user can delete their account
//    @GetMapping("/users/delete/{id}")
//    public String deleteUser(@PathVariable long id) {
//        User user = userDao.getOne(id);
//        userDao.delete(user);
//        return "redirect:sign-up";
//    }
//
//    //    user rating stars
//    @PostMapping("/users/rating/{id}")
//    public String userRating(@RequestParam long userId,@RequestParam int rating, @ModelAttribute User user) {
//        User userRated = userDao.getOne(userId);
//        User userRating = userDao.getOne(((User) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal()).getId());
//        Set<UserRating> ratingUser = userRated.getRatings_received();
//        ratingUser.add(new UserRating(rating, userRating, userRated));
//        userRated.setRatings_received(ratingUser);
//        userDao.save(userRated);
//        return "redirect:profile";
//    }
//
//
//    //    ----------Inserting Favorites
//    @PostMapping("/users/favorite")
//    public String userFavorite(@RequestParam long gameId, @ModelAttribute User user ) {
//        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User userFav = (userDao.getOne(tempUser.getId()));
//        Game gameFavorite = gameRepo.getOne(gameId);
//        List<Game> favorites = userFav.getFavorites();
//        favorites.add(gameFavorite);
//        userFav.setFavorites(favorites);
//        userDao.save(userFav);
//        return "redirect:profile";
//    }
//}
