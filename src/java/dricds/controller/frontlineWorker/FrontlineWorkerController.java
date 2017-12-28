/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.controller.frontlineWorker;

import dricds.common.ListPageObject;
import dricds.dao.awc.AwcDAO;
import dricds.dao.category.CategoryDAO;
import dricds.dao.designation.DesignationDAO;
import dricds.dao.district.DistrictDAO;
import dricds.dao.education.EducationDAO;
import dricds.dao.frontlineWorker.FrontlineWorkerDAO;
import dricds.dao.maxcode.MaxCodeDAO;
import dricds.dao.project.ProjectDAO;
import dricds.dao.relation.RelationDAO;
import dricds.dao.sector.SectorDAO;
import dricds.model.frontlineWorker.FrontlineWorkerModel;
import dricds.model.login.LoginModel;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author surendra
 */
@SessionAttributes("SessionAttr")
@Controller
public class FrontlineWorkerController {

    @Autowired
    MaxCodeDAO maxcodeDao;

    @Autowired
    FrontlineWorkerDAO frontlineDAO;

    @Autowired
    CategoryDAO categoryDao;

    @Autowired
    DesignationDAO desigDao;

    @Autowired
    RelationDAO relationDao;

    @Autowired
    EducationDAO eduDaO;

    @Autowired
    DistrictDAO districtDaO;

    @Autowired
    ProjectDAO projectDao;

    @Autowired
    SectorDAO sectorDao;

    @Autowired
    AwcDAO awcDao;

