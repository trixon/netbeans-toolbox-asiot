/* 
 * Copyright 2017 Patrik Karlsson.
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
package se.trixon.toolbox.idiot;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import org.apache.commons.io.FileUtils;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 *
 * @author Patrik Karlsson
 */
public enum Options {
    
    INSTANCE;
    private final boolean DEFAULT_ACTIVE = false;
    private final String KEY_ACTIVE = "active";
    private final File mConfigFile;
    private final File mDirectory;
    private final Preferences mPreferences;
    
    private Options() {
        mDirectory = new File(FileUtils.getUserDirectory(), ".config/ttidiot");
        mConfigFile = new File(mDirectory, "tasks.json");
        
        mPreferences = NbPreferences.forModule(this.getClass());
        
        try {
            FileUtils.forceMkdir(mDirectory);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }        
    }
    
    public File getConfigFile() {
        return mConfigFile;
    }
    
    public File getDirectory() {
        return mDirectory;
    }
    
    public boolean isActive() {
        return mPreferences.getBoolean(KEY_ACTIVE, DEFAULT_ACTIVE);
    }
    
    public void setActive(boolean value) {
        mPreferences.putBoolean(KEY_ACTIVE, value);
    }
}
