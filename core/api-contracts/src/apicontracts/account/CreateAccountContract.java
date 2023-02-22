package apicontracts.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "account")
public interface CreateAccountContract {

    public final String ENDPOINT = "${account.ms.endpoint}";
    public final String PREFIX = "/account";
    public final String PATH = "";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    public record RequestCreateAccount(String name) {}
    public record ResponseCreateAccount(String id, String name){}

    @AccessType(AccessType.PUBLIC)
    @Operation(summary = "Create new account")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public abstract ResponseCreateAccount create(@RequestBody(required = false) RequestCreateAccount request);
    
}
