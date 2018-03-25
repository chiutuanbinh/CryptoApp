/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class DESCrypto extends CryptoAlgorithm{
    
    private SecretKeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    public DESCrypto(){
        super();
        try {
            factory = SecretKeyFactory.getInstance("DES");
            encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public byte[] encrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        System.out.println(DatatypeConverter.printHexBinary(key));
        SecretKey skey = factory.generateSecret(new DESKeySpec(key));
        encryptCipher.init(Cipher.ENCRYPT_MODE, skey);
        
        return encryptCipher.doFinal(input);
        
    }
    @Override
    public byte[] decrypt() throws Exception {
        byte[] input = readFile(inputFile);
        byte[] key = readKey(inputKey);
        SecretKey sKey = factory.generateSecret(new DESKeySpec(key));
        decryptCipher.init(Cipher.DECRYPT_MODE, sKey);
        
        return decryptCipher.doFinal(input);
    }
    
}
