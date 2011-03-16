/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.intecs.pisa.erogorr.ui;

/**
 *
 * @author simone
 */
import com.google.gson.JsonObject;
import http.utils.multipartrequest.MultipartRequest;
import http.utils.multipartrequest.ServletMultipartRequest;
import it.intecs.pisa.util.DateUtil;
import it.intecs.pisa.util.IOUtil;
import it.intecs.pisa.util.json.JsonUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Massimiliano Fanciulli
 */
public class StoreData extends HttpServlet {

    protected static final int MAX_READ_BYTES = 10000000;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MultipartRequest parser=null;
        JsonObject outputJson = new JsonObject();
        try {
            parser = new ServletMultipartRequest(request, MAX_READ_BYTES, MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED, null);
        } catch (IllegalArgumentException ex) {
           outputJson.addProperty("success", Boolean.FALSE);
           response.setContentType("text/html");
           IOUtil.copy(JsonUtil.getJsonAsStream(outputJson),response.getOutputStream());
        } catch (IOException ex) {
           outputJson.addProperty("success", Boolean.FALSE);
           response.setContentType("text/html");
           IOUtil.copy(JsonUtil.getJsonAsStream(outputJson),response.getOutputStream());
        }finally{
            File outputFile;
            String id = DateUtil.getCurrentDateAsUniqueId();

            String rootDir = getServletContext().getRealPath("/");
            File webInfDir = new File(rootDir, "WEB-INF");
            File storeDir = new File(webInfDir, "storedData");
            outputFile = new File(storeDir, id);

            IOUtil.copy(parser.getFileContents(parser.getFileParameterNames().nextElement().toString()), new FileOutputStream(outputFile));
            outputJson.addProperty("success", Boolean.TRUE);
            outputJson.addProperty("id", id);
            response.setContentType("text/html");
            IOUtil.copy(JsonUtil.getJsonAsStream(outputJson),response.getOutputStream());
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
