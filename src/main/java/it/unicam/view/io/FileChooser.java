package it.unicam.view.io;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser {

    private JFrame frame;
    private FilteredFileChooser fileChooser = new FilteredFileChooser();

    public FileChooser(){


        frame = new JFrame("File Chooser Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton chooseFileButton = new JButton("Choose File");
       chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { showFileChooser(); }
        });

        frame.getContentPane().add(chooseFileButton);

        frame.setSize(300, 200);
        frame.setVisible(false);
    }

    public File showFileChooser() {
        File selectedFile = null;

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected");
        }
        return selectedFile;
    }

    private class FilteredFileChooser extends JFileChooser {
        public FilteredFileChooser() {
            super();
            setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return true;
                    }

                    String extension = getExtension(file);
                    return extension != null && (extension.equals("txt") || extension.equals("png") || extension.equals("jpg") || extension.equals("pdf"));
                }

                @Override
                public String getDescription() {
                    return "Text, PNG, JPG, and PDF files";
                }

                private String getExtension(File file) {
                    String name = file.getName();
                    int lastIndexOfDot = name.lastIndexOf('.');
                    if (lastIndexOfDot > 0 && lastIndexOfDot < name.length() - 1) {
                        return name.substring(lastIndexOfDot + 1).toLowerCase();
                    }
                    return null;
                }
            });
        }
    }
}
