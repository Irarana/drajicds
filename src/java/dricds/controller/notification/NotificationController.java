/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.controller.notification;

import dricds.dao.notification.NotificationDao;
import dricds.dao.notification.NotificationDaoImpl;
import dricds.model.login.LoginModel;
import dricds.model.notification.InBox;
import dricds.model.notification.NotiRespondant;
import dricds.model.notification.Notification;
import dricds.model.notification.TaskList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Ekank
 */
@SessionAttributes({"SessionAttr", "NotificationBean"})
@Controller
public class NotificationController {

    public static Log LOG = LogFactory.getLog(NotificationController.class);

    @Autowired
    private NotificationDao notificationDao;

    private static final String REDIRECT_TO_FORM = "redirect:../personRegistration";
    private static final String SUCCESS_PAGE = "registrationSuccess";
    private static final String NOTIFICATION = "NotificationBean";
    private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:homepage.htm";
    private static final String RESPONDANT_LIST = "notification/RespondantList";
    private static final String SEND_NOTIFICATION = "notification/SendNotification";
    private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";
    private static final String INBOXBEAN = "InBoxBean";
    private static final String INBOX_NOTIFICATION = "notification/ToDoList";
    private static final String TASKLIST = "notification/TaskList";
    private static final String REDIRECT_TO_INBOX_PAGE = "redirect:inboxNoti.htm";

    @RequestMapping(value = "sendNoti", method = RequestMethod.GET)
    public String setupForm(Model model, @ModelAttribute("SessionAttr") LoginModel lm) {
        LOG.info("lm.getLevelAreaCode() >> " + lm.getLevelAreaCode());
        Notification notification = new Notification();
        notification.setSendFromUserId(lm.getUserid());
        notification.setSendFrom(lm.getLevel());
        LOG.info("lm.getLevel() >>>>>>>>>>>>>>>>>>>>>>>>>" + lm.getLevel());
        notification.setSendFromCode(notificationDao.getUserPostCode(lm.getLevelAreaCode()));
        LOG.info("Sender From Code Id" + notification.getSendFromCode());
        LOG.info("Sender USer Id" + notification.getSendFromUserId());
        LOG.info("Send Form" + notification.getSendFrom());
        List<NotiRespondant> respondantList = notificationDao.respondantList();
        notification.setRepondantList(respondantList);
        model.addAttribute(NOTIFICATION, notification);
        return SEND_NOTIFICATION;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(NOTIFICATION) Notification notification,
            BindingResult result,
            SessionStatus status, @RequestParam("_page") int currentPage,
            Model model) {

        Map<Integer, String> pageForms = new HashMap<Integer, String>();
        pageForms.put(0, SEND_NOTIFICATION);
        pageForms.put(1, RESPONDANT_LIST);

        if (notiClickedCancel(request)) {
            status.setComplete();
            return REDIRECT_TO_SUCCESS_PAGE;
        } else if (notiIsSave(request)) {

            for (NotiRespondant nr : notification.getRepondantList()) {
                System.out.println(nr.getOfficeId() + " - Checked :" + nr.isChecked());
            }
            notification = notificationDao.insert(notification);
            return REDIRECT_TO_SUCCESS_PAGE;

        } else {
            int targetPage = WebUtils.getTargetPage(request, "_target",
                    currentPage);
            System.out.println("targetPage" + targetPage);
            System.out.println("currentPage" + currentPage);

            return pageForms.get(targetPage);

        }
    }

    private boolean userClickedPrevious(int currentPage, int targetPage) {
        return targetPage > currentPage;
    }

    private boolean notiIsSave(HttpServletRequest request) {
        return request.getParameter("_save") != null;
    }

    private boolean taskIsSubmit(HttpServletRequest request) {
        return request.getParameter("_submit") != null;
    }

    private boolean taskIsDraft(HttpServletRequest request) {
        return request.getParameter("_draft") != null;
    }

    private boolean notiClickedCancel(HttpServletRequest request) {
        return request.getParameter("_cancel") != null;
    }

    @ModelAttribute("ApproverList")
    public Map<String, String> getApproverList() {
        Map<String, String> approverList = new LinkedHashMap<String, String>();
        approverList.put("DIST", "District");
        approverList.put("PROJECT", "Project");
        approverList.put("SECTOR", "Sector");
        approverList.put("AWC", "AWC");
        return approverList;
    }

    @ModelAttribute("ListOfDays")
    public Map<Integer, Integer> getListOfDays() {
        Map<Integer, Integer> listOfdays = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < 100; i++) {
            listOfdays.put(i, i);
        }
        return listOfdays;
    }

//    @RequestMapping(value = "sendNoti", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView sendNotification(
//            @ModelAttribute("command") Notification notification) {
//        Locale locale = LocaleContextHolder.getLocale();
//        ModelAndView mv = new ModelAndView("notification/SendNotification", "command", notification);
//        System.out.println("Notification >>" + notification.getNotification());
//        Map<Integer, Integer> listOfdays = new HashMap<Integer, Integer>();
//        Map<String, String> approverList = new HashMap<String, String>();
//
//        for (int i = 0; i < 100; i++) {
//            listOfdays.put(i, i);
//        }
//        mv.addObject("ListOfDays", listOfdays);
//        approverList.put("DIST", "District");
//        approverList.put("PROJECT", "Project");
//        approverList.put("SECTOR", "Sector");
//        approverList.put("AWC", "AWC");
//        mv.addObject("ApproverList", approverList);
//        if (NotificationController.notification.getNotification() != null) {
//            notification = NotificationController.notification;
//        } else {
//            NotificationController.notification = notification;
//        }
//        //notification=notificationService.insert(notification);
//        return mv;
//    }
//    @RequestMapping(value = "respodantNoti", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView respondantNotification(
//            @ModelAttribute("command") Notification notification) {
//        Locale locale = LocaleContextHolder.getLocale();
//        ModelAndView mv = new ModelAndView("notification/RespondantList", "command", notification);
//        System.out.println("respondantNotification");
//
//        List<NotiRespondant> respondantList = notificationDao.respondantList();
//        notification.setRepondantList(respondantList);
//        System.out.println("respondantNotification  >> " + notification.getRepondantList().size());
//        NotificationController.notification.setRepondantList(respondantList);
//        notification = NotificationController.notification;
//        return mv;
//    }
//    @RequestMapping(value = "saveNoti", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView saveNotification(
//            @ModelAttribute("command") Notification notification) {
//        Locale locale = LocaleContextHolder.getLocale();
//        ModelAndView mv = new ModelAndView("notification/SendNotification", "command", notification);
//        System.out.println("Notification");
//
//        notification = notificationDao.insert(notification);
//        notification = NotificationController.notification;
//        return mv;
//    }
}
