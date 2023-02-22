package document.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.document.ListDocumentContract;

@RestController
public class ListDocumentService implements ListDocumentContract {

    @Override
    public ResponseListDocument list(RequestListDocument request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }
    
}
