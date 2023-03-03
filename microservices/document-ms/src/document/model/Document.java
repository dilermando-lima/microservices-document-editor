package document.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@org.springframework.data.mongodb.core.mapping.Document("document")
public class Document {

    @Id
    private String id;
    private String title;
    
    @DBRef(lazy = true)
    private List<VersionDocument> versionList;


    public Document() {}

    public Document(String title) {
        this.title = title;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<VersionDocument> getVersionList() {
        return versionList;
    }
    public void setVersionList(List<VersionDocument> versionList) {
        this.versionList = versionList;
    }
    
    
}
