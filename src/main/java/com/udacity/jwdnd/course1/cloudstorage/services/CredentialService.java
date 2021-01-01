package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public Credential getCredential(int credentialId){
        return credentialMapper.getCredential(credentialId);
    }

    public int addCredential(Credential credential){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        int insert = credentialMapper.insert(credential);
        return credential.getCredentialid();
    }

    public String decryptPassword(String encryptedPassword, String key){
        return encryptionService.decryptValue(encryptedPassword, key);
    }

    public List<Credential> getCredentials(int userId){
        return credentialMapper.getAllCredentialsByUserId(userId);
    }

    public int delete(int credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public int update(Credential credential){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credentialMapper.updateCredential(credential);
    }
}
