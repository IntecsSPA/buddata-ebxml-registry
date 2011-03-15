/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.intecs.pisa.util.json;

import com.google.gson.JsonObject;

/**
 *
 * @author Massimiliano Fanciulli
 */
public class JsonSuccessObject {
    public static JsonObject get()
    {
        JsonObject outputJson = new JsonObject();
        outputJson.addProperty("success", true);
        
        return outputJson;
    }
}
