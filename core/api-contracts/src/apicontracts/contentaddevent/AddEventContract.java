package apicontracts.contentaddevent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "content")
public interface AddEventContract {

    public final String ENDPOINT = "${content.add.event.ms.endpoint}";
    public final String PREFIX = "/content";
    public final String PATH = "/event";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.POST;

    public record RequestAddEvent(String idDocument, String idVersion, String idLastEvent, String line){}
    public record ResponseAddEvent(String idPreviousEvent, String idNewEvent){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Add new content event into a document version")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public abstract ResponseAddEvent addEvent(@RequestBody(required = false) RequestAddEvent request);
    
}
