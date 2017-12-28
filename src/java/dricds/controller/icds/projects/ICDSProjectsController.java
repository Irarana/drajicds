/*
 * Controller for List of ICDS unit ,Entery and Sanction post details.
 */
package dricds.controller.icds.projects;

import dricds.common.ListPageObject;
import dricds.common.SelectOption;
import dricds.dao.awc.AwcDAO;
import dricds.dao.icds.ICDSDAO;
import dricds.dao.maxcode.MaxCodeDAO;
import dricds.model.icds.ICDSModel;
import dricds.model.login.LoginModel;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ekank
 */
@SessionAttributes({"SessionAttr", "ICDSModelData", "MenuLevel", "DistrictCode"})
@Controller
public class ICDSProjectsController {

    @Autowired
    MaxCodeDAO maxcodeDao;

    @Autowired
    ICDSDAO icdsDAO;

    @Autowired
    AwcDAO awcDao;

    public static Log LOG = LogFactory.getLog(ICDSProjectsController.class);
    public static String menu_leve;

    private static final String ICDSMASTER = "icds/ICDSMaster";
    private static final String ICDSVIEW = "icds/ICDSView";
    private static final String ICDSLIST = "icds/ICDSList";
    private static final String REDIRECT_TO_ICDSLIST = "redirect:/icdsProjectList.htm";
    private static final String REDIRECT_TO_ICDSLIST_WITH_MENULEVEL = "redirect:/icdsProjectList.htm?menuLevel=";
    private static final String ICDSSANCTIONJOININCUMBENCY = "icds/ICDSSancJoinIncumbency";
    private static final String ICDSSANCTIONPOST = "icds/ICDSSanctionPost";
    private static final String ICDSSANCTIONMASTER = "icds/ICDSSanctionMaster";
    private static final String ICDSSANCTIONINCUMBENCYCHART = "icds/ICDSSancIncumbencyChart";
    private static final String REDIRECT_ICDSSANCTIONPOSTLIST = "redirect:/icdsSanctionPostList.htm?";
    private static final String ICDSSANCTIONRELIEVEINCUMBENCY = "icds/ICDSSancRelieveIncumbency";

    private static final String ALL = "ALL";
    private static final String DISTRICT = "DIST";
    private static final String PROJECT = "PROJECT";
    private static final String SECTOR = "SECTOR";
    private static final String MENULEVEL = "menuLevel";
    private static final String SAVE = "Save";
    private static final String CANCEL = "Cancel";
    private static final String DISTRICTNAME = "districtName";
    private static final String DISTRICTCODE = "districtCode";
    private static final String D_DISTRICTCODE = "DistrictCode";
    private static final String BUILDINGOWNER = "BuildingOwner";
    private static final String COMMAND = "command";
    private static final String PROJECTLIST = "ProjectList";
    private static final String DISTRICTLIST = "DistrictList";
    private static final String ICDSPROJECTLIST = "ICDSPorjectList";
    private static final String ICDSMODELDATA = "ICDSModelData";
    private static final String SESSIONATTR = "SessionAttr";
    private static final String SESSIONMENULEVEL = "MenuLevel";
    private static final String ICDSFORMACTION = "icdsFormAction";
    private static final String VIEW = "View";
    private static final String EDIT = "Edit";
    private static final String PROJECTCODE = "projectCode";
    private static final String SUBSTATTIVEPOSTID = "substattivePostId";
    private static final String PROJECTNAME = "projectName";
    private static final String save = "save";
    private static final String other = "other";
    private static final String ICDSSANCTIONLIST = "ICDSSanctionList";
    private static final String SANCTIONPOSTLIST = "SanctionPostList";
    private static final String SELECTEDPROJECTNAME = "SelectedProjectName";
    private static final String SELECTEDPROJECTCODE = "SelectedProjectCode";
    private static final String INCID = "incId";
    private static final String POSTNAME = "postName";
    private static final String POSTCODE = "postCode";
    private static final String TRACKID = "trackId";
    private static final String TIMELIST = "TimeList";
    private static final String INCUMBENCYCHARTLIST = "IncumbencyChartList";
    private static final String HRMSID = "hrmsID";
    private static final String APPLICATIONJSON = "application/json";
    private static final String HRMSIDNAME = "hrmsIdName";
    private static final String SAVEPOST = "savePost";
    private static final String PARAMPROJECTCODE = "&projectCode=";
    private static final String DISTCODE = "distCode";
    private static final String LASTRELEVINGDATE = "lastRelevingDate";
    private static final String SUCCESSMESSAGE = "success.message";
    private static final String TECHNICALISSUE = "technical.issue";
    private static final String MESSAGE = "message";
    private static final String EXTENTION = "extention";
    private static final String SUCCESSMESSAGEEDIT = "success.message.edit";
    private static final String SUCCESSMESSAGECREATE = "success.message.create";
    private static final String SUCCESSMESSAGEJOIN = "success.message.join";
    private static final String SUCCESSMESSAGERELEVING = "success.message.relieving";

