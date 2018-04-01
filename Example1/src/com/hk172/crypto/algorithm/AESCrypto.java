/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JProgressBar;

/**
 *
 * @author binhchiu
 */
public class AESCrypto extends CryptoAlgorithm{

    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    private final int BUFF_SIZE = 16384;
    public AESCrypto(){
        super();
        try {
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public byte[] encrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        
        SecretKey sKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(sKey.getEncoded());
        encryptCipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
        
        return encryptCipher.doFinal(input);
    }

    
    @Override
    public byte[] decrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        SecretKey sKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(sKey.getEncoded());
        decryptCipher.init(Cipher.DECRYPT_MODE, sKey, iv);
        
        return decryptCipher.doFinal(input);
    }

    @Override
    public void encrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        byte[] key = readKey(inputKey);
        
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        SecretKey sKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(sKey.getEncoded());
        encryptCipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
        
        
        CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
            fis.read(inputBuffer, 0, unitsize);
            hashFunc.append(Arrays.copyOfRange(inputBuffer, 0, unitsize-1));
            cos.write(inputBuffer,0, unitsize);
            
            read += unitsize;
            progressBar.setValue((int) (read*100/offset));
        }
        cos.close();
        
    }

    @Override
    public void decrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        byte[] key = readKey(inputKey);
        
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        SecretKey skey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(skey.getEncoded());
        decryptCipher.init(Cipher.DECRYPT_MODE, skey, iv);
        
        CipherInputStream cis = new CipherInputStream(fis, decryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
            cis.read(inputBuffer,0,unitsize);
            hashFunc.append(Arrays.copyOfRange(inputBuffer, 0, unitsize-1));
            
            fos.write(inputBuffer, 0, unitsize);
            
            read += unitsize;
            progressBar.setValue((int) (read*100/offset));
        }
        cis.close();
    }
    
}
