

package it.intecs.pisa.erogorr.ui;

import it.intecs.pisa.util.DOMUtil;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Andrea Marongiu
 */
public class AuthenticationManager {
    
    protected static final String USERS_XML = "conf/ergoRRGUIUsers.xml";
    protected static final String USER_ELEMENT_NAME = "user";
    protected static final String USER_NAME_ATTRIBUTE = "username";
    protected static final String PASSWORD_ATTRIBUTE = "password";
    protected static final String ROLES_ATTRIBUTE = "roles";
    
    
    /**
     * This method checks client authorization.
     * @param request Request class containing all information to use to authenticate
     * the user
     * @return User roles or NULL if the user is not authorized.
     */
    public String [] authenticateUser(HttpServletRequest request) throws IOException, Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        String userID,password;
        String [] roles=null;
        StringTokenizer st = new java.util.StringTokenizer(request.getHeader("Authorization"));

        if (st.hasMoreTokens()) {
            String basic = st.nextToken();
            // We only handle HTTP Basic authentication

            if (basic.equalsIgnoreCase("Basic")) {
               String credentials = st.nextToken();
               decoder = new sun.misc.BASE64Decoder();
               String userPass =
                  new String(decoder.decodeBuffer(credentials));

               int p = userPass.indexOf(":");
               if (p != -1) {
                  userID = userPass.substring(0, p);
                  password = userPass.substring(p+1);     

                  roles=getRoles(userID,password);
               }
            }
         }

        return roles;
    }
    
    
    private String [] getRoles (String user, String password) throws Exception{
        String [] roles=null;
        DOMUtil du=new DOMUtil();
        File userDocFile=new File(this.getClass().getResource(USERS_XML).toURI());
        Document usersDoc=du.fileToDocument(userDocFile);
        NodeList userNodeList=usersDoc.getElementsByTagName(USER_ELEMENT_NAME);
        Element currentUser=null;
        for(int i=0; i<userNodeList.getLength(); i++){
            currentUser=(Element) userNodeList.item(i);
            if(currentUser.getAttribute(USER_NAME_ATTRIBUTE).equals(user) &&
               currentUser.getAttribute(PASSWORD_ATTRIBUTE).equals(password) ){
               
               roles=currentUser.getAttribute(ROLES_ATTRIBUTE).split(",");
               if(roles.length==0){
                  roles=new String[1];
                  roles[1]=currentUser.getAttribute(ROLES_ATTRIBUTE);
               }
               
             break;
            }
        }
        return roles;
    }

}
