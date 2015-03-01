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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Tools",
        id = "se.trixon.toolbox.asiot.AsiotAction"
)
@ActionRegistration(
        displayName = "#Tool-NameAction"
)
@ActionReference(path = "Menu/Tools", position = 0)
public final class AsiotAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent topComponent = WindowManager.getDefault().findTopComponent("AsiotTopComponent");
        topComponent.open();
        topComponent.requestActive();
    }
}
