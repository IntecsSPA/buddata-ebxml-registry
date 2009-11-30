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
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * @author <a href="mailto:yaman@cryptosense.com">Yaman Ustuntas</a>
 */
public class FileUtil {

    /**
     * Delete a single file or a directory recursively.
     * Ingores files which cannot be deleted.
     *
     * @param file File or directory to delete.
     */
    public static void delete(File file) {
        if (file == null) {
            throw new NullPointerException("file must not be null");
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                delete(files[i]);
            }
        }
        file.delete();
    }

    /**
     * Copy file {@code src} to directory {@code dest}.
     *
     * @param src File to copy.
     * @param dest Directory to copy to.
     * @throws IOException
     */
    public static void copy(File src, File dest) throws IOException {

        if (src == null) {
            throw new NullPointerException("src must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("dest must not be null");
        }
        if (!src.exists() || !src.isFile()) {
            throw new IllegalArgumentException("src does not exist or is not a file: " + src.getAbsolutePath());
        }


        if (dest.isDirectory()) {
            dest = new File(dest, src.getName());
        }

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(src).getChannel();
            destinationChannel = new FileOutputStream(dest).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            IoUtil.close(sourceChannel);
            IoUtil.close(destinationChannel);
        }
    }

    /**
     * Read file to byte array.
     *
     * @param file File to read.
     * @return File content as byte array.
     * @throws IOException
     */
    public static byte[] readAsBytes(File file) throws IOException {

        if (file == null) {
            throw new NullPointerException("file must not be null");
        }
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("src does not exist or is not a file: " + file.getAbsolutePath());
        }

        InputStream inStream = new FileInputStream(file);
        long len = file.length();

        try {
            if (len > Integer.MAX_VALUE) {
                throw new IOException("File too large, " + len + " bytes. (max " + Integer.MAX_VALUE + ")");
            }

            return IoUtil.toByteArray(inStream, (int) file.length());
        } finally {
            IoUtil.close(inStream);
        }
    }

    /**
     * Read file to String.
     *
     * @param file File to read.
     * @return File content as String,
     * @throws IOException
     */
    public static String readAsString(File file) throws IOException {

        if (file == null) {
            throw new NullPointerException("file must not be null");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("src does not exist or is not a file: " + file.getAbsolutePath());
        }

        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int count = 0;

        try {
            while ((count = reader.read(buf)) > 0) {
                String readData = String.valueOf(buf, 0, count);
                fileData.append(readData);
                buf = new char[1024];
            }
        } finally {
            IoUtil.close(reader);
        }

        return fileData.toString();
    }

    /**
     * Write String {@code data} to {@code file} path.
     *
     * @param file File to write to.
     * @param data Data to write.
     * @throws IOException
     */
    public static void write(File file, String data) throws IOException {

        if (file == null) {
            throw new NullPointerException("file must not be null");
        }
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("src does not exist or is not a file: " + file.getAbsolutePath());
        }
        if (data == null) {
            data = "";
        }

        write(file, data.getBytes(IoUtil.ENCODING));
    }

    /**
     * Write byte array {@code data} to {@code file} path.
     *
     * @param file File to write to.
     * @param data Data to write.
     * @throws IOException
     */
    public static void write(File file, byte[] data) throws IOException {

        if (file == null) {
            throw new NullPointerException("file must not be null");
        }
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("src does not exist or is not a file: " + file.getAbsolutePath());
        }
        if (data == null) {
            data = new byte[0];
        }

        InputStream input = new ByteArrayInputStream(data);
        OutputStream output = new FileOutputStream(file);

        try {
            IoUtil.copy(input, output);
        } finally {
            IoUtil.close(input);
            IoUtil.close(output);
        }
    }

    /**
     * Check if the content of {@code file1} and {@code file2} are equal.
     *
     * @param file1 File to compare.
     * @param file2 File to compare.
     * @throws IOException
     */
    public static void contentEquals(File file1, File file2) throws IOException {
        if (file1 == null) {
            throw new NullPointerException("file1 must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("file2 must not be null");
        }
        if (!file1.isFile()) {
            throw new IllegalArgumentException("file1 does not exist or is not a file: " + file1.getAbsolutePath());
        }
        if (!file2.isFile()) {
            throw new IllegalArgumentException("file2 does not exist or is not a file: " + file2.getAbsolutePath());
        }

        InputStream input1 = null;
        InputStream input2 = null;

        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            IoUtil.contentEquals(input1, input2);
        } finally {
            IoUtil.close(input1);
            IoUtil.close(input2);
        }
    }
}
