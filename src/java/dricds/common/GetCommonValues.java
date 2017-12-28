package dricds.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import javax.activation.FileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

@SuppressWarnings("deprecation")
public class GetCommonValues {
	static Connection con=null;
	static String query;
	
	public static String queryString(String inputSQL){
		return inputSQL.replace("'", "''");
	}
	public static boolean isUserSessionValid(HttpServletRequest req){
		if(req.isRequestedSessionIdValid()){
			HttpSession ses=req.getSession(true);
			String userid=(String)ses.getAttribute("ses_uid");
			return (userid!=null);
		}
		else return false;
	}
	public static String isNull(String input){

		String output="";
		output=(input==null)?"":input;
		return output;
	}
	public static String isDate(String input){
		String output=null;
		if(!input.equals(""))output=input;
		return output;
	}
	public static StringBuffer getXml(ResultSet rs, String mainTag){

		StringBuffer outXml=new StringBuffer();
		try{
			ResultSetMetaData rsmd=rs.getMetaData();
			int colCnt=rsmd.getColumnCount();
			if(rs.next()){
				do{
					outXml.append("<"+mainTag+">");
					for(int i=1;i<=colCnt;i++){
						outXml.append("<"+rsmd.getColumnName(i)+">" +
								"<![CDATA["+isNull(rs.getString(i))+"]]>" +
								"</"+rsmd.getColumnName(i)+">");
					}
					outXml.append("</"+mainTag+">");
				}while (rs.next());
			}
		}
		catch (Exception e) {
			System.err.println("getXml Error : "+e.getMessage());
		}
		return outXml;
	}
	public static ArrayList<String> getArrayList(ResultSet rs){

		ArrayList<String> arrOutPut=new ArrayList<String>();
		try{
			ResultSetMetaData rsm=rs.getMetaData();
			if(rs.next()){
				do{
					for(int i=1;i<=rsm.getColumnCount();i++){
						arrOutPut.add(isNull(rs.getString(i)));
					}
				}while (rs.next());
				//rs.close();
			}
		}
		catch (Exception e) {
			System.err.println("getArrayList Error : "+e.getMessage());
		}
		return arrOutPut;
	}
	public static int getCurYear(){
		int year=0;
		try{
			Date dt=new Date();
			year=dt.getYear()+1900;
		}catch (Exception e) {
			System.err.println("getCurYear error = "+e.getMessage());
		}
		finally{}
		return year;
	}
	public static int getCurMonth(){
		int month=0;
		try{
			Date dt=new Date();
			month=(dt.getMonth())+1;
		}catch (Exception e) {
			System.err.println("getCurMonth error = "+e.getMessage());
		}
		finally{}
		return month;
	}
	public static String getCurDate(){
		String curDate="";
		try{
			String months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
			Date dt=new Date();
			String date=""+dt.getDate();
			date=(date.length()==1)?"0"+date:date;
			String hr=""+dt.getHours();
			hr=(hr.length()==1)?"0"+hr:hr;
			String min=""+dt.getMinutes();
			min=(min.length()==1)?"0"+min:min;
			curDate=date+"-"+months[dt.getMonth()]+"-"+(dt.getYear()+1900)+"  "+hr+":"+min;
		}catch (Exception e) {
			System.err.println("getCurDate error = "+e.getMessage());
		}
		finally{}
		return curDate;
	}
	public static int getDaysInMonth(int year, int month){
		int leapYrDays=0;
		if(year%4==0){
			if(year%100==0 && year%400!=0)leapYrDays=28;
			else leapYrDays=29;
		}
		else leapYrDays=28;
		int arrDays[]={31,leapYrDays,31,30,31,30,31,31,30,31,30,31};
		return arrDays[month-1];
	}
	public static HSSFCellStyle headStyle(HSSFWorkbook workBook){
		HSSFCellStyle header=workBook.createCellStyle();
		header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		header.setWrapText(true);
		header.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		header.setFillForegroundColor(HSSFColor.BLUE.index);
		HSSFFont font=workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setUnderline(HSSFFont.U_SINGLE);
		font.setColor(HSSFFont.COLOR_NORMAL);
		header.setFont(font);		
		return header;
	}
	public static HSSFCellStyle headerStyle(HSSFWorkbook workBook){
		HSSFCellStyle header=workBook.createCellStyle();
		header.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		header.setWrapText(true);
		header.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		header.setFillForegroundColor(HSSFColor.BLUE.index);
		HSSFFont font=workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
		font.setColor(HSSFFont.COLOR_NORMAL);
		header.setFont(font);		
		return header;
	}
	public static HSSFCellStyle totalStyle(HSSFWorkbook workBook){
		HSSFCellStyle header=workBook.createCellStyle();
		header.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		header.setWrapText(true);
		header.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		header.setFillForegroundColor(HSSFColor.BLUE.index);
		HSSFFont font=workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFFont.COLOR_NORMAL);
		header.setFont(font);		
		return header;
	}
	public static ArrayList<String> getFileList_DateWise(String folderPath){
		ArrayList<String> arrFileList=new ArrayList<String>();
	    File folder = new File(folderPath);
	    if(folder.isDirectory()){
		    File[] listOfFiles = folder.listFiles();
		    Outer:
		    for (int i = 0; i < listOfFiles.length; i++) {
			    File f1=listOfFiles[i];
		    	if(f1.isFile() && !f1.getName().equals("1.txt")){
		    		String t1=f1.getName().substring(2,11);
		    		Date dt1=new Date(t1);
			    	for(int j=0; j<i; j++){
			    		String t2=f1.getName().substring(2,11);
			    		Date dt2=new Date(t2);
			    		if(dt1.before(dt2)){
			    			for(int x=i-1;x>=j;x--){
			    				listOfFiles[x+1]=listOfFiles[x];
			    			}
			    			listOfFiles[j]=f1;
			    			continue Outer;
			    		}
			    	}
			    }
		    }
		    for (int i = 0; i < listOfFiles.length; i++)arrFileList.add(listOfFiles[i].getName());
	    }
	    return arrFileList;
	}
	public static void setPrmNull(CallableStatement cstm,int index, String param,int Datatype){
		//1 : For String Parameter
		//2 : For Int Parameter
		//3 : For Decimal
		try {
			if(param!=null && param.trim().length()!=0){
				if(Datatype==1)
					cstm.setString(index, param);
				else if(Datatype==2)
					cstm.setInt(index, Integer.parseInt(param));
			}
			else{
				if(Datatype==1)
					cstm.setNull(index, Types.VARCHAR);
				else if(Datatype==2)
					cstm.setNull(index, Types.INTEGER);
			}
		} catch (Exception e) {
			System.err.println("GetComnValues.setPrmNull1 Error :"+e.getMessage());
		}
	}
	
	public static void ajaxSesOut(HttpServletRequest req, HttpServletResponse res)throws Exception{
		try{
			String xml="<Table><sesout>1</sesout></Table>";
			res.setContentType("text/xml");
			PrintWriter wr = res.getWriter();
			wr.write(xml.toString());
			wr.close();
			wr.flush();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	public static String getPassword(String UserId){
		String pass="";
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		try{
			//getStarted();
			con=Datasource.getConnection();
			stm=con.createStatement();
			query="Select user_pass from user_mast where user_id='"+UserId+"'";
			rs=stm.executeQuery(query);
			if(rs.next()){
				pass=rs.getString("user_pass");
			}
		}catch (Exception e) {
			System.err.println("getPassword error = "+e.getMessage());
		}
		finally{
			try {
				if(con!=null)con.close();
				if(stm!=null)stm.close();
				//if(rs!=null)rs.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return pass;
	}
	
	public static void downloadFile(HttpServletResponse response, String FilePath){
		try {
			File fileToDownload = new File(FilePath);
			FileTypeMap fMap=FileTypeMap.getDefaultFileTypeMap();
			String sContentType = fMap.getContentType(fileToDownload);
			response.setContentType(sContentType);
			String disHeader = "Attachment;Filename=\""+fileToDownload.getName()+"\"";
			response.setHeader("Content-Disposition", disHeader);
			ServletOutputStream out=response.getOutputStream();
			FileInputStream fileInputStream = new FileInputStream(fileToDownload);
			int i;
			while ((i=fileInputStream.read())!=-1){
				out.write(i);
			}
			fileInputStream.close();
			out.close();
			out.flush();
		} catch (Exception e) {
			System.err.println("Download Error : "+e.getMessage());
		}
	}
	public static void setPSPrmNull(PreparedStatement pst,int index, String param,int Datatype){
		//1 : For String Parameter
		//2 : For Int Parameter
		//3 : For Decimal
		try {
			if(param!=null && param.trim().length()!=0){
				if(Datatype==1)
					pst.setString(index, param);
				else if(Datatype==2)
					pst.setInt(index, Integer.parseInt(param));
				else if(Datatype==3)
					pst.setDouble(index, Double.parseDouble(param));
			}
			else{
				if(Datatype==1)
					pst.setNull(index, Types.VARCHAR);
				else if(Datatype==2)
					pst.setNull(index, Types.INTEGER);
				else if(Datatype==3)
					pst.setNull(index, Types.DOUBLE);
			}
		} catch (Exception e) {
			System.err.println("GetComnValues.setPrmNull1 Error :"+e.getMessage());
		}
	}
}
