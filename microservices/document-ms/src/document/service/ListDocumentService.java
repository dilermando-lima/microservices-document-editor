package document.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.document.ListDocumentContract;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import document.model.Document;

@RestController
public class ListDocumentService implements ListDocumentContract {
    private static Logger logger = LoggerFactory.getLogger(ListDocumentService.class);

    @Autowired private MongoRepository repository;

    @Override
    public List<ResponseDocument> list(Integer pageNum, Integer pageSize) {
        logger.info("list() : pageNum = {} ,  pageSize = {} ",pageNum, pageSize);

        validateRequest(pageNum,pageSize);

        var documentList = listFromDataBase(pageNum, pageSize);
        var responseList = convertEntityListToResponse(documentList);

        logger.debug("list() : response.listSize() = {}", responseList.size());

        return responseList;
        
    }

    private void validateRequest(Integer pageNum, Integer pageSize) {
        logger.debug("validateRequest() : pageNum = {} ,  pageSize = {} ",pageNum, pageSize);
        Throw.badRequest(logger,"pageNum cannot be less than 1", pageNum != null && pageNum <= 0);
        Throw.badRequest(logger,"pageSize cannot be less than 1", pageSize != null && pageSize <= 0);
    }

    private List<Document> listFromDataBase(Integer pageNum, Integer pageSize) {
        logger.debug("getByNameFromDatabase(): pageNum = {} ,  pageSize = {} ", pageNum, pageSize);
        return repository.list(Document.class, pageNum, pageSize);
    }

    private List<ResponseDocument> convertEntityListToResponse(List<Document> entityList) {
        logger.debug("convertEntityToResponse() : entityList.size = {}", entityList.size());

        return entityList.stream().map( entity -> {
           return new ResponseDocument(
                entity.getId(), 
                entity.getTitle(), 
                entity.getVersionList().stream().map(
                    version -> new ResponseVersion(version.getId(), version.getStatus())
                ).toList()
            );
        }).toList();
        
    }

    
}
