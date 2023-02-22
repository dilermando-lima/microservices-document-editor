package storage.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.storage.SaveContentIntoStorageContract;

@RestController
public class SaveContentIntoStorageService implements SaveContentIntoStorageContract {

    @Override
    public void getContent(String idPrefix, String idContent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getContent'");
    }
    
}
