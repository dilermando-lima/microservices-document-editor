package apicore.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import apicore.exception.Throw;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessIntercept  implements HandlerInterceptor{

    private static Logger logger = LoggerFactory.getLogger(AccessIntercept.class);

    @Autowired private SessionRequest sessionRequest;
    @Autowired private JwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;	
   
            if(method.getMethod().isAnnotationPresent(AccessType.class) ){
                logger.debug("preHandle() : AccessType = {} ", method.getMethod().getAnnotation(AccessType.class).value());

                if(AccessType.PUBLIC.equals(method.getMethod().getAnnotation(AccessType.class).value())){
                    refreshMetadaRequest(method , request);

                }else if(AccessType.PRIVATE_JWT.equals(method.getMethod().getAnnotation(AccessType.class).value())){
                    refreshMetadaRequest(method , request);
                    refreshSessionRequest(request);

                }else if(AccessType.PRIVATE_JWT_FORCE_CHECKING.equals(method.getMethod().getAnnotation(AccessType.class).value())){
                    refreshMetadaRequest(method , request);
                    refreshSessionRequest(request);
                    forceSessionChecking(sessionRequest);

                }

                
            }else if( !method.getMethod().isAnnotationPresent(AccessType.class) && !(method instanceof ErrorController) ) {      
                Throw.any(logger, HttpStatus.INTERNAL_SERVER_ERROR,"All endpoint need to have %s annotation".formatted(AccessType.class.getName()) ); 
            }
        
        }
        
        return true;

    }

    private void forceSessionChecking(SessionRequest session) {
        logger.debug("forceSessionChecking() : session = {}" , session);	
        // will check in cache services if token must be refreshed
        // TODO:
    }

    private void refreshMetadaRequest(HandlerMethod method, HttpServletRequest request){
        logger.debug("refreshMetadaRequest() : init");	
        sessionRequest.setController(method.getBeanType().getName());
        sessionRequest.setMethod(method.getMethod().getName());
        sessionRequest.setIp(request.getHeader("X-Forwarded-For") == null ? request.getRemoteHost()  : request.getHeader("X-Forwarded-For"));
        sessionRequest.setUrl(request.getRequestURL().toString());
        logger.debug("refreshMetadaRequest() : controller = {}, method = {}  ", sessionRequest.getController(), this.sessionRequest.getMethod());		
	}

    private void refreshSessionRequest(HttpServletRequest request){
        logger.debug("refreshSessionRequest() : init");
        var sessionRequestFromJwt = jwtUtils.retriveSubject(request.getHeader(JwtUtils.NAME_HEADER_AUTHORIZATION),SessionRequest.class);
        
        logger.debug("refreshSessionRequest() : sessionRequestFromJwt = {}", sessionRequestFromJwt);
        sessionRequest.setController(sessionRequestFromJwt.getController());
        sessionRequest.setDateTimeInitSession(sessionRequestFromJwt.getDateTimeInitSession());
        sessionRequest.setIdAccount(sessionRequestFromJwt.getIdAccount());
        sessionRequest.setIdSession(sessionRequestFromJwt.getIdSession());
	}

    
}
