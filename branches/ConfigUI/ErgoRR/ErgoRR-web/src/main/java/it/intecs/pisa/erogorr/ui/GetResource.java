/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.intecs.pisa.erogorr.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea Marongiu
 */
public class GetResource extends HttpServlet {

    protected static final String REST_RESOURCE_PANELS = "/resources/panels";
    //protected static final String REST_USER_PANELS = "/panels/user";
    protected static final String USER_PANELS_RESOURCE = "userPanels.js";
    protected static final String ADMIN_PANELS_RESOURCE = "adminPanels.js";
    protected static final String WEBINF_RESOURCES_FOLDER = "WEB-INF/conf/resources/";
    

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
        File webInfResourceFolder=new File(getServletContext().getRealPath(WEBINF_RESOURCES_FOLDER));
        OutputStream out = response.getOutputStream();
        try {
            uri = request.getRequestURI();
            if (uri.endsWith(REST_RESOURCE_PANELS)) {
                if(authenticate(request, "admin"))
                    copy(new FileInputStream(new File(webInfResourceFolder, ADMIN_PANELS_RESOURCE)), out);
                else 
                if (authenticate(request, "user"))
                    copy(new FileInputStream(new File(webInfResourceFolder, USER_PANELS_RESOURCE)), out);
                
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
    @Override
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    

    private boolean authenticate(HttpServletRequest request, String role) throws Exception{
       AuthenticationManager am= new AuthenticationManager();
       String [] roles= am.authenticateUser(request);
       for(int i=0; i<roles.length; i++)
           if(roles[i].equals(role))
              return true;
       return false;
    }
    
    private static void copy(InputStream in, OutputStream out) throws IOException {
        synchronized (in) {
            synchronized (out) {

                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
