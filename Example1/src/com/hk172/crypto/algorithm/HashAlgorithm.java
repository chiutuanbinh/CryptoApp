/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;

/**
 *
 * @author binhchiu
 */
public class HashAlgorithm {
    protected File input;
    protected String hashType;
    protected final int BUFF_SIZE = 1048576;
    public void setInput(File input){
        this.input = input ;
    }
    private byte[] readFile() throws Exception{
        return Files.readAllBytes(input.toPath());
    }

    public void setHashType(String hashType){
        this.hashType = hashType;
    }
    
    public byte[] run() throws Exception {
        byte[] input = readFile();
        MessageDigest md = MessageDigest.getInstance(hashType);
        return md.digest(input);
    }
    
    public byte[] run(byte[] input) throws Exception{
        MessageDigest md = MessageDigest.getInstance(hashType);
        return md.digest(input);
    }
    
}
