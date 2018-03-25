/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
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

    
    protected RSAPublicKeySpec getPublicKeySpec(File file) throws Exception {
        FileInputStream fs = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        
        String modulusString = br.readLine();
        String pubExpString = br.readLine();
        BigInteger modulus = new BigInteger(1, DatatypeConverter.parseBase64Binary(modulusString));
        BigInteger pubExp= new BigInteger(1, DatatypeConverter.parseBase64Binary(pubExpString));
        RSAPublicKeySpec RKS = new RSAPublicKeySpec(modulus, pubExp);
        
        return RKS;
    }

    protected RSAPrivateKeySpec getPrivateKeySpec(File file) throws Exception {
        FileInputStream fs = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        
        String modulusString = br.readLine();
        String prvExpString = br.readLine();
        
        BigInteger modulus = new BigInteger(1, DatatypeConverter.parseBase64Binary(modulusString));
        BigInteger prvExp= new BigInteger(1, DatatypeConverter.parseBase64Binary(prvExpString));
        
        RSAPrivateKeySpec RKS = new RSAPrivateKeySpec(modulus, prvExp);
        
        return RKS;
    }
    
    
    
    @Override
    public byte[] encrypt() throws Exception {
        byte[] input = readFile(inputFile);
        RSAPublicKeySpec keySpec = getPublicKeySpec(inputKey);
        RSAPublicKey publickey = (RSAPublicKey) factory.generatePublic(keySpec);
        
        encryptCipher.init(Cipher.ENCRYPT_MODE, publickey);
        return encryptCipher.doFinal(input);
    }

    @Override
    public byte[] decrypt() throws Exception {
        byte[] input = readFile(inputFile);
        RSAPrivateKeySpec keySpec = getPrivateKeySpec(inputKey);
        RSAPrivateKey privatekey = (RSAPrivateKey) factory.generatePrivate(keySpec);
        
        decryptCipher.init(Cipher.DECRYPT_MODE, privatekey);
        return decryptCipher.doFinal(input);
        
    }
    
    
}
