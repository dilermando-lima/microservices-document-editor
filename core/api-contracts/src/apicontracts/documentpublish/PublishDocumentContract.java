package apicontracts.documentpublish;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "document")
public interface PublishDocumentContract {

    public final String ENDPOINT = "${document.publish.ms.endpoint}";
    public final String PREFIX = "/document";
    public final String PATH = "/publish/{idDocument}/version/{idVersion}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Publish a document version")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public abstract void publish(
        @PathVariable(name = "idDocument") String idDocument,
        @PathVariable(name = "idVersion") String idVersion
    );
    
}
