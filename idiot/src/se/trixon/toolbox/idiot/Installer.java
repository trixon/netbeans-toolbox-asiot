/*
 * Copyright 2015 pata.
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

import java.io.IOException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import se.trixon.toolbox.idiot.task.TaskManager;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        try {
            TaskManager.INSTANCE.load();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
