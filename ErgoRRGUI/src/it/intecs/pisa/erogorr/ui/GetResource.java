/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.intecs.pisa.erogorr.ui;

import it.intecs.pisa.util.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 *
 * @author Andrea Marongiu
 */
public class GetResource extends RestServlet {

    protected static final String REST_RESOURCE_PANELS = "/resources/panels";
    protected static final String REST_RESOURCE_LOG = "/resources/log";
    protected static final String REST_RESOURCE_EOP_CLIENT = "/resources/catalogueClient/eop";
    protected static final String REST_RESOURCE_CIM_CLIENT = "/resources/catalogueClient/cim";
    protected static final String REST_RESOURCE_PROPERTIES = "/resources/properties"; 
    
    
    protected static final String USER_PANELS_RESOURCE = "userPanels.js";
    protected static final String ADMIN_PANELS_RESOURCE = "adminPanels.js";
    protected static final String CIM_CATALOGUE_CLIENT_RESOURCE = "cimCatalogue.html";
    protected static final String EOP_CATALOGUE_CLIENT_RESOURCE = "eopCatalogue.html";
    protected static final String WEBINF_RESOURCES_FOLDER = "WEB-INF/conf/resources/";
    protected static final String ERGORR_LOG_FOLDER = "../logs";
    protected static final String ERGORR_LOG_PREFIX = "ergorr.";
    protected static final String ERGORR_LOG_SUFIX = ".log";
    
    protected static final int LOG_ROWS = 500;
    

    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String uri;
        int logRows=0;
        File webInfResourceFolder=new File(getServletContext().getRealPath(WEBINF_RESOURCES_FOLDER));
        OutputStream out = response.getOutputStream();
        try {
            uri = request.getRequestURI();
            if (uri.endsWith(REST_RESOURCE_PANELS)) {
                if(authenticate(request, "admin"))
                    IOUtil.copy(new FileInputStream(new File(webInfResourceFolder, ADMIN_PANELS_RESOURCE)), out);
                else 
                if (authenticate(request, "user"))
                    IOUtil.copy(new FileInputStream(new File(webInfResourceFolder, USER_PANELS_RESOURCE)), out);
            }else
              if (uri.endsWith(REST_RESOURCE_LOG)) {
                if(authenticate(request, "admin"))
                    logRows=this.getRowsNumber(new FileInputStream(new File(this.getLogFilePath())));
                    this.copyLastRows(new FileInputStream(new File(this.getLogFilePath())), out, logRows, LOG_ROWS);
              }else
                  if (uri.endsWith(REST_RESOURCE_EOP_CLIENT)) {
                    if(authenticate(request, "admin") || authenticate(request, "user"))
                        IOUtil.copy(new FileInputStream(new File(webInfResourceFolder, EOP_CATALOGUE_CLIENT_RESOURCE)), out);
                  }else
                      if (uri.endsWith(REST_RESOURCE_CIM_CLIENT)) {
                        if(authenticate(request, "admin") || authenticate(request, "user"))
                            IOUtil.copy(new FileInputStream(new File(webInfResourceFolder, CIM_CATALOGUE_CLIENT_RESOURCE)), out);
                      }else
                          if (uri.endsWith(REST_RESOURCE_PROPERTIES)) {
                            if(authenticate(request, "admin") || authenticate(request, "user"))
                               IOUtil.copy(new FileInputStream(getPropertiesFile()), out);
                          }    
            
            
        }catch (Exception ex){
            request.getSession().invalidate();
            response.sendError(401);
        } finally {
            
            out.close();
        }
    }




    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
  
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    private String getLogFilePath () throws IOException{
    
        //File rootDir = new File(getServletContext().getRealPath("/"));
        File logDir= new File(/*rootDir,*/ ERGORR_LOG_FOLDER);
        String logFileName=ERGORR_LOG_PREFIX;
        
        Calendar cal = Calendar.getInstance();//ergorr.2011-03-15.log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        logFileName+=sdf.format(cal.getTime())+ERGORR_LOG_SUFIX;

        
        return new File(logDir,logFileName).getCanonicalPath();
    }

    
    private void copyLastRows (InputStream is, OutputStream out, int rowsNumber, int lastRowsNumber) throws IOException{
       int firstRow=rowsNumber-lastRowsNumber;
       if(firstRow <0)
           firstRow=0;
       try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            if(count >= firstRow)
               out.write(c,0, readChars);
            else{
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                    if(count >= firstRow)
                      out.write(c,i+1, readChars-(i+1));  
                }
            }    
        }
        } finally {
            is.close();
        }
      }
    
    
    private int getRowsNumber (InputStream is) throws IOException{
    
       try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
         return count;
        } finally {
            is.close();
        }
      }
    
    
    
    private boolean authenticate(HttpServletRequest request, String role) throws Exception{
       AuthenticationManager am= new AuthenticationManager();
       String [] roles= am.authenticateUser(request);
       for(int i=0; i<roles.length; i++)
           if(roles[i].equals(role))
              return true;
       return false;
    }
    
    
}
