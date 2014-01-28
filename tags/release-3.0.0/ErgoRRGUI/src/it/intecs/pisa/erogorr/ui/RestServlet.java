

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
    
    protected static final String REST_SET_LOCALHOST = "rest/set";
    
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
            String uri = request.getRequestURI();
            if (uri.contains(REST_SET_LOCALHOST)) {
                if(authenticate(request, "admin")){
                if(uri.endsWith(REST_SET_LOCALHOST)){
                    this.ergoRRConf=new ErgoRRGUIConfiguration(getPropertiesFile());
                    String host=ergoRRConf.getProperty(ErgoRRGUIConfiguration.HOST_PROPERTY);
                    String port=ergoRRConf.getProperty(ErgoRRGUIConfiguration.PORT_PROPERTY);
                    JsonObject jObj = new JsonObject();
                    if(port==null)port="";
                    if(host==null)host="";
                    jObj.addProperty("host", host);
                    jObj.addProperty("port", port);
                    Gson gson = new Gson();
                    response.setHeader("Content-Type", "application/json");
                    out.write(gson.toJson(jObj).getBytes());
                   
                }else{
                    String [] splitUri=uri.split("/");
                     this.ergoRRConf.addLocalAddressProperty(splitUri[splitUri.length-2],
                                                                splitUri[splitUri.length-1]);
                     JsonObject jObj = new JsonObject();
                     jObj.addProperty("success", Boolean.TRUE);
                     Gson gson = new Gson();
                     response.setHeader("Content-Type", "application/json");
                     out.write(gson.toJson(jObj).getBytes());
                }
              }  
            }     
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally { 
            out.close();
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
        return new File(getServletContext().getRealPath(PROPERTIES_PATH));
    
    }
}
