package apicontracts.document;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "document")
public interface CreateDocumentContract {

    public final String ENDPOINT = "${document.ms.endpoint}";
    public final String PREFIX = "/document";
    public final String PATH = "";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    public record RequestCreateDocument(String title){}
    public record ResponseCreateDocument(String idDocument,String title){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Create a new document")
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    public abstract ResponseCreateDocument create(@RequestBody(required = false) RequestCreateDocument request);
    
}
