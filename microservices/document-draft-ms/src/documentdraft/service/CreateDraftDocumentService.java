package documentdraft.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.documentdraft.CreateDraftDocumentContract;

@RestController
public class CreateDraftDocumentService implements CreateDraftDocumentContract{

    @Override
    public ResponseCreateDraftDocument createDraft(String idDocument) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDraft'");
    }
    
}
