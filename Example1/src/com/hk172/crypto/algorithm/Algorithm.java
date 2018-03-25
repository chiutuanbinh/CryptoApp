/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk172.crypto.algorithm;

/**
 *
 * @author binhchiu
 */
public interface Algorithm {
    /**
     * Just call this method to execute the chosen, additional information must be provided to the implement
     * @return  The byte array result of the Algorithm
     * @throws java.lang.Throwable in case the algorithm fail
     */
    public byte[] run() throws Throwable;
}
