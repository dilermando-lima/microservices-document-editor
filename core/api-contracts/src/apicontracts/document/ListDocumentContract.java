package apicontracts.document;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import apicore.access.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "document")
public interface ListDocumentContract {

    public final String ENDPOINT = "${document.ms.endpoint}";
    public final String PREFIX = "/document";
    public final String PATH = "";
    public final String URI = ENDPOINT + PREFIX + PATH;
    public final RequestMethod REQUEST_METHOD = RequestMethod.GET;

    public record ResponseVersion(String idVersion, String status){}
    public record ResponseDocument(
        String idDocument,
        String title,
        List<ResponseVersion> versionList
    ){}

    @AccessType(AccessType.PRIVATE_JWT)
    @Operation(summary = "List all documents and their versions")
    @RequestMapping(path = PREFIX + PATH, method = RequestMethod.GET)
    public abstract List<ResponseDocument> list(
        @RequestParam(name = "pageNum", required = false) Integer pageNum,
        @RequestParam(name = "pageSize", required = false) Integer pageSize
    );
    
}
