package account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import account.model.Account;
import apicontracts.dto.AccountMS.CreateAccountRequest;
import apicontracts.dto.AccountMS.CreateAccountResponse;
import apicore.exception.Throw;
import apicore.repository.MongoRepository;

@Service
public class CreateAccountService {

    private static Logger logger = LoggerFactory.getLogger(CreateAccountService.class);

    @Autowired private MongoRepository repository;

    public CreateAccountResponse create(CreateAccountRequest request){
        logger.info("create() : request = {}", request);
        validateRequest(request);

        var account = convertRequestToEntity(request);
        validateEntityBeforeSave(account);
        account = saveIntoDatabase(account);

        var response = convertEntityToResponse(account);

        logger.debug("create() : response = {}", response);
        return response;
    }

    private void validateRequest(CreateAccountRequest request) {
        logger.debug("validateRequest() : request = {}", request);

        Throw.badRequest(logger, "request cannot be empty",             request == null);
        Throw.badRequest(logger, "name cannot be empty",                request.name() == null || request.name().isBlank());
        Throw.badRequest(logger, "name must be more than 20 caract",    request.name().length() > 20);
    }

    private void validateEntityBeforeSave(Account entity) {
        logger.debug("validateModelBeforeSave() : entity = {}", entity);
        Throw.badRequest(
            logger, 
            "account with name '%s' already has been created".formatted(entity.getName()), 
            repository.existsByAttrEqual("name", entity.getName(), Account.class)
        );
    }

    private Account saveIntoDatabase(Account entity) {
        logger.debug("save() : entity = {}", entity);
        return repository.insert(entity);
    }

    private CreateAccountResponse convertEntityToResponse(Account entity) {
        logger.debug("convertEntityToResponse() : entity = {}", entity);
        return new CreateAccountResponse(entity.getId(), entity.getName());
    }

    private Account convertRequestToEntity(CreateAccountRequest request) {
        logger.debug("convertRequestToEntity() : request = {}", request);
        return new Account(request.name());
    }

}
