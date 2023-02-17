package session.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apicontracts.dto.SessionMS.GetInfoSessionResponse;
import apicore.access.SessionRequest;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import session.model.Session;

@Service
public class GetInfoSessionService {

    private static Logger logger = LoggerFactory.getLogger(GetInfoSessionService.class);

    @Autowired private MongoRepository repository;
    @Autowired private SessionRequest sessionRequest;

    public GetInfoSessionResponse getInfo(){
        logger.info("getInfo() : sessionRequest.getIdSession = {}", sessionRequest.getIdSession());
        validateRequest(sessionRequest.getIdSession());

        var session = getByIdFromDatabase(sessionRequest.getIdSession());

        validateAfterGetById(session);

        var response = convertEntityToResponse(session);

        logger.debug("getById() : response = {}", response);

        return response;
    }

    private void validateAfterGetById(Session entity) {
        logger.debug("validateAfterGetById() : entity = {}", entity);
        Throw.unauthorized(logger,"Session has not been found", entity == null);
    }

    private Session getByIdFromDatabase(String id) {
        logger.debug("getByIdFromDatabase() : id = {}",id);
        return repository.getOneById(id, Session.class);
    }

    private GetInfoSessionResponse convertEntityToResponse(Session entity) {
        logger.debug("convertEntityToResponse() : entity = {}", entity);
        return new GetInfoSessionResponse(entity.getId(), entity.getAccountId(), entity.getAccountName() );
    }

    private void validateRequest(String id) {
        logger.debug("validateRequest() : request = {}", id);
        Throw.badRequest(logger,"id cannot be empty", id == null);
        Throw.badRequest(logger,"id cannot be empty", id.isBlank());
    }
    
}
