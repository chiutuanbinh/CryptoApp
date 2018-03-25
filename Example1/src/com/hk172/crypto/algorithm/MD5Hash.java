/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
/**
 *
 * @author binhchiu
 */
public class MD5Hash extends HashAlgorithm{

    @Override
    public byte[] readFile() throws IOException {
        return Files.readAllBytes(input.toPath());
    }

    @Override
    public byte[] run() throws Exception{
        byte[] input = readFile();
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(input);
    }
    
    
    
}
