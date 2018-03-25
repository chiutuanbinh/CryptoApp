/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;

/**
 *
 * @author binhchiu
 */
public class RSACrypto extends CryptoAlgorithm{

    private SecretKeyFactory factory ;
    private Cipher encryptCipher ;
    private Cipher decryptCipher ;
    public RSACrypto(){
        super();
        try {
            factory = SecretKeyFactory.getInstance("RSA");
            encryptCipher = Cipher.getInstance("RSA/ECB/PKCS5Padding");
            decryptCipher = Cipher.getInstance("RSA/ECB/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public byte[] encrypt() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] decrypt() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
