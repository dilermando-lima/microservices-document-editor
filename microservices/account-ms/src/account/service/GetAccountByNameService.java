package account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import account.model.Account;
import apicontracts.dto.AccountMS.GetAccountByNameResponse;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;

@Service
public class GetAccountByNameService {

    private static Logger logger = LoggerFactory.getLogger(GetAccountByNameService.class);

    @Autowired private MongoRepository repository;

    public GetAccountByNameResponse getByName(String name){
        logger.info("getByName() : request = {}", name);
        validateRequest(name);

        var account = getByNameFromDatabase(name);

        validateAfterGetById(account);

        var response = convertEntityToResponse(account);

        logger.debug("getByName() : response = {}", response);

        return response;
    }

    private Account getByNameFromDatabase(String name) {
        logger.debug("getByNameFromDatabase() : name = {}",name);
        return repository.getOneByAttrEqual("name", name, Account.class);
    }

    private void validateRequest(String name) {
        logger.debug("validateRequest() : name = {}",name);
        Throw.badRequest(logger,"name cannot be empty", name == null || name.isBlank());
    }


    private void validateAfterGetById(Account entity) {
        logger.debug("validateAfterGetById() : entity = {}", entity);
        Throw.notFound(logger,"Account has not been found", entity == null);
    }

    private GetAccountByNameResponse convertEntityToResponse(Account entity) {
        logger.debug("convertEntityToResponse() : entity = {}", entity);
        return new GetAccountByNameResponse(entity.getId(), entity.getName());
    }

}
