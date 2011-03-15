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
public class JsonErrorObject {
    public static JsonObject get(String errorMsg)
    {
        JsonObject outputJson = new JsonObject();
        outputJson.addProperty("success", false);
        outputJson.addProperty("reason", errorMsg);

        return outputJson;
    }
}
