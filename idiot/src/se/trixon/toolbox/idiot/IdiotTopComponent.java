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

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import se.trixon.almond.dialogs.Message;
import se.trixon.almond.dictionary.Dict;
import se.trixon.almond.icon.Pict;
import se.trixon.toolbox.idiot.task.Task;
import se.trixon.toolbox.core.base.ToolTopComponent;
import se.trixon.toolbox.idiot.task.Task.DownloadListener;
import se.trixon.toolbox.idiot.task.TaskManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//se.trixon.toolbox.idiot//Idiot//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "IdiotTopComponent",
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
public final class IdiotTopComponent extends ToolTopComponent {

    public static final int ICON_SIZE = TOOLBAR_ICON_SIZE;
    private final IdiotController mController;
    private final TaskManager mTaskManager = TaskManager.INSTANCE;

    public IdiotTopComponent() {
        mBundle = NbBundle.getBundle(IdiotTopComponent.class);
        mToolName = mBundle.getString("Tool-Name");
        initComponents();
        setName(mToolName);
        mController = new IdiotController(this);
        init();

        cronToggleButton.setSelected(Options.INSTANCE.isActive());
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(mHelpId);
    }

    private void init() {
        mHelpId = "se.trixon.toolbox.idiot.about";

        cronToggleButton.setIcon(Pict.Actions.DOWNLOAD_LATER.get(ICON_SIZE));
        cronToggleButton.setToolTipText(Dict.DOWNLOADS_SCHEDULE.getString());

        downloadButton.setIcon(Pict.Actions.DOWNLOAD.get(ICON_SIZE));
        downloadButton.setToolTipText(Dict.DOWNLOAD_NOW.getString());

        openDirectoryButton.setIcon(Pict.Actions.FOLDER_DOWNLOADS.get(ICON_SIZE));
        openDirectoryButton.setToolTipText(Dict.OPEN_DIRECTORY.getString());

        addButton.setIcon(Pict.Actions.LIST_ADD.get(ICON_SIZE));
        editButton.setIcon(Pict.Actions.DOCUMENT_EDIT.get(ICON_SIZE));
        cloneButton.setIcon(Pict.Actions.EDIT_COPY.get(ICON_SIZE));
        cloneButton.setToolTipText(Dict.CLONE.getString());
        removeButton.setIcon(Pict.Actions.LIST_REMOVE.get(ICON_SIZE));
        removeAllButton.setIcon(Pict.Actions.EDIT_DELETE.get(ICON_SIZE));

        helpButton.setIcon(Pict.Actions.HELP_CONTENTS.get(ICON_SIZE));
        helpButton.setToolTipText(Dict.HELP.getString());

        tasksPanel.getList().addListSelectionListener((ListSelectionEvent e) -> {

            if (!e.getValueIsAdjusting()) {
                selectionChanged();
            }
        });

        tasksPanel.getList().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                initImageViewer();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

        tasksPanel.getList().getModel().addListDataListener(new ListDataListener() {

            @Override
            public void contentsChanged(ListDataEvent e) {
                dataChanged();
            }

            @Override
            public void intervalAdded(ListDataEvent e) {
                dataChanged();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                dataChanged();
            }
        });

        selectionChanged();
    }

    private void dataChanged() {
        boolean active = tasksPanel.getList().getModel().getSize() > 0;
        removeAllButton.setEnabled(active);
    }

    private void initImageViewer() {
        Task task = tasksPanel.getSelectedTask();
        if (task == null) {
            return;
        }

        File taskFile = new File(task.getDestination());
        File dir = taskFile.getParentFile();
        String basename = FilenameUtils.getBaseName(taskFile.getAbsolutePath());
        String ext = FilenameUtils.getExtension(taskFile.getAbsolutePath());
        StringBuilder builder = new StringBuilder(basename).append("_????-??-??_??.??.??");
        if (StringUtils.isNotEmpty(ext)) {
            builder.append(".").append(ext);
        }

        FileFilter fileFilter = new WildcardFileFilter(builder.toString());
        File[] files = dir.listFiles(fileFilter);
        Arrays.sort(files);

        imageViewPanel.addReplace(files);
    }

    private void selectionChanged() {
        boolean active = tasksPanel.getList().getSelectedIndex() > -1;
        downloadButton.setEnabled(active);
        openDirectoryButton.setEnabled(active);
        editButton.setEnabled(active);
        cloneButton.setEnabled(active);
        removeButton.setEnabled(active);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        cronToggleButton = new javax.swing.JToggleButton();
        downloadButton = new javax.swing.JButton();
        openDirectoryButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        addButton = new javax.swing.JButton();
        cloneButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        removeAllButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        helpButton = new javax.swing.JButton();
        splitPanel = new javax.swing.JSplitPane();
        tasksPanel = new se.trixon.toolbox.idiot.TasksPanel();
        imageViewPanel = new se.trixon.almond.imageviewer.ImageViewPanel();

        setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);

