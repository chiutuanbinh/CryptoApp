/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author ASUS
 */
public class open_file {
    JFileChooser file_chooser = new JFileChooser();
    StringBuilder sb1 = new StringBuilder();
    String sb2;
    
    public File pick_me() throws FileNotFoundException{
        File returnFile = null;
        if (file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            returnFile = file_chooser.getSelectedFile();
            sb2 = returnFile.getPath();
            try (Scanner input = new Scanner(returnFile)) {
                while (input.hasNext()){
                    sb1.append(input.nextLine());
                    sb1.append("\n");
                }
            }
        }
        else {
            sb1.append("No file was selected");
        }
        return returnFile;
    }
}
