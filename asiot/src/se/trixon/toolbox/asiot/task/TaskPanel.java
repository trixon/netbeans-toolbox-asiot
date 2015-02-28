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
package se.trixon.toolbox.asiot.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.DialogDescriptor;
import se.trixon.almond.dialogs.FileChooserPanel;
import se.trixon.almond.dictionary.Dict;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class TaskPanel extends javax.swing.JPanel implements FileChooserPanel.FileChooserButtonListener {

    private DialogDescriptor mDialogDescriptor;
    private Task mTask;

    /**
     * Creates new form TaskEditorPanel
     */
    public TaskPanel() {
        initComponents();
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
    }

    public void setDialogDescriptor(DialogDescriptor dialogDescriptor) {
        mDialogDescriptor = dialogDescriptor;
        mDialogDescriptor.setButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] addditionalOptions = mDialogDescriptor.getAdditionalOptions();

                if (e.getSource() == addditionalOptions[0]) {
//                    mTaskVerifier = new TaskVerifier();
                    saveTask();
//                    mTaskVerifier.verify(mTask);
                }
            }

        });
    }

    public void setTask(Task task) {
        mTask = task;
        loadTask();
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
        destinationPanel = new se.trixon.almond.dialogs.FileChooserPanel();
        panel = new javax.swing.JPanel();
        basenamePanel = new javax.swing.JPanel();
        basenameLabel = new javax.swing.JLabel();
        basenameTextField = new javax.swing.JTextField();
        datePatternPanel = new javax.swing.JPanel();
        datePatternLabel = new javax.swing.JLabel();
        datePatternTextField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(activeCheckBox, Dict.ACTIVE.getString());

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, Dict.NAME.getString());

        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, Dict.DESCRIPTION.getString());

        org.openide.awt.Mnemonics.setLocalizedText(urlLabel, "URL"); // NOI18N

        urlTextField.setText(org.openide.util.NbBundle.getMessage(TaskPanel.class, "TaskPanel.urlTextField.text")); // NOI18N

        destinationPanel.setHeader(Dict.DESTINATION.getString());
        destinationPanel.setMode(JFileChooser
            .DIRECTORIES_ONLY);

        panel.setLayout(new java.awt.GridLayout(1, 0));

        basenamePanel.setLayout(new javax.swing.BoxLayout(basenamePanel, javax.swing.BoxLayout.PAGE_AXIS));

        basenameLabel.setLabelFor(basenameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(basenameLabel, Dict.BASENAME.getString());
        basenamePanel.add(basenameLabel);
        basenamePanel.add(basenameTextField);

        panel.add(basenamePanel);

        datePatternPanel.setLayout(new javax.swing.BoxLayout(datePatternPanel, javax.swing.BoxLayout.PAGE_AXIS));

        datePatternLabel.setLabelFor(datePatternTextField);
        org.openide.awt.Mnemonics.setLocalizedText(datePatternLabel, Dict.DATE_PATTERN.getString());
        datePatternPanel.add(datePatternLabel);
        datePatternPanel.add(datePatternTextField);

        panel.add(datePatternPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField)
                    .addComponent(descriptionTextField)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JLabel basenameLabel;
    private javax.swing.JPanel basenamePanel;
    private javax.swing.JTextField basenameTextField;
    private javax.swing.JLabel datePatternLabel;
    private javax.swing.JPanel datePatternPanel;
    private javax.swing.JTextField datePatternTextField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private se.trixon.almond.dialogs.FileChooserPanel destinationPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel urlLabel;
    private javax.swing.JTextField urlTextField;
    // End of variables declaration//GEN-END:variables

    private void loadTask() {
        activeCheckBox.setSelected(mTask.isActive());
        nameTextField.setText(mTask.getName());
        descriptionTextField.setText(mTask.getDescription());
        urlTextField.setText(mTask.getUrl());
        destinationPanel.setPath(mTask.getDestination());
        basenameTextField.setText(mTask.getBasename());
        datePatternTextField.setText(mTask.getDatePattern());
    }

    private void saveTask() {
        mTask.setActive(activeCheckBox.isSelected());
        mTask.setName(nameTextField.getText());
        mTask.setDescription(descriptionTextField.getText());
        mTask.setUrl(urlTextField.getText());
        mTask.setDestination(destinationPanel.getPath());
        mTask.setBasename(basenameTextField.getText());
        mTask.setDatePattern(datePatternTextField.getText());
    }
}
