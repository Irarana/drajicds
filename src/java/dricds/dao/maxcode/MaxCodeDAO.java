/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.maxcode;

/**
 *
 * @author surendra
 */
public interface MaxCodeDAO {
    public int getMaxCode(String tablename, String columnName);
     public String getMaxNumber(String tablename, String columnName);
}
