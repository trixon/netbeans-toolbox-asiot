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

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Task implements Comparable<Task> {

    private boolean mActive = true;
    private String mCron;
    private String mDescription;
    private String mDestination;
    private long mId = System.currentTimeMillis();
    private String mName;
    private String mUrl;

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
            fmt="<html><b>%s</b><br />%s</html>";
        } else {
            fmt="<html><i>%s<br />%s</i></html>";
        }

        return String.format(fmt, mName, mDescription);
    }

}
