package apicore.access;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@RequestScope
public class SessionRequest{

    @JsonProperty("na") private String nameAccount;
    @JsonProperty("ia") private String idAccount;
    @JsonProperty("is") private String idSession;
    @JsonProperty("dis") private Long dateTimeInitSession;
    @JsonIgnore private String controller;
    @JsonIgnore private String method;
    @JsonIgnore private String url;
    @JsonIgnore private String ip;

    public SessionRequest(){}

    public SessionRequest(String idSession, String idAccount, String nameAccount, Long dateTimeInitSession) {
        this.idSession = idSession;
        this.idAccount = idAccount;
        this.nameAccount = nameAccount;
        this.dateTimeInitSession = dateTimeInitSession;
    }

    public String getNameAccount() {
        return nameAccount;
    }
    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }
    public String getIdAccount() {
        return idAccount;
    }
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
    public String getIdSession() {
        return idSession;
    }
    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }
    public Long getDateTimeInitSession() {
        return dateTimeInitSession;
    }
    public void setDateTimeInitSession(Long dateTimeInitSession) {
        this.dateTimeInitSession = dateTimeInitSession;
    }
    public String getController() {
        return controller;
    }
    public void setController(String controller) {
        this.controller = controller;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    

    
    
}

