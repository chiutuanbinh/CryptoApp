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
public abstract class HashAlgorithm implements Algorithm{
    protected File input;
    
    public void setInput(File input){
        this.input = input ;
    }
    public abstract byte[] readFile() throws Throwable;
}
