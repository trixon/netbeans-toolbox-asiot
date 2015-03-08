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
import se.trixon.almond.dictionary.Dict;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Task implements Comparable<Task>, Runnable {

    private boolean mActive = true;
    private String mCron = "0 * * * *";
    private String mDescription;
    private String mDestination;
    private DownloadListener mDownloadListener = sDefaultDownloadListener;
    private long mId = System.currentTimeMillis();
    private String mName;
    private String mUrl;
    private static final DownloadListener sDefaultDownloadListener = new DownloadListener() {

        @Override
        public void onDownloadFailed(Task task, IOException ex) {
            String message = String.format("%s: %s %s", Dict.DOWNLOAD_FAILED.getString(), task.getName(), ex.getLocalizedMessage());
            TaskManager.INSTANCE.log(message);
        }

        @Override
        public void onDownloadFinished(Task task, File destFile) {
            String message = String.format("%s: %s %s", Dict.DOWNLOAD_COMPLETED.getString(), task.getName(), destFile.getAbsolutePath());
            TaskManager.INSTANCE.log(message);
        }

        @Override
        public void onDownloadStarted(Task task) {
        }
    };

    public Task() {
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
                mDownloadListener.onDownloadFinished(this, destFile);
            } catch (IOException ex) {
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

        if (ext.isEmpty()) {
            ext = "jpg";
        }

        String filename = String.format("%s_%s.%s", baseName, timeStamp, ext);

        return new File(destPath.getParent(), filename);
    }

    public interface DownloadListener {

        void onDownloadFailed(Task task, IOException ex);

        void onDownloadFinished(Task task, File destFile);

        void onDownloadStarted(Task task);
    }
}
