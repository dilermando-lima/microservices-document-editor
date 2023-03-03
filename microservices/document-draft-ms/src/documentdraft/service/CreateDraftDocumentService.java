package documentdraft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.documentdraft.CreateDraftDocumentContract;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import documentdraft.model.Document;
import documentdraft.model.VersionDocument;

@RestController
public class CreateDraftDocumentService implements CreateDraftDocumentContract{

    private static Logger logger = LoggerFactory.getLogger(CreateDraftDocumentService.class);

    @Autowired private MongoRepository repository;

    @Override
    public ResponseCreateDraftDocument createDraft(String idDocument) {
        logger.info("createDraft() : idDocument = {}", idDocument);

        validateRequest(idDocument);
        validateIdDocumentBeforeSave(idDocument);

        var newVersion = saveVersionIntoDatabase(buildVersionToBeSaved(idDocument));
    
        pushVersionIntoDocument(newVersion, idDocument);

        var response = convertEntityToResponse(newVersion);

        logger.debug("createDraft() : response = {}", response);

        return response;
    }

    private ResponseCreateDraftDocument convertEntityToResponse(VersionDocument versionDocument) {
        logger.debug("convertEntityToResponse() : versionDocument = {}", versionDocument);
        return new ResponseCreateDraftDocument(versionDocument.getId());
    }

    private void pushVersionIntoDocument(VersionDocument newVersion, String idDocument) {
        repository.pushBDrefObjectById(Document.class, idDocument, "versionList", newVersion);
    }

    private VersionDocument saveVersionIntoDatabase(VersionDocument version) {
        logger.debug("saveVersionIntoDatabase() : version = {}", version);
        return repository.insert(version);
    }

    private VersionDocument buildVersionToBeSaved(String idDocument){
        logger.debug("createVersionToBeSaved() : idDocument = {}", idDocument);
        return new VersionDocument(idDocument,"any");
    }

    private void validateIdDocumentBeforeSave(String idDocument) {
        logger.debug("validateIdDocumentBeforeSave() : idDocument = {}", idDocument);
        Throw.notFound(
            logger, 
            "document with id '%s' has not been found".formatted(idDocument), 
            repository.existsByAttrEqual("name", idDocument, Document.class)
        );
    }

    private void validateRequest(String idDocument) {
        logger.debug("validateRequest() : idDocument = {}", idDocument);
        Throw.badRequest(logger, "idDocument cannot be null",  idDocument == null);
        Throw.badRequest(logger, "idDocument cannot be empty", idDocument.isBlank());
    }
    
}
