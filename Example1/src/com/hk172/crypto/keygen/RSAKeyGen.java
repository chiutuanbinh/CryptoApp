/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.keygen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author binhchiu
 */
public class RSAKeyGen implements KeyGen{
    private KeyPairGenerator kpg;
    private int keyLength = 512;

    public RSAKeyGen() {
        try {
            KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    @Override
    public void writeToFile(String path) {
        try {
            KeyPair kp = genKeyPair();
            String publicKey = DatatypeConverter.printBase64Binary(kp.getPublic().getEncoded());
            String privateKey = DatatypeConverter.printBase64Binary(kp.getPrivate().getEncoded());
            
            File publicKeyFile = new File( path + "\\publicKey.txt");
            File privateKeyFile = new File(path + "\\privateKey.txt");
            
            FileWriter fw = new FileWriter(publicKeyFile);
            fw.write(publicKey);
            fw.close();
            
            FileWriter fw1 = new FileWriter(privateKeyFile);
            fw1.write(privateKey);
            fw1.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private KeyPair genKeyPair(){
        kpg.initialize(keyLength);
        return kpg.genKeyPair();
    } 
    
    private String parseToString(byte[] input){
        return DatatypeConverter.printBase64Binary(input);
    }
    
}
