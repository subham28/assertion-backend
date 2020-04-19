package com.assertion.assignment.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class PasswordGenerationUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordGenerationUtil.class);
    public String generateRandomString(String concatenatedString)
    {

        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        char[] concatCharArr = concatenatedString.toCharArray();
        for(int i = concatCharArr.length-1;i>0;i--){
            int j = random.nextInt(i+1);
            char temp = concatenatedString.charAt(i);
            concatCharArr[i] = concatCharArr[j];
            concatCharArr[j] = temp;
        }
        return randomString.append(concatCharArr).toString();
    }

    public int getRandomInteger(int max){
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(max);
    }

    /*public void ecryptPassWord(String passString) {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(ApplicationConstants.ALGO_NAME);

            *//*generate random salt*//*
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);

            // passig salt to algorithm for computation
            messageDigest.update(salt);

            //generate the salted hash
            byte[] encryptedPass = messageDigest.digest(passString.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte pass : encryptedPass) {
                builder.append(String.format("%02x", pass));
            }

            LOGGER.info("Encrypted bytes : {}",encryptedPass);
            LOGGER.info("Encrypted builder : {}",builder);
        }catch (Exception ex){
            LOGGER.info("Error encrypting password : {}",ex.getMessage());
        }
    }*/

    /*public void decryptPassword(String hash,byte[] salt)
    {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(ApplicationConstants.ALGO_NAME);
            messageDigest.update(salt);
            byte[] decryptedPass = messageDigest.d()
        }catch (Exception ex){
            LOGGER.info("Error decrypting password : {}",ex.getMessage());
        }
    }*/
}
