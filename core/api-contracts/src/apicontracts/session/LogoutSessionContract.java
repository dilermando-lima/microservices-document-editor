package apicontracts.session;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "session")
public interface LogoutSessionContract {

    public final String ENDPOINT = "${session.ms.endpoint}";
    public final String PREFIX = "/session";
    public final String PATH = "/logout";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "logout session")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public abstract void logout();
    
}
