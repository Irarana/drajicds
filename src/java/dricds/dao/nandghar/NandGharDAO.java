/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.nandghar;

import dricds.common.ListPageObject;
import dricds.model.nandghar.NandGharSponsor;
import java.util.ArrayList;

/**
 *
 * @author bab
 */
public interface NandGharDAO {

    public ListPageObject getNandGharList();

    public String getAWCName(String awcCode);

    public void saveNandGharData(NandGharSponsor ngs);

    public NandGharSponsor getSponsorData(String sponsorId);

    public void updateNandGharData(NandGharSponsor ngs);

    public void deleteNandGharData(NandGharSponsor ngs);

    public void assignSponsoredName(String awcCode, String sponsoredId);
     public void freeAWCFromSponsor(String awcCode);
     public ArrayList getSponsoredAWCList(String sponsorId);
    
}
