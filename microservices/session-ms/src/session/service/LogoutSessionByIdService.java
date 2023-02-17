package session.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apicore.access.SessionRequest;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;
import session.model.Session;

@Service
public class LogoutSessionByIdService {

    private static Logger logger = LoggerFactory.getLogger(LogoutSessionByIdService.class);

    @Autowired private MongoRepository repository;
    @Autowired private SessionRequest sessionRequest;

    public void logoutById(){
        logger.info("logoutById() : sessionRequest.getIdSession = {}", sessionRequest.getIdSession());
        validateRequest(sessionRequest.getIdSession());
        deleteByIdFromDatabase(sessionRequest.getIdSession());
    }

    private void deleteByIdFromDatabase(String id) {
        logger.debug("deleteByIdFromDatabase() : id = {}",id);
        repository.removeById(id, Session.class);
    }
    
    private void validateRequest(String id) {
        logger.debug("validateRequest() : request = {}", id);
        Throw.badRequest(logger,"id cannot be empty", id == null);
        Throw.badRequest(logger,"id cannot be empty", id.isBlank());
    }

}
