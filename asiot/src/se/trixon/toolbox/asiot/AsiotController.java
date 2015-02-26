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

import java.util.ResourceBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import se.trixon.toolbox.core.ToolProvider;
import se.trixon.toolbox.core.base.ToolController;
import se.trixon.toolbox.core.base.ToolTopComponent;
import se.trixon.almond.news.NewsProvider;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProviders(value = {
    @ServiceProvider(service = ToolProvider.class),
    @ServiceProvider(service = NewsProvider.class)}
)
public class AsiotController extends ToolController {

    public static final String LOG_TAG = "Asiot";

    public AsiotController() {
    }

    public AsiotController(ToolTopComponent toolTopComponent) {
        super(toolTopComponent);
    }

    @Override
    public ResourceBundle getNewsBundle() {
        return ResourceBundle.getBundle(getPackageAsPath() + "news");
    }
}
