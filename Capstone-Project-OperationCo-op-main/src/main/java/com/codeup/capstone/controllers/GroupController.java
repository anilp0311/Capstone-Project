package com.codeup.capstone.controllers;

import com.codeup.capstone.models.*;
import com.codeup.capstone.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class GroupController {
    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final GameRepository gameRepo;
    private final TagRepository tagDao;
    private final PostRepository postRepo;

    public GroupController(GroupRepository groupDao, UserRepository userDao,
                           GameRepository gameRepo, TagRepository tagDao, PostRepository postRepo) {
        this.groupDao = groupDao;
        this.userDao = userDao;
        this.gameRepo = gameRepo;
        this.tagDao = tagDao;
        this.postRepo = postRepo;
    }

    //To show all groups
    @GetMapping("/groups")
    public String showAllGroups(Model model) {
        model.addAttribute("groups", groupDao.findAll());
        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "groups/index";
    }

    //Mapping to get group/create.html
    @GetMapping("/groups/create")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("group", new Group());
        List<Tag> tagsList = tagDao.findAll();
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "groups/create";
    }

    @PostMapping("/groups/create")
    public String saveGroup(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam List<Long> tags) {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(obj instanceof UserDetails)) {
            return "redirect:/login";
        }
        User user = (User) obj;
        user = userDao.getOne(user.getId());
        Group group = new Group();
        List<Tag> tagLists = new ArrayList<>();
        for(long id : tags){
            tagLists.add(tagDao.getOne(id));
        }
        List<User> users = new ArrayList<>();
        List<Group> groups = user.getGroups();
        users.add(user);
        group.setName(name);
        group.setDescription(description);
        group.setOwner(user);
        group.setUsers(users);
        group.setTags(tagLists);
        group.setProfilePic("/img/default-group-profile.png");
        groupDao.save(group);
        groups.add(group);
        user.setGroups(groups);
        userDao.save(user);
        return "redirect:/groups/profile/" + group.getId();
    }

    //group's Profile pic area
    @PostMapping("/groups/pic")
    public String saveProfile(@RequestParam long groupId, @RequestParam String url, @ModelAttribute Group group) {
        Group saveProfile = groupDao.getOne(groupId);
        saveProfile.setProfilePic(url);
        groupDao.save(saveProfile);
        return "redirect:/groups/{id}";
    }

    @GetMapping("/groups/profile/{id}")
    public String profilePage(@PathVariable long id, Model model) {
        Group group = groupDao.getOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isMember = false;
        for (User member : group.getUsers()) {
            if (member.getId() == user.getId()) {
                isMember = true;
            }
        }
        List<Post> mostRecent = postRepo.mostRecentPostsForGroup(id);
        model.addAttribute("group", group);
        model.addAttribute("user", user);
        model.addAttribute("isMember", isMember);
        if (mostRecent.size() > 5) {
            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                posts.add(mostRecent.get(i));
            }
            model.addAttribute("posts", posts);
        } else {
            model.addAttribute("posts", mostRecent);
        }
        return "groups/profile";
    }

