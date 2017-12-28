/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.common;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author surendra
 */
public class CommonFunctions {
    public static java.sql.Date getDateFromInputString(String inputdate) {
        java.sql.Date sqlAgreetDate = null;
        try {

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sdf1.parse(inputdate);
            sqlAgreetDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlAgreetDate;

    }
    public static String getFormattedOutputDate1(Date dateInput) {
        if (dateInput != null) {
            DateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
            String outputDate = formatDate.format(dateInput);
            return outputDate.toUpperCase();
        } else {
            return null;
        }
    }
    
    public static String getFormattedInputDate(String inputDate)
        {
                String formatDateInput1 = "";
                Date outputDate=null;
                try
                {
                    DateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
                    if(inputDate!=null && !inputDate.equals("")){
                     outputDate = formatDate.parse(inputDate);
                    }
                    
                    DateFormat formatDateInput = new SimpleDateFormat("yyyy-MM-dd");
                    formatDateInput1 = formatDateInput.format(outputDate);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
               
                return formatDateInput1;
        }


}
