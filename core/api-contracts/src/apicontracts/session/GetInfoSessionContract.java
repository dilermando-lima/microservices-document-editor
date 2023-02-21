package apicontracts.session;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "session")
public interface GetInfoSessionContract {

    public final String ENDPOINT = "${session.ms.endpoint}";
    public final String PREFIX = "/session";
    public final String PATH = "/info";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    public record ResponseGetInfoSession(String id, String accountId, String accountName){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Get info from authorization token")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.GET)
    public abstract ResponseGetInfoSession getInfo();
    
}
