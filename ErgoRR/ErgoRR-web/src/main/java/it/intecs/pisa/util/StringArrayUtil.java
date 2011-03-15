/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.intecs.pisa.util;

/**
 *
 * @author Massimiliano Fanciulli
 */
public class StringArrayUtil {
    /**
     * This method adds an empty item in the string array.
     * The content of the input array is assigned to the output array, not cloned.
     * @param array
     * @return
     */
    public static String[] addEmptyItemOnTop(String[] array)
    {
        String[] newArray;
        int i=1;

        newArray=new String[array.length+1];
        newArray[0]="";
        for(String item:array)
        {
            newArray[i]=item;
            i++;
        }
        
        return newArray;
    }

    
}
