/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.login;

import dricds.model.login.LoginModel;

/**
 *
 * @author surendra
 */
public interface LoginDAO {
    
    public LoginModel validateUser(String userid, String password);
   
}
