package account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="account")
public class Account {

    @Id
    private String id;
    private String name;

    public Account() {
    }

    public Account(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account [" + (name != null ? "name=" + name + ", " : "") + (id != null ? "id=" + id : "") + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


}
