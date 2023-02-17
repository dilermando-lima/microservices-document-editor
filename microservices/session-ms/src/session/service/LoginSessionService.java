package session.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import apicontracts.dto.AccountMS;
import apicontracts.dto.AccountMS.GetAccountByNameResponse;
import apicontracts.dto.SessionMS.LoginSessionRequest;
import apicontracts.dto.SessionMS.LoginSessionResponse;
import apicore.access.JwtUtils;
import apicore.access.SessionRequest;
import apicore.exception.Throw;
import apicore.integration.RestClient;
import apicore.repository.MongoRepository;
import session.model.Session;


@Service
public class LoginSessionService {

    private static Logger logger = LoggerFactory.getLogger(LoginSessionService.class);

    @Autowired
    private MongoRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value(AccountMS.ENDPOINT_ENV + AccountMS.PREFIX_CONTROLLER + AccountMS.PATH_GET_BY_NAME)
    private String urlApiGetAccountByName;
    
    public LoginSessionResponse login(LoginSessionRequest request){
        logger.info("login() : request = {}", request);
        validateRequest(request);

        var account = consumeApiGetAccountByName(request.accountName());
        var session = convertAccountResponseToEntity(account);

        session = saveIntoDatabase(session);

        return convertSessionToResponse(session);
    }


    private GetAccountByNameResponse consumeApiGetAccountByName(String name){
        logger.debug("consumeApiGetAccountByName() : urlApiGetAccountByName = {} , name = {}", urlApiGetAccountByName , name);
        return RestClient
                .get(urlApiGetAccountByName, GetAccountByNameResponse.class)
                .requestUriValue(name)
                .onError404(err -> {
                    Throw.any(logger, HttpStatus.NOT_FOUND, "account with name '%s' has not been found".formatted(name));
                    return null;
                })
                .call()
                .getResponse();
    }

    public record GetByIdResponse(String name, String id) {}

    private Session convertAccountResponseToEntity(GetAccountByNameResponse accountResponse ){
        logger.debug("convertRequestToEntity() : accountResponse = {}", accountResponse);
        return new Session(accountResponse.id(), accountResponse.name());
    }

    private Session saveIntoDatabase(Session session){
        logger.debug("saveIntoDatabase() : session = {}", session);
        return repository.insert(session);
    }

    private LoginSessionResponse convertSessionToResponse(Session session){
        logger.debug("convertSessionToResponse() : session = {} ", session);
        return new LoginSessionResponse( 
            jwtUtils.tokenize(
                new SessionRequest(
                    session.getId(), 
                    session.getAccountId(), 
                    session.getAccountName(), 
                    null
                )
            )
         );
    }

    private void validateRequest(LoginSessionRequest request) {
        logger.debug("validateRequest() : request = {}", request);
        Throw.badRequest(logger,"request cannot be empty", request == null);
        Throw.badRequest(logger,"accountName cannot be empty", request.accountName() == null || request.accountName().isBlank());
    }
    
}
