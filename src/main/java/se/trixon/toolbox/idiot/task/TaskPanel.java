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
package se.trixon.toolbox.idiot.task;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.commons.io.FileUtils;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.NotifyDescriptor;
import se.trixon.almond.nbp.dialogs.FileChooserPanel;
import se.trixon.almond.nbp.dialogs.cron.CronPanel;
import se.trixon.almond.util.Dict;

/**
 *
 * @author Patrik Karlsson
 */
public class TaskPanel extends javax.swing.JPanel implements FileChooserPanel.FileChooserButtonListener {

    private DialogDescriptor mDialogDescriptor;
    private NotificationLineSupport mNotificationLineSupport;
    private Task mTask;
    private DocumentListener mDocumentListener;

    /**
     * Creates new form TaskEditorPanel
     */
    public TaskPanel() {
        initComponents();
        init();
    }

    public Task getTask() {
        saveTask();
        return mTask;
    }

    @Override
    public void onFileChooserCancel(FileChooserPanel fileChooserPanel) {
    }

    @Override
    public void onFileChooserCheckBoxChange(FileChooserPanel fileChooserPanel, boolean isSelected) {
    }

    @Override
    public void onFileChooserDrop(FileChooserPanel fileChooserPanel) {
    }

    @Override
    public void onFileChooserOk(FileChooserPanel fileChooserPanel, File file) {
    }

    @Override
    public void onFileChooserPreSelect(FileChooserPanel fileChooserPanel) {
        File file;
        if (mTask.getDestination() == null) {
            file = new File(FileUtils.getUserDirectory(), "example.jpg");
        } else {
            file = new File(mTask.getDestination());
        }
        fileChooserPanel.getFileChooser().setSelectedFile(file);
    }

    public void setDialogDescriptor(DialogDescriptor dialogDescriptor) {
        mNotificationLineSupport = dialogDescriptor.createNotificationLineSupport();
        mDialogDescriptor = dialogDescriptor;
        mDialogDescriptor.setButtonListener((ActionEvent e) -> {
            Object[] addditionalOptions = mDialogDescriptor.getAdditionalOptions();

            if (e.getSource() == addditionalOptions[0]) {
                CronPanel cronPanel = new CronPanel();
                DialogDescriptor d = new DialogDescriptor(cronPanel, Dict.SCHEDULE.getString());
                cronPanel.setDialogDescriptor(d);
                cronPanel.setCronString(mTask.getCron());
                Object retval = DialogDisplayer.getDefault().notify(d);

                if (retval == NotifyDescriptor.OK_OPTION) {
                    mTask.setCron(cronPanel.getCronString());
                }
            }
        });
    }

    public void setTask(Task task) {
        mTask = task;
        loadTask();
        validateInput();
    }

    private void init() {
        mDocumentListener = new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }
        };

        nameTextField.getDocument().addDocumentListener(mDocumentListener);
        descriptionTextField.getDocument().addDocumentListener(mDocumentListener);
        destinationPanel.getTextField().getDocument().addDocumentListener(mDocumentListener);
        urlTextField.getDocument().addDocumentListener(mDocumentListener);

        destinationPanel.setButtonListener(this);
    }

    private void validateInput() {
        boolean invalid = nameTextField.getText().isEmpty()
                || descriptionTextField.getText().isEmpty()
                || destinationPanel.getTextField().getText().isEmpty()
                || urlTextField.getText().isEmpty();

        mDialogDescriptor.setValid(!invalid);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        activeCheckBox = new javax.swing.JCheckBox();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        urlLabel = new javax.swing.JLabel();
        urlTextField = new javax.swing.JTextField();
        destinationPanel = new se.trixon.almond.nbp.dialogs.FileChooserPanel();

        org.openide.awt.Mnemonics.setLocalizedText(activeCheckBox, Dict.ACTIVE.getString());

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, Dict.NAME.getString());

        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, Dict.DESCRIPTION.getString());

        org.openide.awt.Mnemonics.setLocalizedText(urlLabel, "URL"); // NOI18N

        urlTextField.setText(org.openide.util.NbBundle.getMessage(TaskPanel.class, "TaskPanel.urlTextField.text")); // NOI18N

        destinationPanel.setHeader(Dict.DESTINATION.getString());
        destinationPanel.setMode(JFileChooser
            .FILES_ONLY);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField)
                    .addComponent(descriptionTextField)
                    .addComponent(destinationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(urlTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(descriptionLabel)
                            .addComponent(urlLabel)
                            .addComponent(activeCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(destinationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private se.trixon.almond.nbp.dialogs.FileChooserPanel destinationPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel urlLabel;
    private javax.swing.JTextField urlTextField;
    // End of variables declaration//GEN-END:variables

    private void loadTask() {
        activeCheckBox.setSelected(mTask.isActive());
        nameTextField.setText(mTask.getName());
        descriptionTextField.setText(mTask.getDescription());
        urlTextField.setText(mTask.getUrl());
        destinationPanel.setPath(mTask.getDestination());
    }

    private void saveTask() {
        mTask.setActive(activeCheckBox.isSelected());
        mTask.setName(nameTextField.getText());
        mTask.setDescription(descriptionTextField.getText());
        mTask.setUrl(urlTextField.getText());
        mTask.setDestination(destinationPanel.getPath());
    }
}
