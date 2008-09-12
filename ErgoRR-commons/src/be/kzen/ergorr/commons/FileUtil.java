/*
 * Project: Buddata ebXML RegRep
 * Class: FileUtil.java
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
package be.kzen.ergorr.commons;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * @author <a href="mailto:yaman@cryptosense.com">Yaman Ustuntas</a>
 */
public class FileUtil {
    
    /**
     * Deletes a file or directory (recursive).
     * Will continue if a deletion fails.
     *
     * @param f File to be deleted
     */
    public static void delete(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            
            for (int i = 0; i < files.length; i++) {
                delete(files[i]);
            }
        }
        f.delete();
    }
    
    /**
     * Does what it says
     *
     * @param in src file
     * @param out dest file
     * @throws IOException guess when this one occurs :)
     */
    public static void copyFile(File src, File dest) throws IOException {
        if (dest.isDirectory()) {
            dest = new File(dest, src.getName());
        }
        FileChannel sourceChannel = new FileInputStream(src).getChannel();
        FileChannel destinationChannel = new FileOutputStream(dest).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }
    
    /**
     * Read a file into a byte array.
     *
     * @param file File to read.
     * @throws IOException
     * @return File content as byte array.
     */
    public static byte[] readFileAsByte(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File too large, " + length + " bytes. (max " + Integer.MAX_VALUE + ")");
        }
        
        byte[] bytes = new byte[(int)length];
        
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        
        is.close();
        return bytes;
    }
    
    /**
     * Read a file into a String.
     *
     * @param file File to read.
     * @throws java.io.IOException
     * @return File content as String.
     */
    public static String readFileAsString(File file) throws IOException{
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int count = 0;
        
        while((count = reader.read(buf)) > 0){
            String readData = String.valueOf(buf, 0, count);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
    
    public static String streamToString(InputStream isStream) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(isStream));
        char[] buf = new char[1024];
        int count = 0;
        
        while((count = reader.read(buf)) > 0){
            String readData = String.valueOf(buf, 0, count);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
    
    /**
     * Writes String data to the given path (UTF-8).
     *
     * @param file File path to save the data.
     * @param data String data to save.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void writeFile(File file, String data) throws FileNotFoundException, IOException {
        writeFile(file, data.getBytes("UTF-8"));
    }
    
    /**
     * Writes data byte array to the given path.
     *
     * @param file File path to save the data.
     * @param data Data to write in the file.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void writeFile(File file, byte[] data) throws FileNotFoundException, IOException {
        writeFile(file, new ByteArrayInputStream(data));
    }
    
    /**
     * Write data from an <code>InputStream</code> to file.
     *
     * @param file File to write to.
     * @param is Stream to write.
     * @throws java.io.IOException
     */
    public static void writeFile(File file, InputStream is) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[2048];
        int count = 0;
        while ((count = is.read(buf)) > 0) {
            fos.write(buf, 0, count);
        }
        fos.flush();
        fos.close();
        is.close();
    }
    
    /**
     * Gets the files extension.
     * 
     * @param file File to get extension.
     * @return Extension of file.
     * @throws java.io.FileNotFoundException
     */
    public static String getExtension(File file) throws FileNotFoundException {
        
        if (file.isFile()) {
            int idx = file.getName().lastIndexOf(".");
            
            if (idx > 0 && idx+1 < file.getName().length()) {
                return file.getName().substring(idx+1);
            } else {
                return "";
            }
        } else {
            throw new FileNotFoundException("Could not find " + file.getAbsolutePath());
        }
    }
    /**
     * Get a new temporary file new. Appends "(-count-)" to the end of the file.
     * For example: myFile.txt(0), if it exist then myFile.txt(1), ...
     * 
     * @param originalFile File to create temporary file for.
     * @return New temporary file.
     */
    public static File getNewTempFile(File originalFile) {
        int count = 0;
        String ext = "";
        File tmpFile;

        do {
            ext = new StringBuilder().append("(").append(count).append(")").toString();
            tmpFile = new File(originalFile.getAbsolutePath() + ext);
            count++;
        } while (tmpFile.exists());
        
        return tmpFile;
    }
}
