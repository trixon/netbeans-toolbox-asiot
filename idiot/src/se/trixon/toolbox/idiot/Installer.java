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
package se.trixon.toolbox.idiot;

import java.io.IOException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;
import se.trixon.almond.Xlog;
import se.trixon.toolbox.idiot.task.TaskManager;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Installer extends ModuleInstall {

    @Override
    public boolean closing() {
        boolean exit = true;

        if (Options.INSTANCE.isActive() && TaskManager.INSTANCE.hasActiveTasks()) {
            NotifyDescriptor d = new NotifyDescriptor(
                    NbBundle.getMessage(Installer.class, "confirmExit"),
                    NbBundle.getMessage(Installer.class, "Tool-Name"),
                    NotifyDescriptor.YES_NO_OPTION,
                    NotifyDescriptor.WARNING_MESSAGE,
                    null,
                    null);
            Object result = DialogDisplayer.getDefault().notify(d);
            exit = result == NotifyDescriptor.YES_OPTION;
        }

        return exit;
    }

    @Override
    public void restored() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            try {
                TaskManager.INSTANCE.load();
                if (Options.INSTANCE.isActive()) {
                    TaskManager.INSTANCE.start();
                }

            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            Xlog.select();
        });
    }
}
