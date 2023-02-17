package session.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import apicontracts.dto.SessionMS;
import apicontracts.dto.SessionMS.GetInfoSessionResponse;
import apicontracts.dto.SessionMS.LoginSessionRequest;
import apicontracts.dto.SessionMS.LoginSessionResponse;
import apicore.access.AccessType;
import session.service.GetInfoSessionService;
import session.service.LoginSessionService;
import session.service.LogoutSessionByIdService;

@RestController
@RequestMapping(SessionMS.PREFIX_CONTROLLER)
public class SessionController {

    private static Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired private LoginSessionService loginSessionService;
    @Autowired private LogoutSessionByIdService logoutSessionByIdService;
    @Autowired private GetInfoSessionService getInfoSessionService;

    @AccessType(AccessType.PUBLIC)
    @PostMapping(SessionMS.PATH_POST_LOGIN)
    public LoginSessionResponse login(@RequestBody(required = false) LoginSessionRequest request  ){
        logger.info("login()");
        return loginSessionService.login(request);
    }

    @AccessType(AccessType.PRIVATE_JWT)
    @PostMapping(SessionMS.PATH_POST_LOGOUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void logoutById(){
        logger.info("logoutById()");
        logoutSessionByIdService.logoutById();
    }

    @AccessType(AccessType.PRIVATE_JWT_FORCE_CHECKING)
    @GetMapping(path =  SessionMS.PATH_GET_INFO)
    public GetInfoSessionResponse getInfo(){
        logger.info("getInfo()");
        return getInfoSessionService.getInfo();
    }
    
}
