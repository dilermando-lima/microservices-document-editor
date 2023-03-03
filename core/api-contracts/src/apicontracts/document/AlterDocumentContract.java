package apicontracts.document;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "document")
public interface AlterDocumentContract {

    public final String ENDPOINT = "${document.ms.endpoint}";
    public final String PREFIX = "/document";
    public final String PATH = "/{idDocument}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.PATCH;

    public record RequestAlterDocument(String title){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Alter a document")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.PATCH)
    public abstract void alter(@PathVariable(name = "idDocument") String idDocument, @RequestBody(required = false) RequestAlterDocument request);
    
}
