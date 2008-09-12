/*
 * Project: Buddata ebXML RegRep
 * Class: LocalizedStringDM.java
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
package be.kzen.ergorr.persist.model;

import be.kzen.ergorr.persist.*;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Yaman Ustuntas
 */
@MappedSuperclass
public abstract class LocalizedStringDM implements Serializable {

    @Id
    @GeneratedValue
    protected long id;
    protected String lang;
    protected String charset;
    @Column(name = "value_")
    protected String value;
    @ManyToOne
    @JoinColumn(name = "parent")
    protected RegistryObjectDM parent;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public RegistryObjectDM getParent() {
        return parent;
    }

    public void setParent(RegistryObjectDM parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public LocalizedStringType createJaxb() {
        LocalizedStringType localString = new LocalizedStringType();
        localString.setCharset(charset);
        localString.setLang(lang);
        localString.setValue(value);
        return localString;
    }
}
