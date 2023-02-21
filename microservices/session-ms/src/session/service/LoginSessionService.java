package session.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.account.GetAccountByNameContract;
import apicontracts.session.LoginSessionContract;
import apicore.access.JwtUtils;
import apicore.access.SessionRequest;
import apicore.exception.Throw;
import apicore.integration.RestClient;
import apicore.repository.MongoRepository;
import session.model.Session;


@RestController
public class LoginSessionService implements LoginSessionContract{

    private static Logger logger = LoggerFactory.getLogger(LoginSessionService.class);

    @Autowired
    private MongoRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value(GetAccountByNameContract.URI)
    private String urlApiGetAccountByName;
    
    @Override
    public ResponseLoginSession login(RequestLoginSession request){
        logger.info("login() : request = {}", request);
        validateRequest(request);

        var account = consumeApiGetAccountByName(request.accountName());
        var session = convertAccountResponseToEntity(account);

        session = saveIntoDatabase(session);

        return convertSessionToResponse(session);
    }


    private GetAccountByNameContract.ResponseGetAccountByName consumeApiGetAccountByName(String name){
        logger.debug("consumeApiGetAccountByName() : urlApiGetAccountByName = {} , name = {}", urlApiGetAccountByName , name);
        return RestClient
                .get(urlApiGetAccountByName, GetAccountByNameContract.ResponseGetAccountByName.class)
                .requestUriValue(name)
                .onError404(err -> {
                    Throw.any(logger, HttpStatus.NOT_FOUND, "account with name '%s' has not been found".formatted(name));
                    return null;
                })
                .call()
                .getResponse();
    }

    public record GetByIdResponse(String name, String id) {}

    private Session convertAccountResponseToEntity(GetAccountByNameContract.ResponseGetAccountByName accountResponse ){
        logger.debug("convertRequestToEntity() : accountResponse = {}", accountResponse);
        return new Session(accountResponse.id(), accountResponse.name());
    }

    private Session saveIntoDatabase(Session session){
        logger.debug("saveIntoDatabase() : session = {}", session);
        return repository.insert(session);
    }

    private ResponseLoginSession convertSessionToResponse(Session session){
        logger.debug("convertSessionToResponse() : session = {} ", session);
        return new ResponseLoginSession( 
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

    private void validateRequest(RequestLoginSession request) {
        logger.debug("validateRequest() : request = {}", request);
        Throw.badRequest(logger,"request cannot be empty", request == null);
        Throw.badRequest(logger,"accountName cannot be empty", request.accountName() == null || request.accountName().isBlank());
    }
    
}
