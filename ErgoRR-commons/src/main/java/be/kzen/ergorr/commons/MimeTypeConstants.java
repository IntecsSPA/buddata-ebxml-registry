/*
 * Project: Buddata ebXML RegRep
 * Class: MimeTypeConstants.java
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

/**
 * MimeType constants.
 * 
 * @author yamanustuntas
 */
public class MimeTypeConstants {

    private MimeTypeConstants() {
    }

    public static final String APPLICATION_GZIP = "application/x-gzip";
    public static final String APPLICATION_JAVASCRIPT = "application/x-javascript";
    public static final String APPLICATION_MSWORD = "application/msword";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_ZIP = "application/zip";
    public static final String APPLICATION_XML = "application/xml";

    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";

    public static final String TEXT_CSS = "text/css";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_XML = "text/xml"; // alias for APPLICATION_XML
}
