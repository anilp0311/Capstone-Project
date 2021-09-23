//package com.codeup.capstone.controllers;
//
//import com.codeup.capstone.models.Group;
//import com.codeup.capstone.models.User;
//import com.codeup.capstone.repositories.GroupRepository;
//import com.codeup.capstone.repositories.UserRepository;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class AdminController {
//
//    private final UserRepository userDao;
//    private final GroupRepository groupDao;
//
//
//    public AdminController(UserRepository userDao, GroupRepository groupDao) {
//        this.userDao = userDao;
//        this.groupDao = groupDao;
//    }
//
//    //    print-out all the users
//    @GetMapping("/admin")
//    public String siteAdmin(Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (userDao.getOne(user.getId()).getSiteAdmin()) {
//            model.addAttribute("users", userDao.findAll());
//            return "/admin/admin";
//        } else {
//            return "/users/login";
//        }
//    }
//
//    //delete the user
//    @PostMapping("/admin/delete")
//    public String deleteProfile(@RequestParam long userId) {
//        User deleteProfile = userDao.getOne(userId);
//        userDao.delete(deleteProfile);
//        return "redirect:/admin";
//    }
//}

// ----------- FOR (PRODUCTION) Uncomment below and  Comment on TOP for file mapping purposes
// ----------- FOR (ORIGIN) Comment below and  UnComment on TOP for file mapping purpose

package com.codeup.capstone.controllers;

        import com.codeup.capstone.models.Group;
        import com.codeup.capstone.models.User;
        import com.codeup.capstone.repositories.GroupRepository;
        import com.codeup.capstone.repositories.UserRepository;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final UserRepository userDao;
    private final GroupRepository groupDao;


    public AdminController(UserRepository userDao, GroupRepository groupDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
    }

    //    print-out all the users
    @GetMapping("/admin")
    public String siteAdmin(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDao.getOne(user.getId()).getSiteAdmin()) {
            model.addAttribute("users", userDao.findAll());
            return "admin/admin";
        } else {
            return "users/login";
        }
    }

    //delete the user
    @PostMapping("/admin/delete")
    public String deleteProfile(@RequestParam long userId) {
        User deleteProfile = userDao.getOne(userId);
        userDao.delete(deleteProfile);
        return "redirect:/admin";
    }
}