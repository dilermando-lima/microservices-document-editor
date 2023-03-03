package document.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.document.CreateDocumentContract;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import document.model.Document;


@RestController
public class CreateDocumentService implements CreateDocumentContract{

    private static Logger logger = LoggerFactory.getLogger(CreateDocumentService.class);

    @Autowired private MongoRepository repository;

    @Override
    public ResponseCreateDocument create(RequestCreateDocument request) {
        logger.info("create() : request = {}", request);
        validateRequest(request);

        var document = convertRequestToEntity(request);

        validateDocumentBeforeSave(document);

        document = saveIntoDatabase(document);

        var response = convertDocumentToResponse(document);

        logger.debug("create() : response = {}", response);
        return response;
    }

    private void validateDocumentBeforeSave(Document document) {
        logger.debug("validateDocumentBeforeSave() : document = {}", document);
        Throw.badRequest(
            logger, 
            "document with title '%s' already has been created".formatted(document.getTitle()), 
            repository.existsByAttrEqual("title",document.getTitle(), Document.class)
        );
    }

    private Document saveIntoDatabase(Document entity) {
        logger.debug("save() : entity = {}", entity);
        return repository.insert(entity);
    }

    private void validateRequest(RequestCreateDocument request) {
        logger.debug("validateRequest() : request = {}", request);

        Throw.badRequest(logger, "request cannot be empty",           request == null);
        Throw.badRequest(logger, "title cannot be empty",             request.title() == null || request.title().isBlank());
        Throw.badRequest(logger, "title must be more than 30 caract", request.title().length() > 30);
    }

    private Document convertRequestToEntity(RequestCreateDocument request) {
        logger.debug("convertRequestToEntity() : request = {}", request);
        return new Document(request.title());
    }
    
    private ResponseCreateDocument convertDocumentToResponse(Document document) {
        logger.debug("convertDocumentToResponse() : document = {}", document);
        return new ResponseCreateDocument(document.getId(), document.getTitle());
    }

}
