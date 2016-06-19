/* 
 * Copyright 2016 Patrik Karlsson.
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

import it.sauronsoftware.cron4j.Scheduler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import se.trixon.almond.util.Dict;
import se.trixon.toolbox.idiot.Options;
import se.trixon.toolbox.core.JsonHelper;
import se.trixon.toolbox.idiot.IdiotTopComponent;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public enum TaskManager {

    INSTANCE;
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_CRON = "cron";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DESTINATION = "destination";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TASKS = "tasks";
    private static final String KEY_URL = "url";
    private static final String KEY_VERSION = "version";
    private static final int sVersion = 1;
    private final ResourceBundle mBundle;
    private final InputOutput mInputOutput;

    private final DefaultListModel mModel = new DefaultListModel<>();
    private final Preferences mPreferences;
    private Scheduler mScheduler = new Scheduler();

    private int mVersion;

    private TaskManager() {
        mBundle = NbBundle.getBundle(IdiotTopComponent.class);
        mPreferences = NbPreferences.forModule(this.getClass());
        mInputOutput = IOProvider.getDefault().getIO(mBundle.getString("Tool-Name"), false);
        mInputOutput.select();
    }

    public boolean exists(Task task) {
        boolean exists = false;

        for (int i = 0; i < mModel.getSize(); i++) {
            Task existingTask = (Task) mModel.elementAt(i);
            if (task.getId() == existingTask.getId()) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    public DefaultListModel getModel() {
        return mModel;
    }

    public Task getTaskById(long id) {
        Task foundTask = null;

        for (int i = 0; i < mModel.getSize(); i++) {
            Task task = (Task) mModel.elementAt(i);
            if (task.getId() == id) {
                foundTask = task;
                break;
            }
        }

        return foundTask;
    }

    public int getVersion() {
        return mVersion;
    }

    public boolean hasActiveTasks() {
        Task[] tasks = Arrays.copyOf(mModel.toArray(), mModel.toArray().length, Task[].class);

        for (Task task : tasks) {
            if (task.isActive()) {
                return true;
            }
        }

        return false;
    }

    public void load() throws IOException {
        if (Options.INSTANCE.getConfigFile().exists()) {
            JSONObject jsonObject = (JSONObject) JSONValue.parse(FileUtils.readFileToString(Options.INSTANCE.getConfigFile()));
            mVersion = JsonHelper.getInt(jsonObject, KEY_VERSION);
            JSONArray tasksArray = (JSONArray) jsonObject.get(KEY_TASKS);

            jsonArrayToModel(tasksArray);
        }
    }

    public synchronized void log(String string) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date(System.currentTimeMillis()));

        try (OutputWriter writer = mInputOutput.getOut()) {
            writer.append(String.format("%s %s\n", timeStamp, string));
        }
    }

    public void save() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_TASKS, modelToJsonArray());
        jsonObject.put(KEY_VERSION, sVersion);

        String jsonString = jsonObject.toJSONString();
        FileUtils.writeStringToFile(Options.INSTANCE.getConfigFile(), jsonString);

        //Backup to preferences.
        String tag = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mPreferences.put(tag, jsonString);
        if (Options.INSTANCE.isActive()) {
            restart();
        }
    }

    public void sortModel() {
        Object[] objects = mModel.toArray();
        Arrays.sort(objects);
        mModel.clear();

        for (Object object : objects) {
            mModel.addElement(object);
        }
    }

    public void start() {
        Options.INSTANCE.setActive(true);
        restart();
        notifyStatus();
    }

    public void stop() {
        Options.INSTANCE.setActive(false);
        mScheduler.stop();
        notifyStatus();
    }

    private void jsonArrayToModel(JSONArray array) {
        for (Object arrayItem : array) {
            JSONObject object = (JSONObject) arrayItem;

            Task task = new Task();
            task.setId(JsonHelper.getLong(object, KEY_ID));
            task.setName((String) object.get(KEY_NAME));
            task.setDescription((String) object.get(KEY_DESCRIPTION));
            task.setUrl((String) object.get(KEY_URL));
            task.setDestination((String) object.get(KEY_DESTINATION));
            task.setCron((String) object.get(KEY_CRON));
            task.setActive((boolean) object.get(KEY_ACTIVE));

            mModel.addElement(task);
        }

        sortModel();
    }

    private JSONArray modelToJsonArray() {
        JSONArray array = new JSONArray();

        for (int i = 0; i < mModel.getSize(); i++) {
            Task task = (Task) mModel.elementAt(i);
            JSONObject object = new JSONObject();

            object.put(KEY_ID, task.getId());
            object.put(KEY_NAME, task.getName());
            object.put(KEY_DESCRIPTION, task.getDescription());
            object.put(KEY_URL, task.getUrl());
            object.put(KEY_DESTINATION, task.getDestination());
            object.put(KEY_CRON, task.getCron());
            object.put(KEY_ACTIVE, task.isActive());

            array.add(object);
        }

        return array;
    }

    private void notifyStatus() {
        String status = Options.INSTANCE.isActive() ? Dict.STARTED.getString() : Dict.STOPPED.getString();
        String message = String.format("%s: %s", Dict.DOWNLOAD_SCHEDULED.getString(), status);
        Notification notification = NotificationDisplayer.getDefault().notify(
                mBundle.getString("Tool-Name"),
                new ImageIcon(),
                message,
                null);

        log(message);
    }
    
    private void restart() {
        if (mScheduler.isStarted()) {
            mScheduler.stop();
        }
        
        mScheduler = new Scheduler();
        Task[] tasks = Arrays.copyOf(mModel.toArray(), mModel.toArray().length, Task[].class);
        
        for (Task task : tasks) {
            if (task.isActive()) {
                mScheduler.schedule(task.getCron(), task);
            }
        }

        mScheduler.start();
    }
}
