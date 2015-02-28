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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import se.trixon.almond.dictionary.Dict;
import se.trixon.toolbox.asiot.task.Task;
import se.trixon.toolbox.asiot.task.TaskPanel;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class TasksPanel extends javax.swing.JPanel {

    private final TaskPanel mTaskPanel = new TaskPanel();
    private final DefaultListModel mModel = new DefaultListModel();

    /**
     * Creates new form TasksPanel
     */
    public TasksPanel() {
        initComponents();
        init();
    }

    public void addTask() {
        mTaskPanel.setTask(new Task());
        DialogDescriptor d = new DialogDescriptor(mTaskPanel, Dict.ADD.getString(), true, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        d.setAdditionalOptions(new JButton[]{new JButton(Dict.VERIFY.getString())});
        mTaskPanel.setDialogDescriptor(d);

        Object retval = DialogDisplayer.getDefault().notify(d);

        if (retval == NotifyDescriptor.OK_OPTION) {
            if (mTaskPanel.getTask().isValid()) {
                Task task = mTaskPanel.getTask();
                mModel.addElement(task);
                sortModel();
                list.setSelectedValue(task, true);
            } else {
                showInvalidTaskDialog();
            }
        }
    }

    public void editTask() {
        if (getSelectedTask() != null) {
            mTaskPanel.setTask(getSelectedTask());
            DialogDescriptor d = new DialogDescriptor(mTaskPanel, Dict.EDIT.getString(), true, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            d.setAdditionalOptions(new JButton[]{new JButton(Dict.VERIFY.getString())});
            mTaskPanel.setDialogDescriptor(d);
            Object retval = DialogDisplayer.getDefault().notify(d);

            if (retval == NotifyDescriptor.OK_OPTION) {
                Task task = mTaskPanel.getTask();
                if (task.isValid()) {
                    mModel.set(mModel.indexOf(getSelectedTask()), task);
                    sortModel();
                    list.setSelectedValue(task, true);
                } else {
                    showInvalidTaskDialog();
                }
            }
        }
    }

    public void removeTask() {
        if (getSelectedTask() != null) {
            NotifyDescriptor d = new NotifyDescriptor(
                    NbBundle.getMessage(this.getClass(), "TasksPanel.message.remove", getSelectedTask().getName()),
                    NbBundle.getMessage(this.getClass(), "TasksPanel.title.remove"),
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.WARNING_MESSAGE,
                    null,
                    null);
            Object retval = DialogDisplayer.getDefault().notify(d);

            if (retval == NotifyDescriptor.OK_OPTION) {
                mModel.removeElement(getSelectedTask());
                sortModel();
            }
        }
    }

    public void removeAllTasks() {
        if (!mModel.isEmpty()) {
            NotifyDescriptor d = new NotifyDescriptor(
                    NbBundle.getMessage(this.getClass(), "TasksPanel.message.removeAll"),
                    NbBundle.getMessage(this.getClass(), "TasksPanel.title.removeAll"),
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.WARNING_MESSAGE,
                    null,
                    null);
            Object retval = DialogDisplayer.getDefault().notify(d);

            if (retval == NotifyDescriptor.OK_OPTION) {
                mModel.removeAllElements();
            }
        }
    }

    private void init() {
        list.setModel(mModel);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
                    editTask();
                }
            }
        });
    }

    private Task getSelectedTask() {
        return (Task) list.getSelectedValue();
    }

    public void sortModel() {
        Object[] objects = mModel.toArray();
        Arrays.sort(objects);
        mModel.clear();

        for (Object object : objects) {
            mModel.addElement(object);
        }
    }

    private void showInvalidTaskDialog() {
        NotifyDescriptor d = new NotifyDescriptor(
                NbBundle.getMessage(this.getClass(), "TasksPanel.invalid"),
                Dict.INVALID_INPUT.getString(),
                NotifyDescriptor.ERROR_MESSAGE,
                NotifyDescriptor.ERROR_MESSAGE,
                new JButton[]{
                    new JButton(Dict.OK.getString())
                },
                null);

        DialogDisplayer.getDefault().notify(d);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        list = new javax.swing.JList();

        scrollPane.setPreferredSize(new java.awt.Dimension(200, 158));
        scrollPane.setViewportView(list);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList list;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
