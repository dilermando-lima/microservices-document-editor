package apicontracts.dto;

@SuppressWarnings("java:S1075")
public class AccountMS {

    public static final String ENDPOINT_ENV = "${account.ms.endpoint}";
    public static final String PREFIX_CONTROLLER = "/account";

    public static final String PATH_POST_CREATE = "";
    public record CreateAccountRequest(String name) {}
    public record CreateAccountResponse(String id, String name){}

    public static final String PATH_GET_BY_ID = "/{id}";
    public record GetAccountByIdResponse(String id, String name) {}
    public record GetAccountByIdRequest(String id){}
    
    public static final String PATH_GET_BY_NAME = "/get-by-name/{name}";
    public record GetAccountByNameResponse(String id, String name) {}

}
