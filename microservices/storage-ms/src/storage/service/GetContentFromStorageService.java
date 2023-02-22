package storage.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.storage.GetContentFromStorageContract;

@RestController
public class GetContentFromStorageService implements GetContentFromStorageContract {

    @Override
    public ResponseGetContentFromStorage getContent(String idPrefix, String idContent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getContent'");
    }
    
}