//-------------for editing group profile information

    @GetMapping("/groups/edit/{id}")
    public String EditGroup(@PathVariable long id, Model model) {
        model.addAttribute("editGroup", groupDao.getOne(id));
        List<Tag> tagsList = tagDao.findAll();
        model.addAttribute("tagsList", tagsList);
        return "groups/edit";
    }

    @PostMapping("/groups/edit/{id}")
    public String postEditGroup(@PathVariable long id, @RequestParam(name = "name") String name,
                                @RequestParam List<Long> tags,
                                @RequestParam(name = "description") String description) {
        List<Tag> tagLists = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            Tag thisTag = tagDao.getOne(tags.get(i));
            tagLists.add(thisTag);
        }
        Group group = groupDao.getOne(id);
        group.setName(name);
        group.setDescription(description);
        group.setTags(tagLists);
        groupDao.save(group);
        return "redirect:/groups/profile/" + group.getId();
    }

    @GetMapping("/groups/delete/{id}")
    public String deleteGroup(@PathVariable long id) {
        Group group = groupDao.getOne(id);
        groupDao.delete(group);
        return "redirect:/groups";
    }

    // ---------- Shows the users within the groups page
    @GetMapping("/groups/users")
    public String showUsers(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "groups/profile";
    }

    @PostMapping("/groups/join/{id}")
    public String joinGroup(@PathVariable long id, @RequestParam long UserId) {
        Group group = groupDao.getOne(id);
        User user = userDao.getOne(UserId);
        List<Group> groups = user.getGroups();
        if (!groups.contains(group)) {
            groups.add(group);
            user.setGroups(groups);
            userDao.save(user);
        }
        return "redirect:/groups/profile/" + id;
    }

    @GetMapping("/groups/userDelete/{id}")
    public String deleteGroupUser(@PathVariable long id, @RequestParam long UserId) {
        Group group = groupDao.getOne(id);
        User user = userDao.getOne(UserId);
        List<Group> groups = user.getGroups();
        groups.remove(group);
        user.setGroups(groups);
        userDao.save(user);
        return "redirect:/groups/profile/" + id;
    }

    ////    ---------- Groups Search
    @GetMapping("/groups/search")
    public String showGroup(@RequestParam String groupTerm, Model model) {
        List<Group> groups = groupDao.searchByNameLike(groupTerm);
        model.addAttribute("groups", groups);
        return "groups/search";
    }
}

// ----------- FOR (PRODUCTION) Uncomment below and  Comment on TOP for file mapping purposes
// ----------- FOR (ORIGIN) Comment below and  UnComment on TOP for file mapping purpose

