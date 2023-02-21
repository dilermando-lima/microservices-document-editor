package apicontracts.account;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "account")
public interface GetAccountByNameContract {

    public final String ENDPOINT = "${account.ms.endpoint}";
    public final String PREFIX = "/account";
    public final String PATH = "/get-by-name/{name}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    public record ResponseGetAccountByName(String id, String name) {}

    @AccessType(AccessType.PUBLIC)
    @Operation(summary = "Get an account by name")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.GET)
    public abstract ResponseGetAccountByName getByName(@PathVariable(name = "name") String name);
 
}
