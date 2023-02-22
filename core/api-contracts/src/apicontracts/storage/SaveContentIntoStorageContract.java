package apicontracts.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "storage")
public interface SaveContentIntoStorageContract {

    public final String ENDPOINT = "${storage.ms.endpoint}";
    public final String PREFIX = "/storage";
    public final String PATH = "/{idPrefix}/{idContent}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    public record ResponseGetContentFromStorage(String content){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Get content from storage")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public abstract void getContent(
        @PathVariable(name = "idPrefix") String idPrefix,
        @PathVariable(name = "idContent") String idContent
    );
    
}