//package com.codeup.capstone.controllers;
//
//import com.codeup.capstone.models.*;
//import com.codeup.capstone.repositories.*;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//public class GroupController {
//    private final GroupRepository groupDao;
//    private final UserRepository userDao;
//    private final GameRepository gameRepo;
//    private final TagRepository tagDao;
//    private final PostRepository postRepo;
//
//    public GroupController(GroupRepository groupDao, UserRepository userDao,
//                           GameRepository gameRepo, TagRepository tagDao, PostRepository postRepo) {
//        this.groupDao = groupDao;
//        this.userDao = userDao;
//        this.gameRepo = gameRepo;
//        this.tagDao = tagDao;
//        this.postRepo = postRepo;
//    }
//
//    //To show all groups
//    @GetMapping("/groups")
//    public String showAllGroups(Model model) {
//        model.addAttribute("groups", groupDao.findAll());
//        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        return "groups/index";
//    }
//
//    //Mapping to get group/create.html
//    @GetMapping("/groups/create")
//    public String showCreateGroupForm(Model model) {
//        model.addAttribute("group", new Group());
//        List<Tag> tagsList = tagDao.findAll();
//        model.addAttribute("tagsList", tagsList);
//        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        return "groups/create";
//    }
//
//    @PostMapping("/groups/create")
//    public String saveGroup(
//            @RequestParam(name = "name") String name,
//            @RequestParam(name = "description") String description,
//            @RequestParam List<Long> tags) {
//
//        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!(obj instanceof UserDetails)) {
//            return "redirect:login";
//        }
//        User user = (User) obj;
//        user = userDao.getOne(user.getId());
//        Group group = new Group();
//        List<Tag> tagLists = new ArrayList<>();
//        List<User> users = new ArrayList<>();
//        List<Group> groups = user.getGroups();
//        users.add(user);
//        group.setName(name);
//        group.setDescription(description);
//        group.setOwner(user);
//        group.setUsers(users);
//        group.setTags(tagLists);
//        groupDao.save(group);
//        groups.add(group);
//        user.setGroups(groups);
//        userDao.save(user);
//        return "redirect:groups/profile/" + group.getId();
//    }
//
//    //group's Profile pic area
//    @PostMapping("/groups/pic")
//    public String saveProfile(@RequestParam long groupId, @RequestParam String url, @ModelAttribute Group group) {
//        Group saveProfile = groupDao.getOne(groupId);
//        saveProfile.setProfilePic(url);
//        groupDao.save(saveProfile);
//        return "redirect:groups/{id}";
//    }
//
//    @GetMapping("/groups/profile/{id}")
//    public String profilePage(@PathVariable long id, Model model) {
//        Group group = groupDao.getOne(id);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        boolean isMember = false;
//        for (User member : group.getUsers()) {
//            if (member.getId() == user.getId()) {
//                isMember = true;
//            }
//        }
//        List<Post> mostRecent = postRepo.mostRecentPostsForGroup(id);
//        model.addAttribute("group", group);
//        model.addAttribute("user", user);
//        model.addAttribute("isMember", isMember);
//        if (mostRecent.size() > 5) {
//            List<Post> posts = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                posts.add(mostRecent.get(i));
//            }
//            model.addAttribute("posts", posts);
//        } else {
//            model.addAttribute("posts", mostRecent);
//        }
//        return "groups/profile";
//    }
//
////-------------for editing group profile information
//
//    @GetMapping("/groups/edit/{id}")
//    public String EditGroup(@PathVariable long id, Model model) {
//        model.addAttribute("editGroup", groupDao.getOne(id));
//        List<Tag> tagsList = tagDao.findAll();
//        model.addAttribute("tagsList", tagsList);
//        return "groups/edit";
//    }
//
//    @PostMapping("/groups/edit/{id}")
//    public String postEditGroup(@PathVariable long id, @RequestParam(name = "name") String name,
//                                @RequestParam List<Long> tags,
//                                @RequestParam(name = "description") String description) {
//        List<Tag> tagLists = new ArrayList<>();
//        for (int i = 0; i < tags.size(); i++) {
//            Tag thisTag = tagDao.getOne(tags.get(i));
//            tagLists.add(thisTag);
//        }
//        Group group = groupDao.getOne(id);
//        group.setName(name);
//        group.setDescription(description);
//        group.setTags(tagLists);
//        groupDao.save(group);
//        return "redirect: groups/profile/" + group.getId();
//    }
//
//    @GetMapping("/groups/delete/{id}")
//    public String deleteGroup(@PathVariable long id) {
//        Group group = groupDao.getOne(id);
//        groupDao.delete(group);
//        return "redirect: groups";
//    }
//
//    // ---------- Shows the users within the groups page
//    @GetMapping("/groups/users")
//    public String showUsers(Model model) {
//        List<User> users = userDao.findAll();
//        model.addAttribute("users", users);
//        return "groups/profile";
//    }
//
//    @PostMapping("/groups/join/{id}")
//    public String joinGroup(@PathVariable long id, @RequestParam long UserId) {
//        Group group = groupDao.getOne(id);
//        User user = userDao.getOne(UserId);
//        List<Group> groups = user.getGroups();
//        if (!groups.contains(group)) {
//            groups.add(group);
//            user.setGroups(groups);
//            userDao.save(user);
//        }
//        return "redirect:groups/profile/" + id;
//    }
//
//    @GetMapping("/groups/userDelete/{id}")
//    public String deleteGroupUser(@PathVariable long id, @RequestParam long UserId) {
//        Group group = groupDao.getOne(id);
//        User user = userDao.getOne(UserId);
//        List<Group> groups = user.getGroups();
//        groups.remove(group);
//        user.setGroups(groups);
//        userDao.save(user);
//        return "redirect:groups/profile/" + id;
//    }
//
//    ////    ---------- Groups Search
//    @GetMapping("/groups/search")
//    public String showGroup(@RequestParam String groupTerm, Model model) {
//        List<Group> groups = groupDao.searchByNameLike(groupTerm);
//        model.addAttribute("groups", groups);
//        return "groups/search";
//    }
//}