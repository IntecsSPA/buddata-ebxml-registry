

package it.intecs.pisa.erogorr.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.intecs.pisa.erogorr.ui.conf.ErgoRRGUIConfiguration;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrea Marongiu
 */
public class RestServlet extends HttpServlet {
    
    
    protected static final String PROPERTIES_PATH = "WEB-INF/conf/properties/ergoRRProperties.xml";
    
    public ErgoRRGUIConfiguration ergoRRConf=null;
    
    
    
    public void init(){
        try {
            this.ergoRRConf=new ErgoRRGUIConfiguration(getPropertiesFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
       
    
    }
   
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
        OutputStream out = response.getOutputStream();
        try {
            this.ergoRRConf.addLocalAddressProperty(request.getLocalAddr(),""+request.getServerPort());
            JsonObject jObj = new JsonObject();
            jObj.addProperty("success", Boolean.TRUE);
            Gson gson = new Gson();
            response.setHeader("Content-Type", "application/json");
            out.write(gson.toJson(jObj).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
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



    public File getPropertiesFile(){
        System.out.println("------------ "+ getServletContext().getRealPath(PROPERTIES_PATH));
        return new File(getServletContext().getRealPath(PROPERTIES_PATH));
    
    }
}