    @RequestMapping(value = "icdsProjectList", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showListPage(@ModelAttribute(COMMAND) ICDSModel icdsm,
            @ModelAttribute(SESSIONATTR) LoginModel lm,
            @RequestParam(value = MENULEVEL, required = false) String menuLevel
    ) {
        Locale locale = LocaleContextHolder.getLocale();
        Map<String, String> distMap = new HashMap<String, String>();;
        Map<String, String> projectList = new HashMap<String, String>();
        ModelAndView mv = new ModelAndView(ICDSLIST, COMMAND, icdsm);
        icdsm.setUserlevel(lm.getLevel());
        try {
            icdsm.setLevelAreaCode(lm.getLevelAreaCode());
            if (lm.getLevelAreaCode() != null && lm.getLevelAreaCode().trim().isEmpty()) {
                icdsm.setUserDistCode(ALL);
            } else {
                icdsm.setUserDistCode(lm.getLevelAreaCode());
            }
            LOG.info("Level menu static>>" + ICDSProjectsController.menu_leve);
            LOG.info("Level >>" + lm.getLevel());
            LOG.info("Level menu>>" + menuLevel);
            if ((menuLevel == null || menuLevel.isEmpty())) {
                if ((ICDSProjectsController.menu_leve == null && ICDSProjectsController.menu_leve.trim().isEmpty())) {
                    menuLevel = DISTRICT;
                } else {
                    icdsm.setLevel(ICDSProjectsController.menu_leve);
                }
            } else {
                icdsm.setLevel(menuLevel);
            }
            ICDSProjectsController.menu_leve = icdsm.getLevel();
            mv.addObject(SESSIONMENULEVEL, icdsm.getLevel());
            LOG.info("icdsm.getLevel() >>" + icdsm.getLevel() + "<<<");
            if (lm.getLevel() != null && !lm.getLevel().equals("")) {
                LOG.info("GetLevelAreaCode >>" + lm.getLevelAreaCode() + "<<<");
                if ((lm.getLevelAreaCode() != null
                        && (!lm.getLevelAreaCode().trim().equalsIgnoreCase("") || !lm.getLevelAreaCode().trim().isEmpty()))
                        || (icdsm.getDistrictCode() != null && !icdsm.getDistrictCode().equalsIgnoreCase("ALL"))) {
                    if (!icdsm.getUserDistCode().equalsIgnoreCase("ALL")) {
                        distMap = icdsDAO.getDistrictListOfficeCodeFilter(locale, lm.getLevelAreaCode());
                        LOG.info("distMap >>" + distMap.size());
                        for (String key : distMap.keySet()) {
                            String value = distMap.get(key);
                            LOG.info("OfficeCode >>" + value);
                            icdsm.setDistrictCode(key);
                            icdsm.setDistrictName(value);
                            mv.addObject(DISTRICTNAME, icdsm.getDistrictName());
                            mv.addObject(DISTRICTCODE, icdsm.getDistrictCode());
                            projectList = icdsDAO.getProjectListFilter(locale, icdsm.getDistrictCode());
                        }
                    } else {
                        distMap = icdsDAO.getDistrictList(locale);
                        if (icdsm.getDistrictCode() != null && !icdsm.getDistrictCode().equalsIgnoreCase("ALL")) {
                            projectList = icdsDAO.getProjectListFilter(locale, icdsm.getDistrictCode());
                        }
                    }
                    mv.addObject(PROJECTLIST, projectList);
                    mv.addObject(D_DISTRICTCODE, icdsm.getDistrictCode());
                    LOG.info("districtCode >>" + icdsm.getDistrictCode());
                } else {
                    distMap = icdsDAO.getDistrictList(locale);
                    icdsm.setDistrictCode(ALL);
                    icdsm.setDistrictName(ALL);
                    mv.addObject(D_DISTRICTCODE, ALL);
                    LOG.info("getDistrictCode >>" + icdsm.getDistrictCode());
                }
                mv.addObject(DISTRICTLIST, distMap);
                mv.addObject(PROJECTLIST, projectList);
            }
            mv.addObject(MENULEVEL, icdsm.getLevel());
            ListPageObject lo = icdsDAO.getICDSProjectList(icdsm, locale);
            if (lo != null) {
                mv.addObject(ICDSPROJECTLIST, lo.getDtaList());
            }
            mv.addObject(ICDSMODELDATA, icdsm);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "/deleteICDSProject", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView deleteICDSProject(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsModel) {
        Locale locale = LocaleContextHolder.getLocale();
        ModelAndView mv = null;
        try {
            if (icdsModel.getProjectCode() != null && !icdsModel.getProjectCode().equals("")) {
                icdsDAO.deleteICDSProject(icdsModel.getProjectCode());
                mv = new ModelAndView(REDIRECT_TO_ICDSLIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "/icdsProjectSave", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView saveICDSProjectData(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsm,
            @RequestParam(ICDSFORMACTION) String btnname,
            @ModelAttribute(SESSIONMENULEVEL) String level,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView model = null;
        String error = "";
        try {
            if (btnname.equals(SAVE)) {
                LOG.info("District code" + icdsm.getDistrictCode());
                if (!icdsm.getHrmsIDHOD().isEmpty()) {
                    icdsm.setHoo(true);
                }
                icdsm.setHoo(true);
                if (icdsm.getLevel() == null || icdsm.getLevel().isEmpty()) {
                    icdsm.setLevel(level);
                }
                LOG.info("Level" + icdsm.getLevel());
                icdsDAO.addICDSProjectData(icdsm, "", "");
            }

            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGECREATE);
            redirectAttrs.addFlashAttribute(EXTENTION, ((locale != null && locale.toString().equalsIgnoreCase("hindi")) ? icdsm.getProjectNameHindi() : icdsm.getProjectName()));

            model = new ModelAndView(REDIRECT_TO_ICDSLIST_WITH_MENULEVEL + icdsm.getLevel());
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSMASTER, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }

        if (btnname.equals(CANCEL)) {
            return model;
        }
        return model;
    }

    @RequestMapping(value = "/icdsProjectEditSave", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView saveICDSProjectEditData(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsm, @RequestParam(ICDSFORMACTION) String btnname,
            @ModelAttribute(SESSIONMENULEVEL) String menuLevel,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");
        //  ModelAndView model = new ModelAndView(REDIRECT_TO_ICDSLIST_WITH_MENULEVEL + menuLevel);
        ModelAndView model = null;
        try {
            if (btnname.equals(SAVE)) {
                LOG.info("District code" + icdsm.getDistrictCode());
                LOG.info("Project code" + icdsm.getProjectCode());
                LOG.info("SubstattivePostId " + icdsm.getSubstattivePostId());
                LOG.info("level " + icdsm.getLevel());
                LOG.info("level " + menuLevel);

                if (!icdsm.getHrmsIDHOD().isEmpty()) {
                    icdsm.setHoo(true);
                }
                icdsm.setHoo(true);
                icdsDAO.addICDSProjectEditData(icdsm, icdsm.getProjectCode());
            }
            if (btnname.equals(CANCEL)) {
                return model;
            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGEEDIT);
            redirectAttrs.addFlashAttribute(EXTENTION, ((locale != null && locale.toString().equalsIgnoreCase("hindi")) ? icdsm.getProjectNameHindi() : icdsm.getProjectName()));
            model = new ModelAndView(REDIRECT_TO_ICDSLIST_WITH_MENULEVEL + icdsm.getLevel());

        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSMASTER, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }
        return model;
    }

    @RequestMapping(value = "icdsProjectEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView entryPage(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsModel,
            @ModelAttribute(DISTRICTCODE) String DistrictCode,
            @ModelAttribute(SESSIONATTR) LoginModel lm) {
        Locale locale = LocaleContextHolder.getLocale();

        ModelAndView mv = new ModelAndView(ICDSMASTER, COMMAND, new ICDSModel());
        try {
            Map<String, String> districtList = null;
            Map<String, String> projectList = null;
            if (lm.getLevelAreaCode() != null && ! !lm.getLevelAreaCode().trim().isEmpty() && icdsModel.getDistrictCode() != null && !icdsModel.getDistrictCode().equalsIgnoreCase("")) {
                districtList = icdsDAO.getDistrictListFilter(locale, icdsModel.getDistrictCode());
                projectList = icdsDAO.getProjectListFilter(locale, icdsModel.getDistrictCode());
            } else if (lm.getLevelAreaCode() != null && !lm.getLevelAreaCode().trim().isEmpty() && DistrictCode != null && !DistrictCode.isEmpty()) {
                icdsModel.setDistrictCode(DistrictCode);
                districtList = icdsDAO.getDistrictListFilter(locale, DistrictCode);
                projectList = icdsDAO.getProjectListFilter(locale, DistrictCode);
            } else {
                districtList = icdsDAO.getDistrictList(locale);
                projectList = icdsDAO.getProjectList(locale);
            }
            mv.addObject(DISTRICTLIST, districtList);
            Map<String, String> buildingOwnerList = new HashMap<String, String>();
            ArrayList buildingOwner = awcDao.getBuilderOwner(locale);
            for (int i = 0; i < buildingOwner.size(); i++) {
                SelectOption so = (SelectOption) buildingOwner.get(i);
                buildingOwnerList.put(so.getValue(), so.getLabel());
            }
            mv.addObject(DISTRICTNAME, icdsModel.getDistrictName());
            mv.addObject(DISTRICTCODE, icdsModel.getDistrictCode());
            mv.addObject(BUILDINGOWNER, buildingOwnerList);
            mv.addObject(MENULEVEL, icdsModel.getLevel());
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsProjectView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewICDSProjectData(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsModel) {
        ModelAndView mv = null;
        Locale locale = LocaleContextHolder.getLocale();
        try {
            if (icdsModel.getProjectCode() != null && !icdsModel.getProjectCode().equals("")) {
                ICDSModel icdsm = icdsDAO.viewIcdsProjectData(icdsModel.getProjectCode(), locale, VIEW);

                if (icdsModel.getLevel().equalsIgnoreCase(PROJECT)) {
                    icdsm.setDistrictCode(icdsModel.getReportingOffice());
                    Map<String, String> selectedDistList = icdsDAO.getDistrictListFilter(locale, icdsm.getDistrictCode());
                    icdsm.setDistrictName(selectedDistList.get(icdsm.getDistrictCode()));
                } else if (icdsModel.getLevel().equalsIgnoreCase(SECTOR)) {
                    Map<String, String> selectedDistList = icdsDAO.getDistrictFromProject(locale, icdsModel.getReportingOffice());
                    LOG.info("selectedDistList " + selectedDistList);
                    for (String key : selectedDistList.keySet()) {
                        String value = selectedDistList.get(key);
                        icdsm.setDistrictCode(key);
                        icdsm.setDistrictName(value);
                    }
                    Map<String, String> selectedProjectList = icdsDAO.getProjectListFilter(locale, icdsm.getDistrictCode());

                    icdsm.setSelectedProjectId(icdsModel.getReportingOffice());

                    icdsm.setSelectedProjectName(selectedProjectList.get(icdsModel.getReportingOffice()));
                }
                icdsm.setProjectCode(icdsModel.getProjectCode());
                icdsm.setProjectName(icdsModel.getProjectName());
                icdsm.setLevel(icdsModel.getLevel());
                Map<String, String> buildingOwnerList = new HashMap<String, String>();
                ArrayList buildingOwner = awcDao.getBuilderOwner(locale);
                for (int i = 0; i < buildingOwner.size(); i++) {
                    SelectOption so = (SelectOption) buildingOwner.get(i);
                    buildingOwnerList.put(so.getValue(), so.getLabel());
                    if (so.getValue().equalsIgnoreCase(icdsm.getBuildingOwner())) {
                        icdsm.setBuildingOwnerName(so.getLabel());
                    }
                }
                mv = new ModelAndView(ICDSVIEW, COMMAND, icdsm);
                Map<String, String> districtList = null;
                Map<String, String> projectList = null;
                LOG.info("icdsm.getDistrictCode()  " + icdsm.getDistrictCode());
                if (icdsm.getDistrictCode() != null && !icdsm.getDistrictCode().equalsIgnoreCase("")) {
                    districtList = icdsDAO.getDistrictList(locale);
                    //districtList = icdsDAO.getDistrictListFilter(locale, icdsModel.getDistrictCode());
                    projectList = icdsDAO.getProjectListFilter(locale, icdsm.getDistrictCode());
                } else {
                    districtList = icdsDAO.getDistrictList(locale);
                    projectList = icdsDAO.getProjectList(locale);
                }
                LOG.info("districtList List " + districtList);
                LOG.info("Project List " + projectList);
                LOG.info("icdsm.getReportingOffice() " + icdsm.getReportingOffice());
                mv.addObject(DISTRICTLIST, districtList);
                mv.addObject(PROJECTLIST, projectList);
                mv.addObject(DISTRICTNAME, icdsm.getDistrictName());
                mv.addObject(DISTRICTCODE, icdsm.getDistrictCode());
                mv.addObject(MENULEVEL, icdsModel.getLevel());
                mv.addObject(BUILDINGOWNER, buildingOwnerList);
            } else {
                mv = new ModelAndView(REDIRECT_TO_ICDSLIST_WITH_MENULEVEL + icdsModel.getLevel());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;

    }

    @RequestMapping(value = "icdsProjectEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView editICDSProjectData(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsModel) {
        ModelAndView mv = null;
        Locale locale = LocaleContextHolder.getLocale();
        LOG.info("getDistrictCode >> " + icdsModel.getDistrictCode());
        LOG.info("getDistrictName >> " + icdsModel.getDistrictName());
        LOG.info("getProjectCode >> " + icdsModel.getProjectCode());
        LOG.info("getProjectName >> " + icdsModel.getProjectName());
        LOG.info("Level** >> " + icdsModel.getLevel());
        try {
            if (icdsModel.getProjectCode() != null && !icdsModel.getProjectCode().equals("")) {
                ICDSModel icdsm = icdsDAO.viewIcdsProjectData(icdsModel.getProjectCode(), locale, EDIT);

                if (icdsModel.getLevel().equalsIgnoreCase(PROJECT)) {
                    icdsm.setDistrictCode(icdsModel.getReportingOffice());
                } else if (icdsModel.getLevel().equalsIgnoreCase(SECTOR)) {
                    Map<String, String> selectedDistList = icdsDAO.getDistrictFromProject(locale, icdsModel.getReportingOffice());
                    LOG.info("selectedDistList " + selectedDistList);
                    for (String key : selectedDistList.keySet()) {
                        String value = selectedDistList.get(key);
                        icdsm.setDistrictCode(key);
                        icdsm.setDistrictName(value);
                    }
                    icdsm.setSelectedProjectId(icdsModel.getReportingOffice());
                }
                icdsm.setProjectCode(icdsModel.getProjectCode());
                icdsm.setProjectName(icdsModel.getProjectName());
                icdsm.setLevel(icdsModel.getLevel());
                mv = new ModelAndView(ICDSMASTER, COMMAND, icdsm);
                Map<String, String> buildingOwnerList = new HashMap<String, String>();
                ArrayList buildingOwner = awcDao.getBuilderOwner(locale);
                for (int i = 0; i < buildingOwner.size(); i++) {
                    SelectOption so = (SelectOption) buildingOwner.get(i);
                    buildingOwnerList.put(so.getValue(), so.getLabel());
                }
                Map<String, String> districtList = null;
                Map<String, String> projectList = null;
                LOG.info("icdsm.getDistrictCode()  " + icdsm.getDistrictCode());
                if (icdsm.getDistrictCode() != null && !icdsm.getDistrictCode().equalsIgnoreCase("")) {
                    districtList = icdsDAO.getDistrictList(locale);
                    projectList = icdsDAO.getProjectListFilter(locale, icdsm.getDistrictCode());
                } else {
                    districtList = icdsDAO.getDistrictList(locale);
                    projectList = icdsDAO.getProjectList(locale);
                }
                LOG.info("districtList List " + districtList);
                LOG.info("Project List " + projectList);
                LOG.info("icdsm.getReportingOffice() " + icdsm.getReportingOffice());
                mv.addObject(DISTRICTLIST, districtList);
                mv.addObject(PROJECTLIST, projectList);
                mv.addObject(BUILDINGOWNER, buildingOwnerList);
                mv.addObject(PROJECTCODE, icdsModel.getProjectCode());
                mv.addObject(DISTRICTNAME, icdsm.getDistrictName());
                mv.addObject(DISTRICTCODE, icdsm.getDistrictCode());
                mv.addObject(SUBSTATTIVEPOSTID, icdsm.getSubstattivePostId());
                mv.addObject(MENULEVEL, icdsm.getLevel());
            } else {
                mv = new ModelAndView(REDIRECT_TO_ICDSLIST_WITH_MENULEVEL + icdsModel.getLevel());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;

    }

    @RequestMapping(value = "icdsSanctionPostList", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView sanctionPostList(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsm,
            @ModelAttribute(SESSIONATTR) LoginModel lm,
            @ModelAttribute(ICDSMODELDATA) ICDSModel icdsModel,
            @ModelAttribute(D_DISTRICTCODE) String districtCode,
            @ModelAttribute(SESSIONMENULEVEL) String level) {
        Locale locale = LocaleContextHolder.getLocale();
        ModelAndView mv = new ModelAndView(ICDSSANCTIONPOST, COMMAND, icdsm);
        icdsm.setUserlevel(lm.getLevel());
        LOG.info("Level >>" + lm.getLevel());
        LOG.info("GetLevelAreaCode >>" + lm.getLevelAreaCode());
        LOG.info("getDistrictCode >>" + icdsm.getDistrictCode());
        LOG.info("trackID >>" + icdsm.getTrackID());
        LOG.info("ProjectCode >>" + icdsm.getProjectCode());
        LOG.info("data getSelectedProjectId >>" + icdsm.getSelectedProjectId());
        LOG.info("getOfficeNameHindi >>" + icdsm.getOfficeNameHindi());
        LOG.info("DistrictCode >>" + districtCode);
        LOG.info("DistrictCode in Session bean >>" + icdsModel.getDistrictCode());
        Map<String, String> districtList = new HashMap<>();
        Map<String, String> projectList = new HashMap<>();
        Map<String, String> selectedProjectList = new HashMap<>();
        try {
            if (icdsm.getDistrictCode() == null) {

                icdsm = icdsModel;
            }
            if (level != null && level.equalsIgnoreCase(DISTRICT)) {
                districtList = icdsDAO.getDistrictListFilter(locale, icdsm.getProjectCode());
                projectList = icdsDAO.getProjectListFilter(locale, icdsm.getProjectCode());
            } else if (level != null && level.equalsIgnoreCase(PROJECT)) {
                districtList = icdsDAO.getDistrictFromProject(locale, icdsm.getProjectCode());
                if (districtList != null && !districtList.isEmpty()) {
                    for (String key : districtList.keySet()) {
                        projectList = icdsDAO.getProjectListFilter(locale, key);
                    }
                }
            } else if (level != null && level.equalsIgnoreCase(SECTOR)) {
                projectList = icdsDAO.getDistrictFromProject(locale, icdsm.getProjectCode());
                if (projectList != null && !projectList.isEmpty()) {
                    for (String key : selectedProjectList.keySet()) {
                        districtList = icdsDAO.getDistrictFromProject(locale, key);
                    }
                }
            }

            mv.addObject(DISTRICTLIST, districtList);
            mv.addObject(PROJECTLIST, projectList);
            LOG.info("districtList >>" + districtList);
            LOG.info("projectList >>" + projectList);
            LOG.info("icdsm.getReportingOffice() >>" + icdsm.getReportingOffice());
            if (districtList.containsKey(icdsm.getDistrictCode())) {
                icdsm.setDistrictName(districtList.get(icdsm.getDistrictCode()));
                mv.addObject(DISTRICTNAME, icdsm.getDistrictName());
            }
            if (icdsm.getReportingOffice() != null && projectList.containsKey(icdsm.getReportingOffice())) {
                icdsm.setSelectedProjectName(projectList.get(icdsm.getReportingOffice()));
                icdsm.setSelectedProjectId(icdsm.getReportingOffice());
                mv.addObject(SELECTEDPROJECTNAME, icdsm.getSelectedProjectName());
                mv.addObject(SELECTEDPROJECTCODE, icdsm.getReportingOffice());
            } else if (icdsm.getProjectCode() != null) {
                selectedProjectList = icdsDAO.getDistrictFromProject(locale, icdsm.getProjectCode());
                if (selectedProjectList.size() > 0) {
                    for (String key : selectedProjectList.keySet()) {
                        icdsm.setSelectedProjectName(selectedProjectList.get(key));
                        icdsm.setSelectedProjectId(key);
                        icdsm.setReportingOffice(key);
                        mv.addObject(SELECTEDPROJECTNAME, icdsm.getSelectedProjectName());
                        mv.addObject(SELECTEDPROJECTCODE, icdsm.getSelectedProjectId());
                    }
                }
                //else if(projectList.size()>0) {
//               
//            }

            }
            LOG.info(">>>>>>getSelectedProjectId<<<<< " + icdsm.getSelectedProjectId());
            if (icdsm.getSelectedProjectId() != null && !icdsm.getSelectedProjectId().trim().isEmpty() && level.equalsIgnoreCase(SECTOR)) {

                Map<String, String> sectorList = icdsDAO.getSectorListFilter(locale, icdsm.getSelectedProjectId());
                LOG.info("projName >>>>>>sectorList<<<<< " + sectorList);
                LOG.info("projName >>>>>>sectorList Project Name<<<<< " + sectorList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTNAME, sectorList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTCODE, icdsm.getProjectCode());

            } else if (icdsm.getProjectCode() != null && !projectList.isEmpty() && projectList.containsKey(icdsm.getProjectCode())) {
                LOG.info("projName >>>>>>projectList<<<<< " + projectList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTNAME, projectList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTCODE, icdsm.getProjectCode());
            } else if (icdsm.getProjectCode() != null && !districtList.isEmpty() && districtList.containsKey(icdsm.getProjectCode())) {
                LOG.info("projName >>>>>>districtList<<<<< " + districtList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTNAME, districtList.get(icdsm.getProjectCode()));
                mv.addObject(PROJECTCODE, icdsm.getProjectCode());
            } else {
                LOG.info("projName >>>>>><<<<<" + icdsm.getProjectName());
                mv.addObject(PROJECTNAME, icdsm.getProjectName());
                mv.addObject(PROJECTCODE, icdsm.getProjectCode());
            }
            LOG.info("projName >>>>>>***" + icdsm.getProjectName());
            LOG.info("projCode >>>>>>>>" + icdsm.getProjectCode());

            ///get count
            List li = new ArrayList();
            int count = 1;
            ListPageObject lo = new ListPageObject();
            int postCount = icdsDAO.getCountPost(level);
            int subsPostCount = icdsDAO.getSubsCountPost(icdsm.getProjectCode());
            LOG.info("postCount > subsPostCount - " + postCount + " >" + subsPostCount);
            LOG.info("postCount > subsPostCount - " + (postCount > subsPostCount));
            if (postCount != 0) {
                if (postCount > subsPostCount) {
                    Map<String, ICDSModel> mapPost = icdsDAO.getMasterPlanPost(level, icdsm.getProjectCode(), locale);

                    for (String key : mapPost.keySet()) {

                        ICDSModel value = mapPost.get(key);
                        if (value.getSanctionPosition() > 1) {

                            int strength = value.getSanctionPosition();
                            while (strength > 0) {
                                value.setSanctionPostNameDis(value.getSanctionPostName() + "(Post " + strength + ")");
                                value.setTrackID(count);
                                li.add(value);
                                strength--;
                                count++;
                            }
                        } else {
                            value.setSanctionPostNameDis(value.getSanctionPostName());
                            value.setTrackID(count);
                            li.add(value);
                            count++;
                        }
                    }
                    lo.setDtaList(li);
                    mv.addObject(save, true);
                    mv.addObject(other, false);
                } else {
                    Map<String, ICDSModel> savedPost = icdsDAO.getSavedPost(level, icdsm.getProjectCode(), locale);
                    LOG.info("savedPost " + savedPost.size());
                    count = 1;
                    for (String key : savedPost.keySet()) {
                        ICDSModel value = savedPost.get(key);
                        value.setTrackID(count);
                        li.add(value);
                        count++;
                    }
                    lo.setDtaList(li);
                    mv.addObject(save, false);
                    mv.addObject(other, true);
                }
            }
            if (lo != null) {
                mv.addObject(ICDSSANCTIONLIST, lo.getDtaList());
                icdsm.setDtaList(lo.getDtaList());
            }
            mv.addObject(ICDSMODELDATA, icdsm);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsSanctionEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView entrySanctionPage(@RequestParam(value = PROJECTCODE, required = false) String projectCode, @RequestParam(value = PROJECTNAME, required = false) String projectName) {
        Locale locale = LocaleContextHolder.getLocale();

        ModelAndView mv = new ModelAndView(ICDSSANCTIONMASTER, COMMAND, new ICDSModel());
        try {
            Map<String, String> districtList = icdsDAO.getDistrictList(locale);
            mv.addObject("DistrictList", districtList);
            Map<String, String> defaultPostList = icdsDAO.getDefaultPost(locale);
            mv.addObject(SANCTIONPOSTLIST, defaultPostList);
            mv.addObject(PROJECTNAME, projectName);
            mv.addObject(PROJECTCODE, projectCode);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsEditSanctionEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView editSanctionPage(@RequestParam(value = PROJECTNAME, required = false) String projectName,
            @RequestParam(value = PROJECTCODE, required = false) String projectCode,
            @RequestParam(value = INCID, required = false) String incId,
            @RequestParam(value = POSTNAME, required = false) String postName,
            @RequestParam(value = POSTCODE, required = false) int postCode,
            @RequestParam(value = TRACKID, required = false) int trackId,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im) {
        Locale locale = LocaleContextHolder.getLocale();
        ICDSModel im1 = null;
        String substattivePostId = null;
        List lst = im.getDtaList();
        LOG.info("list  " + lst.size());
        LOG.info("sanc post  " + postCode);
        boolean breakLoop = false;
        ModelAndView mv = null;
        try {
            for (int i = 0; i < lst.size(); i++) {
                if (!breakLoop) {
                    im1 = (ICDSModel) lst.get(i);
                    if (im1.getTrackID() == trackId) {
                        breakLoop = true;
                        substattivePostId = im1.getSubstattivePostId();
                        LOG.info("getSubstattivePostId  " + im1.getSubstattivePostId() + " : " + incId);
                        LOG.info("Inc Id  " + im1.getIncumbencyId() + " : " + incId);
                    }
                }
            }
            if (!breakLoop) {
                im1 = new ICDSModel();
            }

            mv = new ModelAndView(ICDSSANCTIONMASTER, COMMAND, im1);

            Map<String, String> districtList = icdsDAO.getDistrictList(locale);
            mv.addObject(DISTRICTLIST, districtList);
            Map<String, String> defaultPostList = icdsDAO.getDefaultPost(locale);

            mv.addObject(SANCTIONPOSTLIST, defaultPostList);
            mv.addObject(SUBSTATTIVEPOSTID, substattivePostId);
            mv.addObject(INCID, incId);
            mv.addObject(PROJECTNAME, projectName);
            mv.addObject(PROJECTCODE, projectCode);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsJoinIncumbency", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView joinIncumbencyPage(@RequestParam(value = PROJECTNAME, required = false) String projectName,
            @RequestParam(value = PROJECTCODE, required = false) String projectCode,
            @RequestParam(value = INCID, required = false) String incId,
            @RequestParam(value = POSTNAME, required = false) String postName,
            @RequestParam(value = POSTCODE, required = false) String postCode,
            @RequestParam(value = TRACKID, required = false) int trackId,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im) {
        Locale locale = LocaleContextHolder.getLocale();
        ICDSModel im1 = null;
        ModelAndView mv = null;
        List lst = im.getDtaList();
        LOG.info("list  " + lst.size());
        LOG.info("sanc post  " + postCode);
        LOG.info("trackId  " + trackId);
        try {
            boolean breakLoop = false;
            for (int i = 0; i < lst.size(); i++) {
                if (!breakLoop) {
                    im1 = (ICDSModel) lst.get(i);
                    if (incId != null && !incId.equalsIgnoreCase("0")) {
                        if (im1.getIncumbencyId() != null && im1.getIncumbencyId().equalsIgnoreCase(incId)) {
                            breakLoop = true;
                            LOG.info("Inc Id  " + im1.getIncumbencyId() + " : " + incId);
                        } else if (im1.getSanctionPostCode() == postCode) {
                            breakLoop = true;
                            LOG.info(postCode + " =sanc post  =" + im1.getSanctionPostCode());
                        }
                    } else if (trackId > 0 && im1.getTrackID() > 0 && im1.getTrackID() == trackId) {
                        breakLoop = true;
                        LOG.info("trackId  " + im1.getTrackID() + " : " + trackId);
                    }
                }
            }

            if (!breakLoop) {
                im1 = new ICDSModel();
            } else if (incId != null && !incId.equalsIgnoreCase("0")) {
                im1 = icdsDAO.getIncumbencyChartById(Integer.parseInt(im1.getIncumbencyId()));
            }
            ICDSModel lastIncumbency = icdsDAO.getLastIncumbencyBySPId(im1.getSubstattivePostId(), locale);

            mv = new ModelAndView(ICDSSANCTIONJOININCUMBENCY, COMMAND, im1);
            Map<String, String> defaultTimeList = icdsDAO.getDefaultTime(locale);
            LOG.info("defaultTimeList " + defaultTimeList);
            mv.addObject(TIMELIST, defaultTimeList);
            mv.addObject(INCID, incId);
            mv.addObject(POSTNAME, postName);
            mv.addObject(POSTCODE, postCode);
            mv.addObject(PROJECTCODE, projectCode);
            mv.addObject(PROJECTNAME, projectName);
            mv.addObject(TRACKID, im1.getTrackID());
            mv.addObject(LASTRELEVINGDATE, lastIncumbency.getRelevingDate());
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsRelieveIncumbency", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView relieveIncumbencyPage(@RequestParam(value = PROJECTNAME, required = false) String projectName,
            @RequestParam(value = PROJECTCODE, required = false) String projectCode,
            @RequestParam(value = INCID, required = false) String incId,
            @RequestParam(value = POSTNAME, required = false) String postName,
            @RequestParam(value = POSTCODE, required = false) String postCode,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im) {

        Locale locale = LocaleContextHolder.getLocale();
        ICDSModel im1 = null;
        ModelAndView mv = null;
        try {
            List lst = im.getDtaList();
            LOG.info("list  " + lst.size());
            LOG.info("sanc post  " + postCode);
            boolean breakLoop = false;
            for (int i = 0; i < lst.size(); i++) {
                if (!breakLoop) {
                    im1 = (ICDSModel) lst.get(i);
                    if (im1.getIncumbencyId() != null && im1.getIncumbencyId().equalsIgnoreCase(incId)) {
                        breakLoop = true;
                        LOG.info("Inc Id  " + im1.getIncumbencyId() + " : " + incId);
                    } else if (im1.getSanctionPostCode() == postCode) {
                        breakLoop = true;
                        LOG.info(postCode + " = sanc post=   " + im1.getSanctionPostCode());
                    }
                }
            }
            if (!breakLoop) {
                im1 = new ICDSModel();
            }
            mv = new ModelAndView(ICDSSANCTIONRELIEVEINCUMBENCY, COMMAND, im1);
            Map<String, String> defaultTimeList = icdsDAO.getDefaultTime(locale);
            LOG.info("defaultTimeList " + defaultTimeList);
            mv.addObject(TIMELIST, defaultTimeList);
            mv.addObject(INCID, incId);
            mv.addObject(POSTNAME, postName);
            mv.addObject(POSTCODE, postCode);
            mv.addObject(PROJECTCODE, projectCode);
            mv.addObject(PROJECTNAME, projectName);
            mv.addObject(TRACKID, im1.getTrackID());
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "icdsIncumbencyChart", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView incumbencyChart(@RequestParam(value = PROJECTNAME, required = false) String projectName,
            @RequestParam(value = PROJECTCODE, required = false) String projectCode,
            @RequestParam(value = INCID, required = false) String incId,
            @RequestParam(value = POSTNAME, required = false) String postName,
            @RequestParam(value = POSTCODE, required = false) String postCode,
            @RequestParam(value = SUBSTATTIVEPOSTID, required = false) String substattivePostId,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im) {
        Locale locale = LocaleContextHolder.getLocale();
        ModelAndView mv = new ModelAndView(ICDSSANCTIONINCUMBENCYCHART, COMMAND, new ICDSModel());
        try {
            LOG.info("Inc Id  " + incId);
            LOG.info("projectName  " + projectName);
            LOG.info("projectCode  " + projectCode);
            LOG.info("postCode  " + postCode);
            LOG.info("substattivePostId  " + substattivePostId);
            ListPageObject lo = icdsDAO.getIncumbencyChartList(substattivePostId, postCode, locale);
            if (lo != null) {
                LOG.info("Size >>>  " + lo.getDtaList().size());
                mv.addObject(INCUMBENCYCHARTLIST, lo.getDtaList());
                List lst = lo.getDtaList();
                for (int i = 0; i < lo.getDtaList().size(); i++) {
                    ICDSModel im11 = (ICDSModel) lst.get(i);
                    LOG.info("NAme >>>  >>>  " + im11.getStaffingName());
                }
            }
            mv.addObject(INCID, incId);
            mv.addObject(POSTNAME, postName);
            mv.addObject(POSTCODE, postCode);
            mv.addObject(PROJECTCODE, projectCode);
            mv.addObject(PROJECTNAME, projectName);
            mv.addObject(TRACKID, im.getTrackID());
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject(MESSAGE, TECHNICALISSUE);
        }
        return mv;
    }

    @RequestMapping(value = "/getHRMSIDController", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getHRMSName(HttpServletRequest request, HttpServletResponse response, @RequestParam(HRMSID) String hrmsID) {
        //    ICDSModel getHRMSName(@RequestParam(HRMSID) String hrmsID,BindingResult result) {
        String hrmsIdName = "";
        response.setContentType(APPLICATIONJSON);
        response.setCharacterEncoding("UTF-8");
        JSONObject obj = new JSONObject();
        ICDSModel im = new ICDSModel();

        LOG.info(" >>>>>> ***hrmsID**  >>>> " + hrmsID);
        try {

            hrmsIdName = icdsDAO.getHrmsIdDetails(hrmsID);
            LOG.info(" >>>>>> ***hrmsIdName**  >>>> " + hrmsIdName);
            obj.put(HRMSIDNAME, hrmsIdName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        im.setHodName(hrmsIdName);
        //return im;
        return obj.toString();
    }

    @RequestMapping(value = "icdsSanSave", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView saveICDSSancPostSaveData(
            @ModelAttribute(COMMAND) ICDSModel icdsm,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im,
            RedirectAttributes redirectAttrs) {
        Locale locale = LocaleContextHolder.getLocale();
        List lst1 = im.getDtaList();
        ModelAndView model = null;
        LOG.info("List Sizee ***** " + lst1.size());
        LOG.info("List Sizee ***** " + lst1);
        try {
            for (int i = 0; i < lst1.size(); i++) {
                ICDSModel im1 = (ICDSModel) lst1.get(i);
                if (im1.getIncumbencyId() == null) {
                    LOG.info("im1 ***** " + im1.getLevel());
                    icdsDAO.addICDSProjectData(im1, icdsm.getProjectCode(), SAVEPOST);
                }
            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGECREATE);
            model = new ModelAndView(redirectSanctionPostListURL(im.getProjectName(), im.getProjectCode()));
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSSANCTIONPOST, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }
        return new ModelAndView(redirectSanctionPostListURL(im.getProjectName(), im.getProjectCode()));
    }

    @RequestMapping(value = "/postSave")
    public ModelAndView postSaveData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(COMMAND) ICDSModel icdsm,
            @RequestParam(ICDSFORMACTION) String btnname,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView model = null;
        try {
            if (btnname.equalsIgnoreCase(SAVE)) {
                LOG.info("District code ***** " + icdsm.getProjectCode());
                LOG.info("Project Code ***** " + icdsm.getSanctionOrderNo());
                LOG.info("Project Code ***** " + icdsm.getSanctionDate());
                LOG.info("Project Code ***** " + icdsm.getSubject());
                icdsDAO.addICDSProjectData(icdsm, icdsm.getProjectCode(), "");

            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGECREATE);
            redirectAttrs.addFlashAttribute(EXTENTION, ((locale != null && locale.toString().equalsIgnoreCase("hindi")) ? icdsm.getSanctionPostNameHindi() : icdsm.getSanctionPostName()));
            new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSSANCTIONMASTER, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }

        return model;
    }

    @RequestMapping(value = "/postEditSave")
    public ModelAndView postEditSaveData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(COMMAND) ICDSModel icdsm,
            @RequestParam(ICDSFORMACTION) String btnname,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView model = null;
        try {
            if (btnname.equalsIgnoreCase(SAVE)) {
                LOG.info("District code ***** " + icdsm.getProjectCode());
                LOG.info("Project Code ***** " + icdsm.getSanctionOrderNo());
                LOG.info("Project Code ***** " + icdsm.getSanctionDate());
                LOG.info("Project Code ***** " + icdsm.getSubject());
                icdsDAO.updateICDSProjectEditData(icdsm, icdsm.getProjectCode());

            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGEEDIT);
            redirectAttrs.addFlashAttribute(EXTENTION, icdsm.getSanctionPostNameDis());

            model = new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSSANCTIONMASTER, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }
        return new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
    }

    @RequestMapping(value = "/joinSave", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView joinSaveData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(COMMAND) ICDSModel icdsm,
            @RequestParam(ICDSFORMACTION) String btnname, @ModelAttribute(ICDSMODELDATA) ICDSModel im,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");
        LOG.info("btnname ***** " + btnname);
        LOG.info("District code ***** " + icdsm.getProjectCode());
        List lst = im.getDtaList();
        LOG.info("List Sizee ***** " + lst.size());
        LOG.info("List Sizee ***** " + lst);
        LOG.info("Project Code ***** " + icdsm.getSanctionOrderNo());
        LOG.info("Project Code ***** " + icdsm.getSanctionDate());
        LOG.info("staffing Code ***** " + icdsm.getStaffingName());
        LOG.info("Project Code ***** " + icdsm.getIncumbencyId());
        LOG.info("Project Code ***** " + icdsm.getSanctionPostCode());
        LOG.info("track id Code ***** " + icdsm.getTrackID());
        boolean breakLoop = false;
        ICDSModel im1 = null;
        ModelAndView model = null;
        try {
            for (int i = 0; i < lst.size(); i++) {
                if (!breakLoop) {
                    im1 = (ICDSModel) lst.get(i);
                    if (im1.getTrackID() == icdsm.getTrackID()) {
                        LOG.info("im.getTrackID()==icdsm.getTrackID() ***** " + im.getTrackID() + "==" + icdsm.getTrackID());
                        breakLoop = true;
                    }
                }
            }
            im1.setHodName(icdsm.getStaffingName());
            im1.setHodNameHindi(icdsm.getStaffingNameHindi());
            im1.setJoinedDate(icdsm.getJoinedDate());
            im1.setJoiningTime(icdsm.getJoiningTime());
            im1.setHrmsIDHOD(icdsm.getHrmsIDHOD());
            LOG.info("icdsm.getIncumbencyId() " + icdsm.getIncumbencyId());
            LOG.info("icdsm.getJoiningTime() " + icdsm.getJoiningTime());
            LOG.info("icdsm.getJoinedDate() " + icdsm.getJoinedDate());

            if (icdsm.getIncumbencyId() != null && !icdsm.getIncumbencyId().equalsIgnoreCase("0")) {
                im1.setIncumbencyId(icdsm.getIncumbencyId());
                icdsDAO.updateIncumbency(im1);
            } else {
                im1.setInPosition(true);
                icdsDAO.addIncumbency(im1);
            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGEJOIN);
            redirectAttrs.addFlashAttribute(EXTENTION, ((locale != null && locale.toString().equalsIgnoreCase("hindi")) ? im1.getStaffingNameHindi() : im1.getStaffingName()));
            model = new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSSANCTIONJOININCUMBENCY, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }
        return new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
    }

    @RequestMapping(value = "/relieveSave", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView relieveSaveData(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(COMMAND) ICDSModel icdsm,
            @RequestParam(ICDSFORMACTION) String btnname,
            @ModelAttribute(ICDSMODELDATA) ICDSModel im,
            RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
        Locale locale = LocaleContextHolder.getLocale();
        response.setContentType("text/html; charset=UTF-8");

        LOG.info("District code ***** " + icdsm.getProjectCode());
        List lst = im.getDtaList();
        LOG.info("List Sizee ***** " + lst.size());
        LOG.info("List Sizee ***** " + lst);
        LOG.info("Project Code ***** " + icdsm.getSanctionOrderNo());
        LOG.info("Project Code ***** " + icdsm.getSanctionDate());
        LOG.info("staffing Code ***** " + icdsm.getStaffingName());
        LOG.info("Project Code ***** " + icdsm.getIncumbencyId());
        LOG.info("Project Code ***** " + icdsm.getSanctionPostCode());
        LOG.info("track id Code ***** " + icdsm.getTrackID());
        boolean breakLoop = false;
        ICDSModel im1 = null;
        ModelAndView model = null;
        try {
            for (int i = 0; i < lst.size(); i++) {
                if (!breakLoop) {
                    im1 = (ICDSModel) lst.get(i);
                    if (im1.getTrackID() == icdsm.getTrackID()) {
                        LOG.info("im.getTrackID()==icdsm.getTrackID() ***** " + im.getTrackID() + "==" + icdsm.getTrackID());
                        breakLoop = true;
                    }
                }
            }
            im1.setRelevingDate(icdsm.getRelevingDate());
            im1.setRelevingTime(icdsm.getRelevingTime());
            if (icdsm.getIncumbencyId() != null) {
                icdsDAO.updateIncumbencyForRelieve(im1);
            }
            redirectAttrs.addFlashAttribute(MESSAGE, SUCCESSMESSAGERELEVING );
            redirectAttrs.addFlashAttribute(EXTENTION, ((locale != null && locale.toString().equalsIgnoreCase("hindi")) ? im1.getStaffingNameHindi() : im1.getStaffingName()));

            model = new ModelAndView(redirectSanctionPostListURL(icdsm.getProjectName(), icdsm.getProjectCode()));
        } catch (Exception e) {
            e.printStackTrace();
            model = new ModelAndView(ICDSSANCTIONRELIEVEINCUMBENCY, COMMAND, icdsm);
            model.addObject(MESSAGE, TECHNICALISSUE);
        }
        return model;
    }

    @RequestMapping("/getProjForDis")
    @ResponseBody
    public void printProject(HttpServletResponse response, @RequestParam(DISTCODE) String distCode) {

        response.setContentType(APPLICATIONJSON);
        PrintWriter out = null;
        JSONObject json = null;
        try {
            out = response.getWriter();
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> projects = icdsDAO.getProjectListFilter(locale, distCode);

            json = new JSONObject(projects);
            out.write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    private String redirectSanctionPostListURL(String projectName, String projectCode) {
        return REDIRECT_ICDSSANCTIONPOSTLIST + projectName + PARAMPROJECTCODE + projectCode;
    }

}
