/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.controller.notification;

import dricds.dao.notification.NotificationDao;
import dricds.model.login.LoginModel;
import dricds.model.notification.Notification;
import dricds.model.notification.OutBox;
import dricds.model.notification.Respondents;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author ekank
 */
@SessionAttributes({"SessionAttr", "OutBoxBean"})
@Controller
public class OutboxController {

    public static Log LOG = LogFactory.getLog(OutboxController.class);
    @Autowired
    private NotificationDao notificationDao;

    private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:homepage.htm";
    private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";
    private static final String OUTBOXBEAN = "OutBoxBean";
    private static final String OUTBOX_NOTIFICATION = "notification/OutBoxList";
    private static final String RESPONDANTLIST = "notification/OutBoxRespondentsList";
    private static final String REDIRECT_TO_OUTBOX_PAGE = "redirect:outboxNoti.htm";
    private static final String DISTRICT = "DIST";
    private static final String PROJECT = "PROJECT";
    private static final String SECTOR = "SECTOR";
    private static final String AWC = "AWC";
    private static final String STATE = "STATE";

    @RequestMapping(value = "outboxNoti", method = RequestMethod.GET)
    public String setupForm(
            Model model,
            @ModelAttribute("SessionAttr") LoginModel lm) {
        // OutBox outBox= new OutBox();
        LOG.info("lm.getLevelAreaCode() >> " + lm.getLevelAreaCode());
        LOG.info("lm.getUserid() >> " + lm.getUserid());
        OutBox outBox = notificationDao.setupOutBox(lm.getUserid());

        //List<Notification> outboxNotificationList = notificationDao.outboxNotificationList();
        // outBox.setNotificationList(outboxNotificationList);
        model.addAttribute(OUTBOXBEAN, outBox);
        return OUTBOX_NOTIFICATION;
    }

    @RequestMapping(value = "outboxAction", method = RequestMethod.POST)
    public String OutBoxActions(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OUTBOXBEAN) OutBox outbox,
            BindingResult result,
            SessionStatus status, @RequestParam("_page") int currentPage,
            Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        Map<Integer, String> pageForms = new HashMap<Integer, String>();
        pageForms.put(0, OUTBOX_NOTIFICATION);
        pageForms.put(1, RESPONDANTLIST);

