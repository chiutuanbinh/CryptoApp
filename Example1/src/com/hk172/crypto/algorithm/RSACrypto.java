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
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class RSACrypto extends CryptoAlgorithm{

    private KeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    
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
        FileInputStream fs = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fs);
        byte[] key = new byte[(int) file.length()];
        dis.readFully(key);
        dis.close();
        
        return new X509EncodedKeySpec(key);
    }

    protected X509EncodedKeySpec getPrivateKeySpec(File file) throws Exception {
        FileInputStream fs = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fs);
        byte[] key = new byte[(int) file.length()];
        dis.readFully(key);
        dis.close();
        
        return new X509EncodedKeySpec(key);
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
        X509EncodedKeySpec keySpec = getPrivateKeySpec(inputKey);
        RSAPrivateKey privatekey = (RSAPrivateKey) factory.generatePrivate(keySpec);
        
        decryptCipher.init(Cipher.DECRYPT_MODE, privatekey);
        return decryptCipher.doFinal(input);
        
    }
    
    
}
