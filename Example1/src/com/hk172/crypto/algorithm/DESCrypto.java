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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JProgressBar;

/**
 *
 * @author binhchiu
 */
public class DESCrypto extends CryptoAlgorithm{
    
    private SecretKeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    private final int BUFF_SIZE = 8192;
    
    public DESCrypto(){
        super();
        try {
            factory = SecretKeyFactory.getInstance("DES");
            encryptCipher = Cipher.getInstance("DES");
            decryptCipher = Cipher.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public byte[] encrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        
        SecretKey skey = factory.generateSecret(new DESKeySpec(key));
        encryptCipher.init(Cipher.ENCRYPT_MODE, skey);
        //System.out.println(encryptCipher.getBlockSize());
        
        return encryptCipher.doFinal(input);
        
    }

    @Override
    public void encrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        //get the key
        byte[] key = readKey(inputKey);
        //prepare the file
        FileInputStream fs = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        //parse the key, init the cipher
        SecretKey skey = factory.generateSecret(new DESKeySpec(key));
        encryptCipher.init(Cipher.ENCRYPT_MODE, skey);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        //wrap the fos stream
        CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);
        //var for encrypt
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
//            System.out.println(unitsize);
            fs.read(inputBuffer, 0, unitsize);
            hashFunc.append(inputBuffer);
            cos.write(inputBuffer,0, unitsize);
            
            read += unitsize;
            progressBar.setValue((int) (read*100/offset));
        }
//        System.out.println((int)read/offset);
//        System.out.println(offset);
        cos.close();
    }
    
    
    
    @Override
    public byte[] decrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        SecretKey sKey = factory.generateSecret(new DESKeySpec(key));
        decryptCipher.init(Cipher.DECRYPT_MODE, sKey);
        
        return decryptCipher.doFinal(input);
    }

    @Override
    public void decrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        //get the key
        byte[] key = readKey(inputKey);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        //prepare the file 
        FileInputStream fs = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        //prepare the key
        SecretKey skey = factory.generateSecret(new DESKeySpec(key));
        decryptCipher.init(Cipher.DECRYPT_MODE, skey);
        
        //wrap the fos into a cipherstream
        CipherInputStream cis = new CipherInputStream(fs, decryptCipher);
        
        //init value for cipher
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        //read and print the value by blocks
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
            cis.read(inputBuffer,0,unitsize);
            hashFunc.append(inputBuffer);
            
            fos.write(inputBuffer, 0, unitsize);
            
            read += unitsize;
            progressBar.setValue((int) (read*100/offset));
        }
        cis.close();
    }
    
}