    @RequestMapping("/getProjectForDistrict")
    @ResponseBody
    public void printProject(HttpServletResponse response, @RequestParam("distCode") String distCode) {

        response.setContentType("application/json");
        PrintWriter out = null;
        JSONObject json = null;
        try {
            out = response.getWriter();
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> projects = projectDao.getProjectListDistrictWise(locale, distCode);

            json = new JSONObject(projects);
            out.write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    @RequestMapping("/getSectorForProject")
    @ResponseBody
    public void printSector(HttpServletResponse response, @RequestParam("projectCode") String projectCode) {

        response.setContentType("application/json");
        PrintWriter out = null;
        JSONObject json = null;
        try {
            out = response.getWriter();
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> sectors = sectorDao.getSectorListProjectWise(locale, projectCode);

            json = new JSONObject(sectors);
            out.write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    @RequestMapping("/getCenterForSector")
    @ResponseBody
    public void printCenter(HttpServletResponse response, @RequestParam("sectorCode") String sectorCode) {

        response.setContentType("application/json");
        PrintWriter out = null;
        JSONObject json = null;
        try {
            out = response.getWriter();
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> centers = awcDao.getAnganWadiCenterListSectorWise(locale, sectorCode);

            json = new JSONObject(centers);
            out.write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    @RequestMapping(value = "frontlineWorkerList")
    public ModelAndView showListPage(@ModelAttribute("command") FrontlineWorkerModel fwm, @ModelAttribute("SessionAttr") LoginModel lm) {
        Locale locale = LocaleContextHolder.getLocale();
        ModelAndView mv = new ModelAndView("FrontlineWorkerList", "command", fwm);
        fwm.setUserlevel(lm.getLevel());
        if (lm.getLevel() != null && !lm.getLevel().equals("") && lm.getLevel().equalsIgnoreCase("STATE")) {
            Map<String, String> districtList = districtDaO.getDistrictList(locale);
            mv.addObject("DistrictList", districtList);

            if (fwm.getDistrictCode() == null || fwm.getDistrictCode().equals("")) {
                fwm.setDistrictCode("All");
            }
            if (fwm.getProjectCode() == null || fwm.getProjectCode().equals("")) {
                fwm.setProjectCode("All");
            }
            if (fwm.getSectorCode() == null || fwm.getSectorCode().equals("")) {
                fwm.setSectorCode("All");
            }
            if (fwm.getCenterCode() == null || fwm.getCenterCode().equals("")) {
                fwm.setCenterCode("All");
            }

            Map<String, String> proList = projectDao.getProjectList(locale);
            mv.addObject("ProjectListDistrictWise", proList);

            Map<String, String> sectorList = sectorDao.getSectorList(locale);
            mv.addObject("SectorListProjectWise", sectorList);

            Map<String, String> centerList = awcDao.getAnganWadiCenterList(locale);
            mv.addObject("CenterListSectorWise", centerList);

        } else if (lm.getLevel() != null && !lm.getLevel().equals("") && lm.getLevel().equalsIgnoreCase("DIST")) {
            
            fwm.setDistrictCode(lm.getLevelAreaCode());
            
            if (fwm.getProjectCode() == null || fwm.getProjectCode().equals("")) {
                fwm.setProjectCode("All");
            }
            if (fwm.getSectorCode() == null || fwm.getSectorCode().equals("")) {
                fwm.setSectorCode("All");
            }
            if (fwm.getCenterCode() == null || fwm.getCenterCode().equals("")) {
                fwm.setCenterCode("All");
            }
            Map<String, String> districtList = districtDaO.getDistrictListFilter(locale, fwm.getDistrictCode());
            mv.addObject("DistrictList", districtList);

            Map<String, String> proList = projectDao.getProjectListDistrictWise(locale, fwm.getDistrictCode());
            mv.addObject("ProjectListDistrictWise", proList);

            Map<String, String> sectorList = sectorDao.getSectorList(locale);
            mv.addObject("SectorListProjectWise", sectorList);

            Map<String, String> centerList = awcDao.getAnganWadiCenterList(locale);
            mv.addObject("CenterListSectorWise", centerList);
        } else if (lm.getLevel() != null && !lm.getLevel().equals("") && lm.getLevel().equalsIgnoreCase("PROJECT")) {
            
            if (fwm.getSectorCode() == null || fwm.getSectorCode().equals("")) {
                fwm.setSectorCode("All");
            }
            
            if (fwm.getCenterCode() == null || fwm.getCenterCode().equals("")) {
                fwm.setCenterCode("All");
            }
            fwm.setProjectCode(lm.getLevelAreaCode());
            fwm.setDistrictCode(projectDao.getDistrictCode(fwm.getProjectCode()));

            Map<String, String> districtList = districtDaO.getDistrictListFilter(locale, fwm.getDistrictCode());
            mv.addObject("DistrictList", districtList);

            Map<String, String> proList = projectDao.getProjectListFilter(locale, fwm.getProjectCode());
            mv.addObject("ProjectListDistrictWise", proList);

            Map<String, String> sectorList = sectorDao.getSectorListProjectWise(locale, fwm.getProjectCode());
            mv.addObject("SectorListProjectWise", sectorList);

            Map<String, String> centerList = awcDao.getAnganWadiCenterList(locale);
            mv.addObject("CenterListSectorWise", centerList);

        } else if (lm.getLevel() != null && !lm.getLevel().equals("") && lm.getLevel().equalsIgnoreCase("SECTOR")) {

            fwm.setSectorCode(lm.getLevelAreaCode());
            
            if (fwm.getCenterCode() == null || fwm.getCenterCode().equals("")) {
                fwm.setCenterCode("All");
            }
            fwm.setProjectCode(sectorDao.getProjectCode(fwm.getSectorCode()));
            fwm.setDistrictCode(projectDao.getDistrictCode(fwm.getProjectCode()));

            Map<String, String> proList = projectDao.getProjectListFilter(locale, fwm.getProjectCode());
            mv.addObject("ProjectListDistrictWise", proList);

            Map<String, String> districtList = districtDaO.getDistrictListFilter(locale, fwm.getDistrictCode());
            mv.addObject("DistrictList", districtList);

            Map<String, String> sectorList = sectorDao.getSectorListFilter(locale, fwm.getSectorCode());
            mv.addObject("SectorListProjectWise", sectorList);

            Map<String, String> centerList = awcDao.getAnganWadiCenterListSectorWise(locale, fwm.getSectorCode());
            mv.addObject("CenterListSectorWise", centerList);
        } else if (lm.getLevel() != null && !lm.getLevel().equals("") && lm.getLevel().equalsIgnoreCase("AWC")) {

            fwm.setCenterCode(lm.getLevelAreaCode());
            fwm.setSectorCode(awcDao.getSectorCode(fwm.getCenterCode()));
            fwm.setProjectCode(sectorDao.getProjectCode(fwm.getSectorCode()));
            fwm.setDistrictCode(projectDao.getDistrictCode(fwm.getProjectCode()));

            Map<String, String> districtList = districtDaO.getDistrictListFilter(locale, fwm.getDistrictCode());
            mv.addObject("DistrictList", districtList);

            Map<String, String> proList = projectDao.getProjectListFilter(locale, fwm.getProjectCode());
            mv.addObject("ProjectListDistrictWise", proList);

            Map<String, String> sectorList = sectorDao.getSectorListFilter(locale, fwm.getSectorCode());
            mv.addObject("SectorListProjectWise", sectorList);

            Map<String, String> centerList = awcDao.getCenterListFilter(locale, fwm.getCenterCode());
            mv.addObject("CenterListSectorWise", centerList);
        }

        ListPageObject lo = frontlineDAO.getFrontlineWorkerList(fwm);

        if (lo != null) {
            mv.addObject("FrontlineWorkerList", lo.getDtaList());
            mv.addObject("box1", lo.getBox1());
            mv.addObject("box2", lo.getBox2());
            mv.addObject("box3", lo.getBox3());
            mv.addObject("box4", lo.getBox4());
        }

        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/getAwcNameController")
    public String getAwcNameName(HttpServletRequest request, HttpServletResponse response, @RequestParam("awcCode") String awcCode) {
        String awcname = "";
        response.setContentType("application/json");
        JSONObject obj = new JSONObject();

        try {
            awcname = awcDao.getAwcName(awcCode);
            // obj.put("tcname", "{trainingCenterName:"+tcname+"}");

            obj.put("awcName", awcname);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return obj.toString();
    }

    @RequestMapping(value = "frontLineWorkerEntry", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView entryPage() {
        Locale locale = LocaleContextHolder.getLocale();

        ModelAndView mv = new ModelAndView("FrontlineWorkerMaster", "command", new FrontlineWorkerModel());
        Map<String, String> categoryList = categoryDao.getcategoryList(locale);
        mv.addObject("categoryList", categoryList);
        Map<String, String> educationList = eduDaO.getEducationList(locale);
        mv.addObject("educationList", educationList);
        Map<String, String> desigList = desigDao.getdesignationList(locale);
        mv.addObject("desigList", desigList);
        Map<String, String> relationList = relationDao.getRelationList(locale);
        mv.addObject("relationList", relationList);

        return mv;
    }

    @RequestMapping(value = "/frontlineWorkerControllerSave")
    public ModelAndView addUpdateFrontlineWorkData(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("command") FrontlineWorkerModel fwm, @RequestParam("frontline") String btnname) throws UnsupportedEncodingException {

        response.setContentType("text/html; charset=UTF-8");
        if (btnname.equals("Save")) {
            if (fwm.getWorkerId() != null && !fwm.getWorkerId().equals("")) {
                frontlineDAO.updateFrontlineWorkData(fwm);
            } else {
                int maxtcid = maxcodeDao.getMaxCode("frontline_worker", "worker_id");
                fwm.setWorkerId(StringUtils.leftPad(maxtcid + "", 4, "0"));
                frontlineDAO.addFrontlineWorkerData(fwm);
            }
        } else if (btnname.equals("Delete")) {
            frontlineDAO.deleteFrontlineWorkData(fwm.getWorkerId());
        }

        return new ModelAndView("redirect:/frontlineWorkerList.htm");
    }

    @RequestMapping("/frontlineWorkerControllerEdit")
    public ModelAndView editfrontlineWorkerData(@RequestParam(value = "workerId", required = false) String workerId) {
        Locale locale = LocaleContextHolder.getLocale();
        ModelAndView mv = null;
        if (workerId != null && !workerId.equals("")) {
            FrontlineWorkerModel fwm = frontlineDAO.editFrontlineWorkerData(workerId);

            mv = new ModelAndView("FrontlineWorkerMaster", "command", fwm);

            Map<String, String> categoryList = categoryDao.getcategoryList(locale);
            mv.addObject("categoryList", categoryList);
            Map<String, String> educationList = eduDaO.getEducationList(locale);
            mv.addObject("educationList", educationList);
            Map<String, String> desigList = desigDao.getdesignationList(locale);
            mv.addObject("desigList", desigList);
            Map<String, String> relationList = relationDao.getRelationList(locale);
            mv.addObject("relationList", relationList);
        } else {
            mv = new ModelAndView("redirect:/frontlineWorkerList.htm");
        }
        return mv;

    }

    @RequestMapping("/frontlineWorkerControllerView")
    public ModelAndView viewfrontlineWorkerData(@RequestParam(value = "workerId", required = false) String workerId) {
        ModelAndView mv = null;
        Locale locale = LocaleContextHolder.getLocale();
        if (workerId != null && !workerId.equals("")) {
            FrontlineWorkerModel fwm = frontlineDAO.viewFrontlineWorkerData(workerId);
            mv = new ModelAndView("FrontlineWorkerView", "command", fwm);

        } else {
            mv = new ModelAndView("redirect:/frontlineWorkerList.htm");
        }
        return mv;

    }

}
