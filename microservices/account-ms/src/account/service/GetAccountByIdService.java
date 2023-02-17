package account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import account.model.Account;
import apicontracts.dto.AccountMS.GetAccountByIdResponse;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;

@Service
public class GetAccountByIdService {

    private static Logger logger = LoggerFactory.getLogger(GetAccountByIdService.class);

    @Autowired private MongoRepository repository;

    public GetAccountByIdResponse getById(String id){
        logger.info("getById() : request = {}", id);
        validateRequest(id);

        var account = getByIdFromDatabase(id);

        validateAfterGetById(account);

        var response = convertEntityToResponse(account);

        logger.debug("getById() : response = {}", response);

        return response;
    }

    private Account getByIdFromDatabase(String id) {
        logger.debug("getByIdFromDatabase() : id = {}",id);
        return repository.getOneById(id, Account.class);
    }

    private void validateRequest(String id) {
        logger.debug("validateRequest() : id = {}",id);
        Throw.badRequest(logger,"id cannot be empty", id == null || id.isBlank());
    }


    private void validateAfterGetById(Account entity) {
        logger.debug("validateAfterGetById() : entity = {}", entity);
        Throw.notFound(logger,"Account has not been found", entity == null);
    }

    private GetAccountByIdResponse convertEntityToResponse(Account entity) {
        logger.debug("convertEntityToResponse() : entity = {}", entity);
        return new GetAccountByIdResponse(entity.getId(), entity.getName());
    }

}
