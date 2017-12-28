/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.notification;

import dricds.model.icds.ICDSModel;

/**
 *
 * @author ekank
 */
public class NotiRespondant extends ICDSModel {
    
      private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
      
     @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result = prime * result + (checked ? 1231 : 1237);
        result = prime * result + ((getOfficeId() == null) ? 0 : getOfficeId().hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotiRespondant other = (NotiRespondant) obj;
        
        if (getOfficeId() == null) {
            if (other.getOfficeId() != null)
                return false;
        } else if (!getOfficeId().equals(other.getOfficeId()))
            
            return false;
        System.out.println("getOfficeId() >> "+getOfficeId());
        System.out.println("other.getOfficeId() >> "+other.getOfficeId());
        return true;
    }
}
