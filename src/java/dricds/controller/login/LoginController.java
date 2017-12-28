/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.controller.login;

import dricds.dao.login.LoginDAO;
import dricds.model.login.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author surendra
 */
@Controller
@SessionAttributes("SessionAttr")
public class LoginController {

    @Autowired
    LoginDAO loginDao;

    @RequestMapping(value = "loginpageController")
    public ModelAndView showPage() {
        ModelAndView mv = new ModelAndView("login", "command", new LoginModel());
        return mv;
    }
    
    @RequestMapping(value = "homepage")
    public ModelAndView homepage(@ModelAttribute("command") LoginModel login) {
        ModelAndView mv = new ModelAndView("index");
        return mv;
        
    }
    @RequestMapping(value = "loginValidateController")
    public ModelAndView validateUser(@ModelAttribute("command") LoginModel login) {
        String path = "login";
        LoginModel lm = null;
        ModelAndView mv = null;
        try {
            lm = loginDao.validateUser(login.getUserid(), login.getPassword());
            if (lm != null) {
                path = "index";
            } else {
                path = "login";
            }

            mv = new ModelAndView(path, "command", lm);
            mv.addObject("SessionAttr", lm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
}
