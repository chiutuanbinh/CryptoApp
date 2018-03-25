/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public abstract class CryptoAlgorithm {
    protected File inputFile;
    protected File inputKey;
    
    protected byte[] readFile(File file) throws IOException{
        return Files.readAllBytes(file.toPath());
    }
    
    protected byte[] readKey(File file) throws Exception{
        FileInputStream fs = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        
        String key = br.readLine();
        return DatatypeConverter.parseHexBinary(key);
    }
    
    public void setInputFile(File input){
        this.inputFile = input;
    }
    
    public void setInputKey(File key){
        this.inputKey = key;
    }
    
    public abstract byte[] encrypt() throws Exception;
    public abstract byte[] decrypt() throws Exception;
}