        if (taskIsBack0(request)) {
            status.setComplete();
            return REDIRECT_TO_SUCCESS_PAGE;
        } else if (taskIsBack1(request)) {
            status.setComplete();
            return REDIRECT_TO_OUTBOX_PAGE;
        } else if (taskIsFilter(request)) {
            LOG.info("selected district" + outbox.getSelDistOption());
            LOG.info("selected Project" + outbox.getSelProjOption());

            for (Notification noti : outbox.getNotificationList()) {
                if (noti.getNotificationId() == outbox.getSelectedNotificationId()) {
                    //  if (outbox.getSelDistOption() != null && !outbox.getSelDistOption().trim().isEmpty()) {
                    Map<String, List<Respondents>> localRespondentLst = notificationDao.getRespondantOnDist(noti.getRespondentsList());
                    List<Respondents> finalRespondentLst = new ArrayList<>();
                    for (String distTemp : localRespondentLst.keySet()) {
                        Respondents localRes = new Respondents();

                        LOG.info(distTemp + " <Dist >>" + localRespondentLst.get(distTemp).size());
                        Set<String> projectSet = new HashSet<>();
                        for (Respondents resTemp : localRespondentLst.get(distTemp)) {
                            Respondents localResTemp = new Respondents();
                            if ((outbox.getSelDistOption() != null && !outbox.getSelDistOption().trim().isEmpty() && distTemp.equalsIgnoreCase(outbox.getSelDistOption())) || (outbox.getSelDistOption() != null && outbox.getSelDistOption().trim().isEmpty())) {

                                LOG.info("resTemp.getRespReportingProjName() " + resTemp.getRespReportingProjName());
                                LOG.info("resTemp.getRespReportingProjName() " + resTemp.getRespReportingSecName());
                                localResTemp = resTemp;
                                if (outbox.getSelProjOption() != null && outbox.getSelProjOption().isEmpty()) {
                                    //localResTemp.setRespReportingDistName(resTemp.getRespReportingSecName());

                                    //finalRespondentLst.add(localResTemp);
                                    projectSet.add(resTemp.getRespReportingProj());
                                } else if (outbox.getSelProjOption() != null && !outbox.getSelProjOption().isEmpty() && resTemp.getRespReportingProj().equalsIgnoreCase(outbox.getSelProjOption())) {
                                    localResTemp.setRespReportingDistName(resTemp.getRespReportingSecName());
                                    finalRespondentLst.add(localResTemp);
                                } else if (outbox.getSelProjOption() == null) {
                                    localResTemp.setRespReportingDistName(resTemp.getRespReportingProjName());
                                    finalRespondentLst.add(localResTemp);
                                }
                                LOG.info("localResTemp.getRespReportingDistName() " + localResTemp.getRespReportingDistName());
                            } else if (outbox.getSelDistOption() != null && outbox.getSelDistOption().trim().isEmpty()) {
                                localResTemp = resTemp;
                                if (outbox.getSelProjOption() != null && outbox.getSelProjOption().isEmpty()) {
                                    localResTemp.setRespReportingDistName(resTemp.getRespReportingProjName());
                                } else {
                                    localResTemp.setRespReportingDistName(resTemp.getRespReportingSecName());
                                }
                                finalRespondentLst.add(localResTemp);
                            }
                            //else {
//                                    if (resTemp.getAcknowledgedOn() != null && !resTemp.getAcknowledgedOn().equalsIgnoreCase("")) {
//                                        acknowledged++;
//                                    }
//                                    if (resTemp.getRepliedOn() != null && !resTemp.getRepliedOn().equalsIgnoreCase("")) {
//                                        replied++;
//                                    }
//                                    if (resTemp.getApprovedOn() != null && !resTemp.getApprovedOn().equalsIgnoreCase("")) {
//                                        approved++;
//                                    }
//                                    totalAck++;
//                                    totalRep++;
//                                    totalapp++;
//                                    localRes.setRespReportingDistName(resTemp.getRespReportingDistName());
                            // }
                        }
                        LOG.info("Project set " + projectSet);
                        for (String projTemp : projectSet) {
                            int totalAck = 0, totalRep = 0, totalapp = 0;
                            int acknowledged = 0, replied = 0, approved = 0;
                            Respondents localResProj = new Respondents();
                            for (Respondents resTemp : localRespondentLst.get(distTemp)) {
                                LOG.info("projTemp " + projTemp + ">>> " + resTemp.getRespReportingProj());
                                if (projTemp.equalsIgnoreCase(resTemp.getRespReportingProj())) {
                                    if (resTemp.getAcknowledgedOn() != null && !resTemp.getAcknowledgedOn().equalsIgnoreCase("")) {
                                        acknowledged++;
                                    }
                                    if (resTemp.getRepliedOn() != null && !resTemp.getRepliedOn().equalsIgnoreCase("")) {
                                        replied++;
                                    }
                                    if (resTemp.getApprovedOn() != null && !resTemp.getApprovedOn().equalsIgnoreCase("")) {
                                        approved++;
                                    }
                                    totalAck++;
                                    totalRep++;
                                    totalapp++;
                                    localResProj.setRespReportingDistName(resTemp.getRespReportingProjName());
                                }
                                localResProj.setAllTaskCompleted(resTemp.isAllTaskCompleted());
                            }
                            LOG.info(distTemp + " <totalAck++; totalRep++; totalapp++; >>" + totalAck + totalRep + totalapp);
                            localResProj.setRespondentCode(distTemp);
                            localResProj.setTotalAcknowledged(totalAck);
                            localResProj.setTotalReplied(totalRep);
                            localResProj.setTotalApproved(totalapp);
                            localResProj.setAcknowledged(acknowledged);
                            localResProj.setReplied(replied);
                            localResProj.setApproved(approved);
                            finalRespondentLst.add(localResProj);

                        }

                        LOG.info(" localRes.getRespReportingDistName >>" + localRes.getRespReportingDistName());
//                            if (localRes.getRespReportingDistName() != null && !localRes.getRespReportingDistName() .trim().isEmpty()) {
//                                
//                                    localRes.setRespondentCode(distTemp);
//                                    localRes.setTotalAcknowledged(totalAck);
//                                    localRes.setTotalReplied(totalRep);
//                                    localRes.setTotalApproved(totalapp);
//                                    localRes.setAcknowledged(acknowledged);
//                                    localRes.setReplied(replied);
//                                    localRes.setApproved(approved);
//                                    finalRespondentLst.add(localRes);
//                                
//                            }
                    }
                    outbox.setSelectedRespondentsList(finalRespondentLst);
                    //  }
                }
            }
            return RESPONDANTLIST;
        } else if (taskIsSubmit(request)) {

            for (Respondents taskLst : outbox.getSelectedRespondentsList()) {

                System.out.println(taskLst.getNotificationId() + " - Checked :");
            }

            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_OUTBOX_PAGE;

        } else if (taskIsWithdrawNoti(request)) {
            System.out.println(" withdraw Noti id" + outbox.getSelectedNotificationId());

            for (Notification noti : outbox.getNotificationList()) {
                if (noti.getNotificationId() == outbox.getSelectedNotificationId()) {
                    notificationDao.withdrawFullNotification(noti.getRespondentsList(), noti.getNotificationId());
                }
            }
            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_OUTBOX_PAGE;

        }else if (taskIsWithdrawResp(request)) {
            LOG.info(" withdraw Noti id" + outbox.getSelectedNotificationId());

            for (Notification noti : outbox.getNotificationList()) {
                if (noti.getNotificationId() == outbox.getSelectedNotificationId()) {
                     LOG.info(" outbox.getSelectedRespondentId() >> " + outbox.getSelectedRespondentId());
                    notificationDao.withdrawRespondent(noti.getRespondentsList(), noti.getNotificationId(),outbox.getSelectedRespondentId());
                } 
            }
            // notification = notificationDao.insert(notification);
            return REDIRECT_TO_OUTBOX_PAGE;

        } else {
            int targetPage = WebUtils.getTargetPage(request, "_target",
                    currentPage);

            System.out.println("targetPage" + outbox.getSelectedNotification());
            for (Notification noti : outbox.getNotificationList()) {
                if (noti.getNotificationId() == outbox.getSelectedNotificationId()) {
                    //outbox.setSelectedRespondentsList(noti.getRespondentsList());
                    outbox.setSelNotiResponseTime(noti.getNotificationDate() + " to " + notificationDao.addDays(noti.getNotificationDate(), noti.getNeedReplyBefore()));
                    outbox.setSelNotiApproveTime(noti.getNotificationDate() + " to " + notificationDao.addDays(noti.getNotificationDate(), noti.getNeedApprovalBefore()));
                    System.out.println("setSelNotiResponseTime" + outbox.getSelNotiResponseTime());
                    System.out.println("setSelNotiApproveTime" + outbox.getSelNotiApproveTime());
                    if (noti.getRespondentsList().size() > 0) {
                        outbox.setAboutLevel(noti.getRespondentsList().get(0).getRespondentType().toUpperCase());
                    } else {
                        outbox.setAboutLevel(DISTRICT);
                    }
                    System.out.println("getAboutLevel >" + outbox.getAboutLevel().toUpperCase());
                    //outbox.setSelectedDist(notificationDao.getOptionByRespoLevel(noti.getNotificationId(), outbox.getAboutLevel(), locale));
                    Map<String, List<Respondents>> localRespondentLst = notificationDao.getRespondantOnDist(noti.getRespondentsList());
                    outbox.setSelectedDist(notificationDao.getOptionBySet(localRespondentLst.keySet(), locale));

                    List<Respondents> finalRespondentLst = new ArrayList<>();
                    for (String distTemp : localRespondentLst.keySet()) {
                        Respondents localRes = new Respondents();
                        int totalAck = 0, totalRep = 0, totalapp = 0;
                        int acknowledged = 0, replied = 0, approved = 0;
                        LOG.info(distTemp + " <Dist >>" + localRespondentLst.get(distTemp).size());

                        for (Respondents resTemp : localRespondentLst.get(distTemp)) {
                            if (!outbox.getAboutLevel().toUpperCase().equalsIgnoreCase(DISTRICT)) {
                                if (resTemp.getAcknowledgedOn() != null && !resTemp.getAcknowledgedOn().equalsIgnoreCase("")) {
                                    acknowledged++;
                                }
                                if (resTemp.getRepliedOn() != null && !resTemp.getRepliedOn().equalsIgnoreCase("")) {
                                    replied++;
                                }
                                if (resTemp.getApprovedOn() != null && !resTemp.getApprovedOn().equalsIgnoreCase("")) {
                                    approved++;
                                }
                                totalAck++;
                                totalRep++;
                                totalapp++;
                            }else{
                                localRes=resTemp;
                            localRes.setRespondentId(resTemp.getRespondentId());
                        }
                            localRes.setRespReportingDistName(resTemp.getRespReportingDistName());
                            localRes.setAllTaskCompleted(resTemp.isAllTaskCompleted());

                        }
                        LOG.info(distTemp + " <totalAck++; totalRep++; totalapp++; >>" + totalAck + totalRep + totalapp);
                        LOG.info(" localRes.getRespReportingDistName >>" + localRes.getRespReportingDistName());
                        localRes.setRespondentCode(distTemp);
                        localRes.setTotalAcknowledged(totalAck);
                        localRes.setTotalReplied(totalRep);
                        localRes.setTotalApproved(totalapp);
                        localRes.setAcknowledged(acknowledged);
                        localRes.setReplied(replied);
                        localRes.setApproved(approved);
                        finalRespondentLst.add(localRes);
                    }
                    outbox.setSelectedRespondentsList(finalRespondentLst);
                }

            }

            System.out.println("targetPage" + targetPage);
            System.out.println("currentPage" + currentPage);

            return pageForms.get(targetPage);

        }
    }

    @RequestMapping("/getOption")
    @ResponseBody
    public void getOption(HttpServletResponse response, @RequestParam("code") String code,
            @ModelAttribute(OUTBOXBEAN) OutBox outbox
    ) {

        response.setContentType("application/json");
        PrintWriter out = null;
        JSONObject json = null;
        Map<String, String> filterOptions = new HashMap<>();
        try {
            out = response.getWriter();
            Locale locale = LocaleContextHolder.getLocale();
            LOG.info("Notification ID " + outbox.getSelectedNotification());
            String level = "";
            String codeLevel = "";
            String keyLevel = "";

            if (code.contains("D") && outbox.getAboutLevel().equalsIgnoreCase("SECTOR")) {
                level = "PROJECT";
                codeLevel = (code == null || code.length() < 3)
                        ? code : code.substring(code.length() - 3);
            } else if ((code.contains("P") && outbox.getAboutLevel().equalsIgnoreCase("SECTOR"))) {
                level = "DIST";
                codeLevel = (code == null || code.length() < 8)
                        ? code : code.substring(code.length() - 8);
            } else if (code.contains("D") && outbox.getAboutLevel().equalsIgnoreCase("PROJECT")) {
                level = "DIST";
                codeLevel = (code == null || code.length() < 3)
                        ? code : code.substring(code.length() - 3);
            }
            LOG.info("codeLevel " + codeLevel);
            Map<String, String> options = notificationDao.getOptionByRespoLevel(code, level, locale);
            for (String key : options.keySet()) {

                LOG.info("key: " + key + "Value: " + options.get(key) + "Code : " + code);
                if (code.contains("D")) {
                    keyLevel = (key == null || key.length() < 3)
                            ? key : key.substring(key.length() - 3);
                } else if (code.contains("P")) {
                    keyLevel = (key == null || key.length() < 8)
                            ? key : key.substring(key.length() - 8);
                }
                if (keyLevel.contains(codeLevel)) {
                    LOG.info("filter key: " + key + "Value: " + options.get(key));
                    filterOptions.put(key, options.get(key));

                }
            }
            if (code.contains("D")) {
                outbox.setSelectedProject(filterOptions);
            } else if (code.contains("P")) {
                outbox.setSelectedSector(filterOptions);
            }
            json = new JSONObject(filterOptions);
            out.write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    private boolean userClickedPrevious(int currentPage, int targetPage) {
        return targetPage > currentPage;
    }

    private boolean taskIsSubmit(HttpServletRequest request) {
        return request.getParameter("_submit") != null;
    }

    private boolean taskIsFilter(HttpServletRequest request) {
        return request.getParameter("_filter") != null;
    }

    private boolean taskIsBack0(HttpServletRequest request) {
        return request.getParameter("_back0") != null;
    }

    private boolean taskIsBack1(HttpServletRequest request) {
        return request.getParameter("_back1") != null;
    }

    private boolean taskIsWithdrawNoti(HttpServletRequest request) {
        return request.getParameter("_withdrawNoti") != null;
    }
     private boolean taskIsWithdrawResp(HttpServletRequest request) {
        return request.getParameter("_withdrawResp") != null;
    }

}
