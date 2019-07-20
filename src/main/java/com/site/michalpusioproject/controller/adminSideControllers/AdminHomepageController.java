package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.domains.Result;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomepageController {

    @Autowired
    ResultRepository resultRepository;

    @RequestMapping({"/", ""})
    public String getHomepage() {

//        User user = new User();
//
//        resultRepository.save(new Result(11, new Quiz(), user));
//        resultRepository.save(new Result(31, new Quiz(), user));

        return "admin/homepage";
    }
}
