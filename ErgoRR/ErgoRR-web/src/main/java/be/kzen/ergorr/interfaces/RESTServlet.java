package be.kzen.ergorr.interfaces;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.logging.LoggerSetup;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RESTServlet extends HttpServlet
{
  protected static final String REST_RESOURCE_CONFIG = "config";
  protected static final String REST_RESOURCE_LOG_LEVEL = "config/log/level";
  protected static final String REST_RESOURCE_AUTHENTICATE = "authenticate";
  private static Logger logger = Logger.getLogger(RESTServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    if (authenticateUser(request))
    {
      String uri = request.getRequestURI();
      String jsonStr;
      if (uri.endsWith(REST_RESOURCE_CONFIG)) {
        jsonStr = getConfiguration(request);
      }
      else
      {
        if (uri.contains(REST_RESOURCE_LOG_LEVEL))
        {
          if (uri.endsWith(REST_RESOURCE_LOG_LEVEL)) {
            jsonStr = getLogLevel();
          } else {
            String[] uriSplit = uri.split("/");
            jsonStr = setLogLevel(Level.parse(uriSplit[(uriSplit.length - 1)]));
          }
        }
        else {
          response.sendError(404);
          return;
        }
      }
      response.setHeader("Content-Type", "application/json");
      response.getOutputStream().print(jsonStr);
    } else {
      response.sendError(405);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    JsonObject obj = null;
    if (authenticateUser(request)) {
      obj = readConfigurationFromRequest(request.getReader());

      String uri = request.getRequestURI();
      if (uri.endsWith("config")) {
        saveProperties(obj);
        response.setHeader("Content-Type", "application/json");
        JsonObject outputJson = new JsonObject();
        outputJson.addProperty("success", Boolean.valueOf(true));
        Gson gson = new Gson();
        response.getOutputStream().print(gson.toJson(outputJson));
      }
      else if (uri.endsWith("authenticate")) {
        return;
      }
    } else {
      response.sendError(405);
    }
  }

  private String getConfiguration(HttpServletRequest request)
  {
    JsonObject obj = null;

    Properties props = loadProperties();
    Enumeration keys = props.keys();

    obj = new JsonObject();
    while (keys.hasMoreElements())
    {
      String key = (String)keys.nextElement();

      obj.addProperty(key, props.getProperty(key));
    }

    Gson gson = new Gson();
    String jsonStr = gson.toJson(obj);
    return jsonStr;
  }

  private String getLogLevel()
  {
    JsonObject obj = new JsonObject();

    obj.addProperty("logLevel", LoggerSetup.getLevel());
    Gson gson = new Gson();
    String jsonStr = gson.toJson(obj);
    return jsonStr;
  }

  private String setLogLevel(Level newLevel)
  {
    LoggerSetup.setLevel(newLevel);

    JsonObject obj = new JsonObject();

    obj.addProperty("success", Boolean.valueOf(true));
    Gson gson = new Gson();
    String jsonStr = gson.toJson(obj);
    return jsonStr;
  }

  public Properties loadProperties()
  {
    try
    {
      Properties props = new Properties();

      props.load(new FileInputStream(getPropertiesFile()));

      return props;
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Could not load ergorr.properties", ex);
    }return null;
  }

  public void saveProperties(JsonObject obj)
    throws IOException
  {
    Properties oldprops = loadProperties();
    File propFile = getPropertiesFile();

    Set entryset = obj.entrySet();
    Iterator iterator = entryset.iterator();

    while (iterator.hasNext()) {
      Entry entry = (Entry)iterator.next();
      oldprops.setProperty((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
    }

    oldprops.store(new FileOutputStream(propFile), null);

    CommonProperties.removeInstance();

    System.setProperty("ergorr.common.properties", getPropertiesFile().getCanonicalPath());
  }

  private JsonObject readConfigurationFromRequest(Reader input)
  {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject)parser.parse(input);

    return obj;
  }

  private File getPropertiesFile()
  {
    return new File(getClass().getClassLoader().getResource("ergorr.properties").getFile());
  }

  private boolean authenticateUser(HttpServletRequest request)
  {
    return true;
  }
}
