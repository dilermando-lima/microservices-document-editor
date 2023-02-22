package documentpublish.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.documentpublish.PublishDocumentContract;

@RestController
public class PublishDocumentService implements PublishDocumentContract {

    @Override
    public void publish(String idDocument, String idVersion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'publish'");
    }
    
}
