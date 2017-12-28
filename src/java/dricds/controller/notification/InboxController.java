/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.controller.notification;

import static dricds.controller.notification.OutboxController.LOG;
import dricds.dao.notification.NotificationDao;
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
 * @author ekank
 */
@SessionAttributes({"SessionAttr", "InBoxBean"})
@Controller
public class InboxController {

    public static Log LOG = LogFactory.getLog(InboxController.class);

    @Autowired
    private NotificationDao notificationDao;

    private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:homepage.htm";
    private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";
    private static final String INBOXBEAN = "InBoxBean";
    private static final String INBOX_NOTIFICATION = "notification/ToDoList";
    private static final String TASKLIST = "notification/TaskList";
    private static final String REDIRECT_TO_INBOX_PAGE = "redirect:inboxNoti.htm";
    private static final String ACTIVE = "A";
    private static final String REASSIGNED = "R";
    private static final String COMPLETED = "C";

    @RequestMapping(value = "inboxNoti", method = RequestMethod.GET)
    public String setupForm(Model model,
            @ModelAttribute("SessionAttr") LoginModel lm) {
        LOG.info("lm.getLevelAreaCode() >> " + lm.getLevelAreaCode());
        LOG.info("lm.getUserid() >> " + lm.getUserid());
        InBox inBox = notificationDao.setupInBox(lm.getLevelAreaCode(), lm.getLevel());
        inBox.setUserLevel(lm.getLevel());

        //List<Notification> notificationList = notificationDao.notificationList();
        //inBox.setNotificationList(notificationList);
        model.addAttribute(INBOXBEAN, inBox);
        return INBOX_NOTIFICATION;
    }

    @RequestMapping(value = "inboxAction", method = RequestMethod.POST)
    public String InBoxActions(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(INBOXBEAN) InBox inbox,
            BindingResult result,
            SessionStatus status, @RequestParam("_page") int currentPage,
            Model model) {

        Map<Integer, String> pageForms = new HashMap<Integer, String>();
        pageForms.put(0, INBOX_NOTIFICATION);
        pageForms.put(1, TASKLIST);

        if (taskIsDraft(request)) {
            status.setComplete();
            return REDIRECT_TO_INBOX_PAGE;
        } else if (taskIsSubmit(request)) {

             boolean breakFlag = false;

            for (Notification noti : inbox.getNotificationList()) {
                if (!breakFlag) {
                    if (noti.getRespondentId() == inbox.getSelApproveRespoId()) {
                        breakFlag = true;
                        LOG.info(noti.getRespondentId() + " - Submitted :" + inbox.getSelApproveRespoId());
                    notificationDao.repliedOnRespondent(inbox.getUserIncumbencyId(),noti.getNotificationId(),noti.getRespondentId());

                    }

                }
            }

            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_INBOX_PAGE;

        } else if (taskIsMarkCompleted(request)) {

            boolean breakFlag = false;
            String aboutId;
            int notificationId;
            List<TaskList> releatedTaskList;
            for (TaskList taskLst : inbox.getSelectedTaskList()) {
                 LOG.info(taskLst.getTaskId() + " - MArk cmpleted :" + inbox.getSelectedTaskID());
                if (!breakFlag) {
                    if (taskLst.getTaskId() == inbox.getSelectedTaskID()) {
                        breakFlag = true;
                        LOG.info(taskLst.getTaskId() + " - Checked :" + inbox.getSelectedTaskID());
                        aboutId = taskLst.getDetailsAboutId();
                        notificationId = taskLst.getNotId();
                        notificationDao.markCompleted(COMPLETED, taskLst.getRespTaskId());
                        LOG.info(taskLst.getRespTaskId() + " - Respondent Task Id :");
                        releatedTaskList = notificationDao.inBoxRelatedTaskList(aboutId, notificationId, taskLst.getRespTaskId());
                        for (TaskList taskLstTemp : releatedTaskList) {
                            if (taskLstTemp.getStatus() != null && taskLstTemp.getStatus().equalsIgnoreCase(REASSIGNED)) {
                                notificationDao.markCompleted(ACTIVE, taskLstTemp.getRespTaskId());
                            }
                        }
                       
                       notificationDao.completedTask("Y",inbox.getUserLevel(),inbox.getUserAreaCode(),inbox.getUserIncumbencyId(),taskLst.getTaskId());
                    }

                }
            }

            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_INBOX_PAGE;

        } else if (taskIsApprove(request)) {

            boolean breakFlag = false;

            for (Notification noti : inbox.getNotificationList()) {
                if (!breakFlag) {
                    if (noti.getRespondentId() == inbox.getSelApproveRespoId()) {
                        breakFlag = true;
                        LOG.info(noti.getRespondentId() + " - Checked :" + inbox.getSelApproveRespoId());
                    notificationDao.approvedRespondent(inbox.getUserIncumbencyId(),noti.getNotificationId(),noti.getRespondentId());

                    }

                }
            }

            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_INBOX_PAGE;

        } else if (taskIsReassign(request)) {
            LOG.info(" - Reassign getSelectedTaskID:" + inbox.getSelectedTaskID());
            boolean breakFlag = false;
            String aboutId;
            int notificationId;
            List<TaskList> releatedTaskList;
            for (TaskList taskLst : inbox.getSelectedTaskList()) {
                if (!breakFlag) {
                    if (taskLst.getTaskId() == inbox.getSelectedTaskID()) {
                        breakFlag = true;
                        LOG.info(taskLst.getTaskId() + " - Reassign :" + inbox.getSelectedTaskID());
                        aboutId = taskLst.getDetailsAboutId();
                        notificationId = taskLst.getNotId();
                        notificationDao.saveReassign(taskLst, inbox.getCurrentRespondent(), notificationId, aboutId, taskLst.getDetailsAbout(), taskLst.getRespTaskId(), taskLst.getSender());
                       // notificationDao.markCompleted(COMPLETED, taskLst.getRespTaskId());

                    }

                }
            }

            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_INBOX_PAGE;

        } else {
            int targetPage = WebUtils.getTargetPage(request, "_target",
                    currentPage);

            System.out.println("targetPage" + inbox.getSelectedNotification());
            for (Notification noti : inbox.getNotificationList()) {
                if (noti.getNotificationId() == inbox.getSelectedNotificationId()) {
                    if(noti.getRespondentId()==inbox.getSelApproveRespoId()){
                    
                    notificationDao.acknowlegedRespondent(inbox.getUserIncumbencyId(),noti.getNotificationId(),noti.getRespondentId());
                    inbox.setSelectedTaskList(noti.getTaskList());
                    inbox.setSelectedIsApproved(noti.isIsApprover());
                    inbox.setSelectedIsReplied(noti.isIsReplied());
                    }
                }
            }

            System.out.println("targetPage" + targetPage);
            System.out.println("currentPage" + currentPage);

            return pageForms.get(targetPage);

        }
    }

    private boolean userClickedPrevious(int currentPage, int targetPage) {
        return targetPage > currentPage;
    }

    private boolean taskIsSubmit(HttpServletRequest request) {
        return request.getParameter("_submit") != null;
    }

    private boolean taskIsMarkCompleted(HttpServletRequest request) {
        return request.getParameter("_markCompleted") != null;
    }

    private boolean taskIsDraft(HttpServletRequest request) {
        return request.getParameter("_draft") != null;
    }

    private boolean taskIsApprove(HttpServletRequest request) {
        return request.getParameter("_approve") != null;
    }

    private boolean taskIsReassign(HttpServletRequest request) {
        return request.getParameter("_reAssign") != null;
    }

}
