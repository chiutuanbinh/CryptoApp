/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class HashAlgorithm {
    protected File input;
    protected String hashType;
    MessageDigest md = null;
    public void setInput(File input){
        this.input = input ;
        
    }
    private byte[] readFile() throws Exception{
        return Files.readAllBytes(input.toPath());
    }

    public void setHashType(String hashType){
        this.hashType = hashType;
        try {
            md = MessageDigest.getInstance(hashType);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] run() throws Exception {
        byte[] input = readFile();
        return md.digest(input);
    }
    
    public byte[] run(byte[] input) throws Exception{
        
        return md.digest(input);
    }
    
    public static String hashFile(File inputFile, String hashType) throws Exception{
        MessageDigest md = MessageDigest.getInstance(hashType);
        FileInputStream fs = new FileInputStream(inputFile);
        int bufferSize = 16384;
        byte[] buffer = new byte[bufferSize];
        int read = 0;
        while((read = fs.read(buffer)) > 0){
            md.update(buffer,0,read);
        }
        return DatatypeConverter.printHexBinary(md.digest());
    }
    
    public void append(byte[] nextblock) {
        md.update(nextblock);
    }
    public byte[] executeHash(){
        return md.digest();
    }
    
}
