package apicontracts.account;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "account")
public interface GetAccountByIdContract {

    public final String ENDPOINT = "${account.ms.endpoint}";
    public final String PREFIX = "/account";
    public final String PATH = "/{id}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    public record ResponseGetAccountById(String id, String name) {}

    @AccessType(AccessType.PUBLIC)
    @Operation( summary = "Get an account by id")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.GET)
    public abstract ResponseGetAccountById getById( @PathVariable(name = "id") String id );
 
}
