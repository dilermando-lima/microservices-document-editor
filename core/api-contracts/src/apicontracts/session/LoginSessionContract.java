package apicontracts.session;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "session")
public interface LoginSessionContract {

    public final String ENDPOINT = "${session.ms.endpoint}";
    public final String PREFIX = "/session";
    public final String PATH = "/login";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    public record ResponseLoginSession(String authorization){}
    public record RequestLoginSession(String accountName){}

    @AccessType(AccessType.PUBLIC)
    @Operation(summary = "Do a login and grab a authorization token")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    public abstract ResponseLoginSession login(@RequestBody(required = false) RequestLoginSession request);
    
}
