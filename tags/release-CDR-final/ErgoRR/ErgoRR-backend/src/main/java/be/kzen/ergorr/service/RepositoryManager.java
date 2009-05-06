/*
 * Project: Buddata ebXML RegRep
 * Class: RepositoryManager.java
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

import be.kzen.ergorr.commons.CommonProperties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Manages reading and writing to the repository.
 *
 * @author yamanustuntas
 */
public class RepositoryManager {

    private static final String REPO_ROOT = CommonProperties.getInstance().get("repository.root");
    private File repoDir;

    /**
     * Constructor.
     * Uses default deployName from common.properties.
     */
    public RepositoryManager() {
        this(CommonProperties.getInstance().get("deployName"));
    }

    /**
     * Constructor.
     *
     * @param deployName Deployment name which defines the repository directory.
     */
    public RepositoryManager(String deployName) {
        repoDir = new File(REPO_ROOT, deployName);
    }

    /**
     * Save a repository item.
     *
     * @param objId ID of repository item.
     * @param dataHandler Data to save.
     * @throws java.io.IOException
     */
    public void save(String objId, String mimeType, DataHandler dataHandler) throws IOException {
        initRepo();
        InputStream inStream = dataHandler.getInputStream();
        File repoFile = getRepositoryItemFile(objId);

        OutputStream outStream = new FileOutputStream(repoFile);
        byte[] buf = new byte[1024];
        int len;
        while ((len = inStream.read(buf)) > 0) {
            outStream.write(buf, 0, len);
        }
        outStream.close();
        inStream.close();
    }

    /**
     * Check if repository item exists.
     *
     * @param objId ID of the object.
     * @return True if repository item exists.
     */
    public boolean exists(String objId) {
        File repoFile = getRepositoryItemFile(objId);
        return repoFile.exists();
    }

    public File getFile(String objId) {
        return getRepositoryItemFile(objId);
    }

    /**
     * Get repository item.
     *
     * @param objId Object id whoes repository item to get.
     * @return DataHandler of repository file.
     * If no repository file exists, returns <code>null</code>.
     */
    public DataHandler get(String objId) {
        DataHandler dataHandler = null;
        File repoFile = getRepositoryItemFile(objId);

        if (repoFile.exists()) {
            dataHandler = new DataHandler(new FileDataSource(repoFile));
        }
        return dataHandler;
    }

    /**
     * Delete a repository item.
     *
     * @param objId ID of object to delete.
     */
    public void delete(String objId) {
        File repoFile = getRepositoryItemFile(objId);

        if (repoFile.exists()) {
            repoFile.delete();
        }
    }

    /**
     * Creates the repository directory if it doesn't exist.
     */
    private void initRepo() {
        if (!repoDir.exists()) {
            repoDir.mkdirs();
        }
    }

    /**
     * Get the repository item <code>File</code>
     * from the objects ID.
     *
     * @param id ID of repository item.
     * @return File.
     */
    private File getRepositoryItemFile(String id) {
        String t = id.replaceAll(":", "_");
        return new File(repoDir, t);
    }
}
