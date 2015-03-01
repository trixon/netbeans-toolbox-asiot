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
package se.trixon.toolbox.asiot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import se.trixon.toolbox.asiot.task.Task;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Downloader {

    private Task mTask;
    private DownloadListener mDownloadListener;

    public Downloader() {
    }

    public Downloader(Task task) {
        mTask = task;
    }

    public void download() {
        if (!mTask.isActive()) {
            return;
        }

        new Thread(() -> {
            mDownloadListener.onDownloadStarted(mTask);
            try {
                URL url = new URL(mTask.getUrl());
                File destFile = getDestPath();
                FileUtils.copyURLToFile(url, destFile, 5000, 5000);
                mDownloadListener.onDownloadFinished(mTask, destFile);
            } catch (IOException ex) {
                mDownloadListener.onDownloadFailed(mTask, ex);
            }
        }).start();
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    private File getDestPath() {
        File destPath = new File(mTask.getDestination());
        String ext = FilenameUtils.getExtension(destPath.getAbsolutePath());
        String baseName = FilenameUtils.getBaseName(destPath.getAbsolutePath());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis()));

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
