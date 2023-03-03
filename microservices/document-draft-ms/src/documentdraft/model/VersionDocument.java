package documentdraft.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("version_document")
public class VersionDocument {

    @Id
    private String id;
    private String status;

    public VersionDocument() {}

    public VersionDocument(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



}
