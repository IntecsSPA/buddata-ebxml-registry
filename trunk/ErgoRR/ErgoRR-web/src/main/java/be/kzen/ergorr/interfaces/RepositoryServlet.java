/*
 * Project: Buddata ebXML RegRep
 * Class: CswServiceImpl.java
 * Copyright (C) 2008 Yaman Ustuntas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.interfaces;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.ows.ExceptionReport;
import be.kzen.ergorr.model.ows.ExceptionType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.service.RepositoryManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBException;

/**
 *
 * @author yamanustuntas
 */
public class RepositoryServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RepositoryServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml"); // TODO - read from ExtrinsicObject contenttype
        ServletOutputStream out = response.getOutputStream();
        String id = request.getParameter("id");

        if (id != null && !id.trim().equals("")) {
            RepositoryManager repoMngr = new RepositoryManager();
            File file = repoMngr.getFile(id);
            
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Request for repo file: " + file.getAbsolutePath());
            }

            if (file.exists()) {
                int b = 0;
                FileInputStream fis = new FileInputStream(file);

                while ((b = fis.read()) != -1) {
                    out.write(b);
                }
            } else {
                out.print(createException("Repository does not exist", "NotFound"));
            }

        } else {
            out.print(createException("ID not provided", "InvalidRequest"));
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().print("Post not supported");
    }

    private String createException(String error, String code) {
        ExceptionReport exRep = new ExceptionReport();
        exRep.setLang(CommonProperties.getInstance().get("lang"));
        exRep.setVersion("1.0");
        ExceptionType ex = new ExceptionType();
        ex.setExceptionCode(code);
        ex.getExceptionText().add(error);
        exRep.getException().add(ex);

        try {
            return JAXBUtil.getInstance().marshallToStr(exRep);
        } catch (JAXBException ex1) {
            logger.log(Level.SEVERE, "Error marshalling exception report", ex1);
            return "<error>oops</error>";
        }
    }
}