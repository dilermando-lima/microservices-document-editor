package apicontracts.dto;

@SuppressWarnings("java:S1075")
public class SessionMS {

    public static final String ENDPOINT_ENV = "${session.ms.endpoint}";
    public static final String PREFIX_CONTROLLER = "/session";

    public static final String PATH_POST_LOGIN = "/login";
    public record LoginSessionResponse(String authorization){}
    public record LoginSessionRequest(String accountName){}

    public static final String PATH_POST_LOGOUT = "/logout";

    public static final String PATH_GET_INFO = "/info";
    public record GetInfoSessionResponse(String id, String accountId, String accountName){}

}
