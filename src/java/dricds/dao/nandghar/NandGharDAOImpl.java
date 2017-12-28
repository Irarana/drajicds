/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.nandghar;

import dricds.common.CommonFunctions;
import dricds.common.DataBaseFunctions;
import dricds.common.ListPageObject;
import dricds.model.nandghar.NandGharSponsor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author bab
 */
public class NandGharDAOImpl implements NandGharDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ListPageObject getNandGharList() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List nandgharList = new ArrayList();
        NandGharSponsor ngs = null;
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("select Sponsor_Id,Sponsor_Name,Contact_Person,Start_DoMoU,End_DoMoU,\n"
                    + "nand_ghar_sponsor.MoU_Type,Estimated_Cost,No_of_AWC_Adopted,\n"
                    + "G_MoU_Type.MoU_Type gmoutype from nand_ghar_sponsor\n"
                    + "left outer join G_MoU_Type on G_MoU_Type.MoU_Type_Id=nand_ghar_sponsor.MoU_Type");
            //System.out.println("select Sponsor_Id,Sponsor_Name,Contact_Person,Start_DoMoU,End_DoMoU,MoU_Type,Estimated_Cost,No_of_AWC_Adopted from nand_ghar_sponsor inner join G_MoU_Type on G_MoU_Type.MoU_Type_Id=nand_ghar_sponsor.MoU_Type");
            rs = pst.executeQuery();
            while (rs.next()) {
                ngs = new NandGharSponsor();
                ngs.setSponsorId(rs.getString("Sponsor_Id"));
                ngs.setSponsorNameEn(rs.getString("Sponsor_Name"));
                ngs.setContactPerson(rs.getString("Contact_Person"));
                ngs.setStartDoMoU(CommonFunctions.getFormattedOutputDate1(rs.getDate("Start_DoMoU")));
                ngs.setEndDoMoU(CommonFunctions.getFormattedOutputDate1(rs.getDate("End_DoMoU")));
                ngs.setMoUType(rs.getString("gmoutype"));
                ngs.setEstimatedCost(rs.getString("Estimated_Cost"));
                ngs.setNoofAWCAdopted(rs.getString("No_of_AWC_Adopted"));
                nandgharList.add(ngs);
            }
            lo.setDtaList(nandgharList);
            DataBaseFunctions.closeSqlObjects(rs);

            pst = con.prepareStatement("select count(mou_type) cnt,mou_type from nand_ghar_sponsor where if_csr='Y' group by mou_type");
            rs = pst.executeQuery();
            int csr=0;
            while (rs.next()) {

                if (rs.getInt("cnt") > 0) {
                    if (rs.getString("mou_type").equals("1")) {
                        lo.setBox1(rs.getString("cnt"));
                        csr=csr+rs.getInt("cnt");
                    } else if (rs.getString("mou_type").equals("2")) {
                        lo.setBox2(rs.getString("cnt"));
                        csr=csr+rs.getInt("cnt");
                    } else if (rs.getString("mou_type").equals("3")) {
                        lo.setBox3(rs.getString("cnt"));
                        csr=csr+rs.getInt("cnt");
                    } else if (rs.getString("mou_type").equals("4")) {
                        lo.setBox4(rs.getString("cnt"));
                        csr=csr+rs.getInt("cnt");
                    }
                }
            }
            lo.setBox11(csr+"");
            DataBaseFunctions.closeSqlObjects(rs);

            pst = con.prepareStatement("select count(*) cnt from nand_ghar_sponsor where COALESCE(if_csr,'N') <> 'Y'");
            rs = pst.executeQuery();
            while (rs.next()) {

                if (rs.getInt("cnt") > 0) {
                    lo.setBox12(rs.getInt("cnt")+"");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return lo;

    }

    @Override
    public String getAWCName(String awcCode) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList nandgharList = null;
        String awcName = null;
        try {
            con = dataSource.getConnection();
            System.out.println("select * from awc_master where awc_code='" + awcCode + "'");
            // pst=con.prepareStatement("select * from awc_master where awc_code=?");
            st = con.createStatement();
            rs = st.executeQuery("select * from awc_master where awc_code='" + awcCode + "'");

            // rs=pst.executeQuery();
            if (rs.next()) {
                awcName = rs.getString("awc_name_english");
                System.out.println("=========nand name=====" + rs.getString("awc_name_english"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(st);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awcName;
    }

    public void saveNandGharData(NandGharSponsor ngs) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            System.out.println("==========inside save =======" + ngs.getSponsorNameEn());
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("insert into nand_ghar_sponsor(Sponsor_Name,Sponsor_Name_Hindi,Contact_Person,Mobile,eMail,Address,Start_DoMoU,"
                    + "End_DoMoU,MoU_Type,if_CSR,Estimated_Cost,No_of_AWC_Adopted,If_PSE,If_SNP,If_Other) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, ngs.getSponsorNameEn());
            pst.setString(2, ngs.getSponsorNameHn());
            pst.setString(3, ngs.getContactPerson());
            pst.setString(4, ngs.getMobile());
            pst.setString(5, ngs.geteMail());
            pst.setString(6, ngs.getAddress());
            if (ngs.getStartDoMoU() != null && !ngs.getStartDoMoU().equals("")) {
                pst.setString(7, CommonFunctions.getFormattedInputDate(ngs.getStartDoMoU()));
            } else {
                pst.setString(7, null);
            }
            if (ngs.getEndDoMoU() != null && !ngs.getEndDoMoU().equals("")) {
                pst.setString(8, CommonFunctions.getFormattedInputDate(ngs.getEndDoMoU()));
            } else {
                pst.setString(8, null);
            }
            pst.setString(9, ngs.getMoUType());
            pst.setString(10, ngs.getIfCSR());
            if(ngs.getEstimatedCost()!=null && !ngs.getEstimatedCost().equals("")){
                pst.setString(11, ngs.getEstimatedCost());
            }else{
                pst.setString(11, "0");
            }
             if(ngs.getNoofAWCAdopted()!=null && !ngs.getNoofAWCAdopted().equals("")){
                pst.setString(12,ngs.getNoofAWCAdopted());
            }else{
                pst.setString(12, "0");
            }
           // pst.setString(12, ngs.getNoofAWCAdopted());
            pst.setString(13, ngs.getIfPSE());
            pst.setString(14, ngs.getIfSNP());
            pst.setString(15, ngs.getIfOther());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }

    }

    public void updateNandGharData(NandGharSponsor ngs) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("update  nand_ghar_sponsor set Sponsor_Name=?,Sponsor_Name_Hindi=?,Contact_Person=?,Mobile=?,eMail=?,Address=?,Start_DoMoU=?,"
                    + "End_DoMoU=?,MoU_Type=?,if_CSR=?,Estimated_Cost=?,No_of_AWC_Adopted=?,If_PSE=?,If_SNP=?,If_Other=? where Sponsor_Id=?");
            pst.setString(1, ngs.getSponsorNameEn());
            pst.setString(2, ngs.getSponsorNameHn());
            pst.setString(3, ngs.getContactPerson());
            pst.setString(4, ngs.getMobile());
            pst.setString(5, ngs.geteMail());
            pst.setString(6, ngs.getAddress());
            if (ngs.getStartDoMoU() != null && !ngs.getStartDoMoU().equals("")) {
                pst.setString(7, CommonFunctions.getFormattedInputDate(ngs.getStartDoMoU()));
            } else {
                pst.setString(7, null);
            }
            if (ngs.getEndDoMoU() != null && !ngs.getEndDoMoU().equals("")) {
                pst.setString(8, CommonFunctions.getFormattedInputDate(ngs.getEndDoMoU()));
            } else {
                pst.setString(8, null);
            }

            pst.setString(9, ngs.getMoUType());
            pst.setString(10, ngs.getIfCSR());
            pst.setString(11, ngs.getEstimatedCost());
            pst.setString(12, ngs.getNoofAWCAdopted());
            pst.setString(13, ngs.getIfPSE());
            pst.setString(14, ngs.getIfSNP());
            pst.setString(15, ngs.getIfOther());
            pst.setString(16, ngs.getSponsorId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }

    }

    public NandGharSponsor getSponsorData(String sponsorId) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        NandGharSponsor ngs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("select Sponsor_Id,Sponsor_Name,Sponsor_Name_Hindi,Contact_Person,Mobile,eMail,Address,Start_DoMoU,"
                    + "End_DoMoU,MoU_Type,if_CSR,Estimated_Cost,No_of_AWC_Adopted,If_PSE,If_SNP,If_Other from nand_ghar_sponsor where Sponsor_Id=?");
            pst.setString(1, sponsorId);
            rs = pst.executeQuery();
            if (rs.next()) {
                ngs = new NandGharSponsor();
                ngs.setSponsorNameEn(rs.getString("Sponsor_Name"));
                ngs.setSponsorNameHn(rs.getString("Sponsor_Name_Hindi"));
                ngs.setContactPerson(rs.getString("Contact_Person"));
                ngs.setMobile(rs.getString("Mobile"));
                ngs.seteMail(rs.getString("eMail"));
                ngs.setAddress(rs.getString("Address"));
                ngs.setStartDoMoU(CommonFunctions.getFormattedOutputDate1(rs.getDate("Start_DoMoU")));
                ngs.setEndDoMoU(CommonFunctions.getFormattedOutputDate1(rs.getDate("End_DoMoU")));
                ngs.setMoUType(rs.getString("MoU_Type"));
                ngs.setIfCSR(rs.getString("if_CSR"));
                ngs.setEstimatedCost(rs.getString("Estimated_Cost"));
                ngs.setNoofAWCAdopted(rs.getString("No_of_AWC_Adopted"));
                ngs.setIfPSE(rs.getString("If_PSE"));
                ngs.setIfSNP(rs.getString("If_SNP"));
                ngs.setIfOther(rs.getString("If_Other"));
                ngs.setSponsorId(rs.getString("Sponsor_Id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return ngs;
    }
     public void deleteNandGharData(NandGharSponsor ngs) {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("delete from nand_ghar_sponsor where Sponsor_Id=?");
            pst.setString(1, ngs.getSponsorId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
    }
     public void assignSponsoredName(String awcCode, String sponsoredId) {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("update awc_master set Sponsor_Id=? where AWC_Code=?");
            pst.setString(1, sponsoredId);
            pst.setString(2, awcCode);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
    }

    public ArrayList getSponsoredAWCList(String sponsorId) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList awcList = new ArrayList();
        NandGharSponsor ngs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("select AWC_Code,AWC_Name_English from awc_master where Sponsor_Id=?");
            pst.setString(1, sponsorId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ngs = new NandGharSponsor();
                ngs.setAwcCode(rs.getString("AWC_Code"));
                ngs.setAwcName(rs.getString("AWC_Name_English"));
                awcList.add(ngs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awcList;
    }
     public void freeAWCFromSponsor(String awcCode) {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("update awc_master set Sponsor_Id=null where AWC_Code=?");
            pst.setString(1, awcCode);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
    }
     

}
