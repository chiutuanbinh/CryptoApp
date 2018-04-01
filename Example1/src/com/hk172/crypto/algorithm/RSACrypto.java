/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.swing.JProgressBar;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class RSACrypto extends CryptoAlgorithm{

    private KeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    private final int BUFF_SIZE = 1024;
    
    public RSACrypto(){
        super();
        try {
            factory = KeyFactory.getInstance("RSA");
            encryptCipher = Cipher.getInstance("RSA");
            decryptCipher = Cipher.getInstance("RSA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    
    protected X509EncodedKeySpec getPublicKeySpec(File file) throws Exception {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String keyString = bf.readLine();
        byte[] key = DatatypeConverter.parseBase64Binary(keyString);

        return new X509EncodedKeySpec(key);
    }

    protected PKCS8EncodedKeySpec getPrivateKeySpec(File file) throws Exception {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String keyString = bf.readLine();
        byte[] key = DatatypeConverter.parseBase64Binary(keyString);
         
        return new PKCS8EncodedKeySpec(key);
    }
    
    
    
    @Override
    public byte[] encrypt() throws Exception {
        byte[] input = readFile(inputFile);
        X509EncodedKeySpec keySpec = getPublicKeySpec(inputKey);
        RSAPublicKey publickey = (RSAPublicKey) factory.generatePublic(keySpec);
        
        encryptCipher.init(Cipher.ENCRYPT_MODE, publickey);
        return encryptCipher.doFinal(input);
    }

    @Override
    public byte[] decrypt() throws Exception {
        byte[] input = readFile(inputFile);
        PKCS8EncodedKeySpec keySpec = getPrivateKeySpec(inputKey);
        RSAPrivateKey privatekey = (RSAPrivateKey) factory.generatePrivate(keySpec);
        
        decryptCipher.init(Cipher.DECRYPT_MODE, privatekey);
        return decryptCipher.doFinal(input);
        
    }


    @Override
    public void encrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        byte[] input = readFile(inputFile);
        X509EncodedKeySpec keySpec = getPublicKeySpec(inputKey);
        RSAPublicKey publickey = (RSAPublicKey) factory.generatePublic(keySpec);
        
        encryptCipher.init(Cipher.ENCRYPT_MODE, publickey);
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        CipherOutputStream cos = new CipherOutputStream(fos, encryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
        while (read < offset) {
            unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
            
//            System.out.println(unitsize);
            fis.read(inputBuffer, 0, unitsize);
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
    public void decrypt(String path, JProgressBar progressBar, HashAlgorithm hashFunc) throws Exception {
        byte[] input = readFile(inputFile);
        PKCS8EncodedKeySpec keySpec = getPrivateKeySpec(inputKey);
        RSAPrivateKey privatekey = (RSAPrivateKey) factory.generatePrivate(keySpec);
        
        decryptCipher.init(Cipher.DECRYPT_MODE, privatekey);
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(path);
        
        CipherInputStream cis = new CipherInputStream(fis, encryptCipher);
        byte[] inputBuffer = new byte[BUFF_SIZE];
        long read = 0;
        long offset = inputFile.length();
        int unitsize;
        
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
