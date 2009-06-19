/*
 * Project: Buddata ebXML RegRep
 * Class: CapabilitiesReader.java
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
package be.kzen.ergorr.service;

import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.xpath.XMLNamespaces;
import be.kzen.ergorr.xpath.XPathUtil;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author yamanustuntas
 */
public class CapabilitiesReader {
    private Document cababilities;
    private XPathUtil xpath;

    public CapabilitiesReader() {
        XMLNamespaces ns = new XMLNamespaces();
        ns.addNamespaceURI("rim", NamespaceConstants.RIM);
        ns.addNamespaceURI("wrs", NamespaceConstants.WRS);
        ns.addNamespaceURI("ows", NamespaceConstants.OWS);
        ns.addNamespaceURI("gml", NamespaceConstants.GML);
        ns.addNamespaceURI("ogc", NamespaceConstants.OGC);
        xpath = new XPathUtil(ns);
    }

    public void load() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        cababilities = docBuilder.parse(this.getClass().getResourceAsStream("/resources/Capabilities.xml"));
    }

    public XPathUtil getXpath() {
        return xpath;
    }

    public Document getCababilities() {
        return cababilities;
    }
}
