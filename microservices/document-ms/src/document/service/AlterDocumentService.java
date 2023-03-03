package document.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.document.AlterDocumentContract;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import document.model.Document;

@RestController
public class AlterDocumentService implements AlterDocumentContract {

    private static Logger logger = LoggerFactory.getLogger(AlterDocumentService.class);

    @Autowired private MongoRepository repository;

    @Override
    public void alter(String idDocument, RequestAlterDocument request) {
        logger.info("alter() : idDocument = {}, request = {}", idDocument , request);

        validateRequest(idDocument, request);

        alterDocumentIntoDatabase(buildMapAttrFromRequest(request), idDocument);

        logger.debug("alter() : altered sucessfully");
       
    }

    private Map<String, Object> buildMapAttrFromRequest(RequestAlterDocument request) {
        logger.debug("buildMapAttrFromRequest() : request = {}", request);

        var mapAttr = new HashMap<String, Object>();
        if(willAlterTitle.test(request)){
            mapAttr.put("title", request.title());
        }
        
        return mapAttr;
    }


    private void alterDocumentIntoDatabase(Map<String, Object> mapAttr, String idDocument) {
        logger.debug("alterDocumentIntoDatabase() : mapAttr = {}, idDocument = {}", mapAttr, idDocument);

        if(!mapAttr.isEmpty()) repository.alter(Document.class, idDocument, mapAttr);
    }

    private Predicate<RequestAlterDocument> willAlterTitle = r -> r != null && r.title() != null && !r.title().isBlank();


    private void validateRequest(String idDocument, RequestAlterDocument request) {
        logger.debug("validateRequest() : idDocument = {}, request = {}", idDocument, request);

        Throw.notFound(logger, "idDocument is required", idDocument == null );
        Throw.notFound(logger, "idDocument must not be empty", idDocument.isBlank());
        Throw.badRequest(
            logger, 
            "title must be more than 30 caract", 
            willAlterTitle.and(r ->  r.title().length() > 30).test(request));

    }
    
}
