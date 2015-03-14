/* 
 * Copyright 2015 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.toolbox.idiot.task;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.openide.util.Exceptions;
import se.trixon.almond.dictionary.Dict;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Task implements Comparable<Task>, Runnable, Cloneable {

    public static final DownloadListener DEFAULT_DOWNLOAD_LISTENER = new DownloadListener() {

        @Override
        public void onDownloadFailed(Task task, IOException ex) {
        }

        @Override
        public void onDownloadFinished(Task task, File destFile) {
        }

        @Override
        public void onDownloadStarted(Task task) {
        }
    };

    private boolean mActive = true;
    private String mCron = "0 * * * *";
    private String mDescription;
    private String mDestination;
    private DownloadListener mDownloadListener = DEFAULT_DOWNLOAD_LISTENER;
    private long mId = System.currentTimeMillis();
    private String mName;
    private String mUrl;

    public Task() {
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task t = (Task) super.clone();
        t.setId(System.currentTimeMillis());
        return t;
    }

    @Override
    public int compareTo(Task o) {
        return mName.compareTo(o.getName());
    }

    public String getCron() {
        return mCron;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDestination() {
        return mDestination;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isActive() {
        return mActive;
    }

    public boolean isValid() {
        return !getName().isEmpty() && !getUrl().isEmpty() && !getDestination().isEmpty();
    }

    @Override
    public void run() {
//        if (!isActive()) {
//            return;
//        }

        new Thread(() -> {
            mDownloadListener.onDownloadStarted(this);
            try {
                URL url = new URL(getUrl());
                File destFile = getDestPath();
                FileUtils.copyURLToFile(url, destFile, 15000, 15000);

                String message = String.format("%s: %s", Dict.DOWNLOAD_COMPLETED.getString(), destFile.getAbsolutePath());
                TaskManager.INSTANCE.log(message);
                logToFile(message);

                mDownloadListener.onDownloadFinished(this, destFile);
            } catch (IOException ex) {
                String message = String.format("%s: %s", Dict.DOWNLOAD_FAILED.getString(), ex.getLocalizedMessage());
                TaskManager.INSTANCE.log(message);
                logToFile(message);

                mDownloadListener.onDownloadFailed(this, ex);
            }
        }).start();
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public void setCron(String cron) {
        mCron = cron;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        String fmt;

        if (mActive) {
            fmt = "<html><b>%s</b><br />%s</html>";
        } else {
            fmt = "<html><i>%s<br />%s</i></html>";
        }

        return String.format(fmt, mName, mDescription);
    }

    private File getDestPath() {
        File destPath = new File(getDestination());
        String ext = FilenameUtils.getExtension(destPath.getAbsolutePath());
        String baseName = FilenameUtils.getBaseName(destPath.getAbsolutePath());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date(System.currentTimeMillis()));

        String filename;
        if (ext.isEmpty()) {
            filename = String.format("%s_%s", baseName, timeStamp);
        } else {
            filename = String.format("%s_%s.%s", baseName, timeStamp, ext);

        }

        return new File(destPath.getParent(), filename);
    }

    private synchronized void logToFile(String message) {
        File dir = new File(mDestination).getParentFile();
        String basename = FilenameUtils.getBaseName(mDestination);
        String ext = FilenameUtils.getExtension(mDestination);
        if (ext.equalsIgnoreCase("log")) {
            ext = "log.log";
        } else {
            ext = "log";
        }

        File logFile = new File(dir, String.format("%s.%s", basename, ext));
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date(System.currentTimeMillis()));

        message = String.format("%s %s%s", timeStamp, message, IOUtils.LINE_SEPARATOR);

        try {
            FileUtils.writeStringToFile(logFile, message, true);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public interface DownloadListener {

        void onDownloadFailed(Task task, IOException ex);

        void onDownloadFinished(Task task, File destFile);

        void onDownloadStarted(Task task);
    }
}
