/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author binhchiu
 */
public class AESCrypto extends CryptoAlgorithm{

    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    
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
    
}
