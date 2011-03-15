package it.intecs.pisa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void extractZipFile(String zipPath, String destination) {
        ZipFile zipFile = null;
        File inputFile = null;
        Enumeration<ZipEntry> enumeration = null;
        ZipEntry entry = null;
        InputStream stream = null;
        String destFilePath = null;
        String entryName=null;
        try {
            inputFile = new File(zipPath);
            zipFile = new ZipFile(inputFile);

            enumeration = (Enumeration<ZipEntry>) zipFile.entries();
            while (enumeration.hasMoreElements()) {
                entry = enumeration.nextElement();

                if (entry.isDirectory()) {
                    extractDir(entry, destination);
                } else {
                    stream = zipFile.getInputStream(entry);
                    
                    entryName= entry.getName();
                    destFilePath = destination + File.separator +entryName.replace('/', File.separatorChar);
                    
                    extractFile(stream, destFilePath);
                }
            }

            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private static void extractFile(InputStream stream, String destination) {
        byte[] buf = new byte[1024];
        FileOutputStream out = null;
        int len = 0;
        String outputDirStr;
        File destFile;
        File dir;
        
        try {
            //searching the / char because all path into the zip file use this char as dir separator
            outputDirStr=destination.substring(0, destination.lastIndexOf(File.separatorChar));
            dir=new File(outputDirStr);
            dir.mkdirs();
            // createDir(outputDirStr);
            
            destFile = new File(destination);

            out = new FileOutputStream(destFile);

            while (stream != null && (len = stream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            stream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createDir(String absolutePath) {
        StringTokenizer tokenizer;
        File toBeCreated = null;
        String subPath = null;

        try {
            tokenizer = new StringTokenizer(absolutePath, "/");


            if (tokenizer.hasMoreElements()) {
                subPath = tokenizer.nextToken();
                toBeCreated = new File(subPath);

                //if(toBeCreated.exists()==false)
                toBeCreated.mkdir();
            }

            while (tokenizer.hasMoreElements()) {
                toBeCreated = new File(toBeCreated, (String) tokenizer.nextElement());

                //if(toBeCreated.exists()==false)
                toBeCreated.mkdir();

              //  subPath = toBeCreated.getPath();//.getCanonicalPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractDir(ZipEntry entry, String destination) {
        String name = null;
        StringTokenizer tokenizer = null;
        File toBeCreated = null;
        String subPath = null;

        try {
            name = entry.getName();
            createDir(destination, name);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createDir(String destination, String name) throws IOException {
        StringTokenizer tokenizer = null;
        File toBeCreated = null;
        String subPath = null;

        tokenizer = new StringTokenizer(name, "/");

        subPath = destination;

        while (tokenizer.hasMoreElements()) {
            toBeCreated = new File(subPath, (String) tokenizer.nextElement());

            //if(toBeCreated.exists()==false)
            toBeCreated.mkdir();

            subPath = toBeCreated.getCanonicalPath();
        }
    }

    public static void zipDirectory(String zipFileName, String directoryPath, boolean includeDir) {
        zipDirectory(zipFileName, directoryPath, includeDir, null);
    }

    public static void zipDirectory(String zipFileName, String directoryPath, boolean includeDir, AbstractFileFilter filter) {
        //		 Create a buffer for reading the files
        byte[] buf = new byte[1024];
        String[] entries = null;
        File file = null;
        String fullpath = null;
        FileInputStream in = null;
        String parentDir = null;
        String zipEntryName = null;

        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

            entries = parseDirForFiles(directoryPath, filter);

            for (String entry : entries) {
                file = new File(directoryPath, entry);

                fullpath = file.getAbsolutePath();

                try {
                    in = new FileInputStream(fullpath);
                } catch (Exception e) {
                    in = null;
                }

                // Add ZIP entry to output stream.
                zipEntryName = new String();

                if (includeDir == true) {
                    zipEntryName += directoryPath.substring(directoryPath.lastIndexOf(File.separator)) + "/";
                //zipEntryName=;
                }
                zipEntryName += entry;

                //temporary.. find a better check
                if (in == null) {
                    zipEntryName += "/";
                }

                out.putNextEntry(new ZipEntry(zipEntryName.replace(File.separatorChar, '/')));

                //  Transfer bytes from the file to the ZIP file
                int len;
                while (in != null && (len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                if (in != null) {
                    in.close();
                }
            }
            out.close();
        } catch (Exception ecc) {
            ecc.printStackTrace();

        }
    }

    private static String[] parseDirForFiles(String directoryPath, AbstractFileFilter filter) {
        Vector<String> entriesVector = null;
        File dir = null;
        try {
            dir = new File(directoryPath);

            if (dir.isDirectory() == false) {
                return new String[0];
            }

            entriesVector = scanDir(dir, directoryPath, filter);

            //entries=entriesVector.
            return (String[]) entriesVector.toArray(new String[1]);

        } catch (Exception e) {
            return new String[0];
        }
    }

    private static Vector<String> scanDir(File dir, String directoryPath, AbstractFileFilter filter) {
        File childFile = null;
        String[] children = null;
        Vector<String> vector = null;
        Vector<String> childVector = null;
        String currenRelativetPath = null;
        String fileName;
        try {
            vector = new Vector<String>();

            children = dir.list();
            for (String child : children) {
                childFile = new File(dir.getAbsolutePath(), child);
                fileName=childFile.getName();
                if (fileName.startsWith(".") == false ||
                		(fileName.startsWith(".") == true && dir.getAbsolutePath().indexOf("Operations")>-1)) {
                    currenRelativetPath = childFile.getAbsolutePath().substring(directoryPath.length() + 1);
                    if (childFile.isDirectory()) {

                        if (filter == null || filter.acceptFile(currenRelativetPath)) {
                            childVector = scanDir(childFile, directoryPath, filter);
                            vector.addAll(childVector);
                        }

                    } else {
                        if (filter == null || filter.acceptFile(currenRelativetPath)) {
                            vector.add(currenRelativetPath);
                        }
                    }
                }
            }

            if (children.length == 0) {
                vector.add(dir.getAbsolutePath().substring(directoryPath.length() + 1));
            }

            return vector;
        } catch (Exception ecc) {
            ecc.printStackTrace();
            return null;
        }


    }
}
