package session.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("session")
public class Session {

    @Id
    private String id;
    private String accountId;
    private String accountName;

    public Session(){}

    public Session(String id, String accountId, String accountName) {
        this.id = id;
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public Session(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