        cronToggleButton.setFocusable(false);
        cronToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cronToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cronToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cronToggleButtonActionPerformed(evt);
            }
        });
        toolBar.add(cronToggleButton);

        downloadButton.setFocusable(false);
        downloadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        downloadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });
        toolBar.add(downloadButton);

        openDirectoryButton.setFocusable(false);
        openDirectoryButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openDirectoryButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDirectoryButtonActionPerformed(evt);
            }
        });
        toolBar.add(openDirectoryButton);
        toolBar.add(jSeparator2);

        addButton.setToolTipText(Dict.ADD.getString());
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        toolBar.add(addButton);

        cloneButton.setFocusable(false);
        cloneButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cloneButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cloneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cloneButtonActionPerformed(evt);
            }
        });
        toolBar.add(cloneButton);

        editButton.setToolTipText(Dict.EDIT.getString());
        editButton.setFocusable(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        toolBar.add(editButton);

        removeButton.setToolTipText(Dict.REMOVE.getString());
        removeButton.setFocusable(false);
        removeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        toolBar.add(removeButton);

        removeAllButton.setToolTipText(Dict.REMOVE_ALL.getString());
        removeAllButton.setFocusable(false);
        removeAllButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeAllButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllButtonActionPerformed(evt);
            }
        });
        toolBar.add(removeAllButton);
        toolBar.add(jSeparator1);

        helpButton.setFocusable(false);
        helpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        helpButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        toolBar.add(helpButton);

        add(toolBar, java.awt.BorderLayout.PAGE_START);

        splitPanel.setLeftComponent(tasksPanel);
        splitPanel.setRightComponent(imageViewPanel);

        add(splitPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        tasksPanel.editTask(null);
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        Task task = tasksPanel.getSelectedTask();
        if (task != null) {
            tasksPanel.editTask(task);
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        tasksPanel.removeTask();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllButtonActionPerformed
        tasksPanel.removeAllTasks();
    }//GEN-LAST:event_removeAllButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        Task task = tasksPanel.getSelectedTask();
        task.setDownloadListener(new DownloadListener() {
            String fmt = "<html><h1>%s</h1>\n\n%s";

            @Override
            public void onDownloadFailed(Task task, IOException ex) {
                String message = String.format(fmt, task.getName(), ex.getLocalizedMessage());
                Message.error(Dict.DOWNLOAD_FAILED.getString(), message);
                restoreListener();
            }

            @Override
            public void onDownloadFinished(Task task, File destFile) {
                String message = String.format(fmt, task.getName(), destFile.getAbsolutePath());
                initImageViewer();
                Message.information(Dict.DOWNLOAD_COMPLETED.getString(), message);
                restoreListener();
            }

            @Override
            public void onDownloadStarted(Task task) {
            }

            private void restoreListener() {
                task.setDownloadListener(Task.DEFAULT_DOWNLOAD_LISTENER);
            }
        });

        task.run();
    }//GEN-LAST:event_downloadButtonActionPerformed

    private void openDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDirectoryButtonActionPerformed
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            File dest = new File(tasksPanel.getSelectedTask().getDestination()).getParentFile();

            desktop.open(dest);
        } catch (Exception ex) {
            Message.error(Dict.IO_ERROR_TITLE.getString(), Dict.ERROR_CANT_OPEN_DIRECTORY.getString());
        }
    }//GEN-LAST:event_openDirectoryButtonActionPerformed

    private void cronToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cronToggleButtonActionPerformed
        if (cronToggleButton.isSelected()) {
            mTaskManager.start();
        } else {
            mTaskManager.stop();
        }
    }//GEN-LAST:event_cronToggleButtonActionPerformed

    private void cloneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cloneButtonActionPerformed
        try {
            Task t = tasksPanel.getSelectedTask().clone();
            mTaskManager.getModel().addElement(t);
            mTaskManager.sortModel();
            mTaskManager.save();
        } catch (CloneNotSupportedException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_cloneButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        displayHelp(mHelpId);
    }//GEN-LAST:event_helpButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cloneButton;
    private javax.swing.JToggleButton cronToggleButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton helpButton;
    private se.trixon.almond.imageviewer.ImageViewPanel imageViewPanel;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JButton openDirectoryButton;
    private javax.swing.JButton removeAllButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JSplitPane splitPanel;
    private se.trixon.toolbox.idiot.TasksPanel tasksPanel;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
