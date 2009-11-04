package be.kzen.ergorr.commons;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * IO utility class.
 * 
 * @author Yaman Ustuntas
 */
public class IoUtil {

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final String ENCODING = CommonProperties.getInstance().get("encoding");

    public static void copy(String data, OutputStream output) throws IOException {
        if (data != null) {
            output.write(data.getBytes());
        }
    }

    public static void copy(String data, OutputStream output, String encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }
    }

    public static byte[] toByteArray(InputStream inStream, int size) throws IOException {
        byte[] bytes = new byte[size];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = inStream.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        return bytes;
    }

    public static byte[] toByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        copy(inStream, outStream);
        return outStream.toByteArray();
    }

    public static String toString(InputStream input, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int readBytes = 0;
        long totalCopied = 0;

        while ((readBytes = input.read(buf)) > 0) {
            output.write(buf, 0, readBytes);
            totalCopied += readBytes;
        }

        return totalCopied;
    }

    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        copy(new InputStreamReader(input, encoding), output);
    }

    public static void copy(InputStream input, Writer output) throws IOException {
        copy(new InputStreamReader(input), output);
    }

    public static long copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int readChars = 0;
        long totalCopied = 0;

        while (-1 != (readChars = input.read(buffer))) {
            output.write(buffer, 0, readChars);
            totalCopied += readChars;
        }

        return totalCopied;
    }

    public static void copy(Reader input, OutputStream output) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output);
        copy(input, out);
        out.flush(); // needs to flush the underlying StreamEncoder.
    }

    public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, encoding);
        copy(input, out);
        out.flush(); // needs to flush the underlying StreamEncoder.
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }

        int ch1 = input1.read();
        
        while (-1 != ch1) {
            int ch2 = input2.read();
            
            if (ch1 != ch2) {
                return false;
            }
            ch1 = input1.read();
        }

        int ch2 = input2.read();
        return (ch2 == -1);
    }

    public static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // ignore
            }
        }
    }
}
