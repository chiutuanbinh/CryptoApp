/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

import java.io.File;

/**
 *
 * @author binhchiu
 */
public abstract class CryptoAlgorithm implements Algorithm{
    protected File inputFile;
    protected File inputKey;
    
    public abstract byte[] readFile();
    
    public void setInputFile(File input){
        this.inputFile = input;
    }
    
    public void setInputKey(File key){
        this.inputKey = key;
    }
}
