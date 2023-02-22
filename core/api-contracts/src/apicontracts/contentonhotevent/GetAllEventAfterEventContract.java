package apicontracts.contentonhotevent;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "content")
public interface GetAllEventAfterEventContract {

    public final String ENDPOINT = "${content.on.hot.event.ms.endpoint}";
    public final String PREFIX = "/content";
    public final String PATH = "/version/{idVersion}/event/all-after/{idEvent}";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    public record ItemGetAllEventAfter(String idPreviousEvent, String idEvent, String line){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "Get all events after specific event")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.CREATED)
    public abstract List<ItemGetAllEventAfter> getAllEventAfter(
        @PathVariable(name = "idVersion") String idVersion,
        @PathVariable(name = "idEvent") String idEvent
    );
    
}
