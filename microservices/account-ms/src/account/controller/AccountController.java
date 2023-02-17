package account.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import account.service.CreateAccountService;
import account.service.GetAccountByIdService;
import account.service.GetAccountByNameService;
import apicontracts.dto.AccountMS;
import apicontracts.dto.AccountMS.CreateAccountRequest;
import apicontracts.dto.AccountMS.CreateAccountResponse;
import apicontracts.dto.AccountMS.GetAccountByIdResponse;
import apicontracts.dto.AccountMS.GetAccountByNameResponse;
import apicore.access.AccessType;


@RestController
@RequestMapping(AccountMS.PREFIX_CONTROLLER)
public class AccountController {
    
    @Autowired private CreateAccountService createService;
    @Autowired private GetAccountByIdService getAccountByIdService;
    @Autowired private GetAccountByNameService getAccountByNameService;
  
    @AccessType(AccessType.PUBLIC)
    @PostMapping(AccountMS.PATH_POST_CREATE)
    public CreateAccountResponse create(@RequestBody(required = false)  CreateAccountRequest request){
        return createService.create(request);
    }
    
    @AccessType(AccessType.PUBLIC)
    @GetMapping(AccountMS.PATH_GET_BY_ID)
    public GetAccountByIdResponse getById(@PathVariable(name = "id") String id){
        return getAccountByIdService.getById(id);
    }

    @AccessType(AccessType.PUBLIC)
    @GetMapping(AccountMS.PATH_GET_BY_NAME)
    public GetAccountByNameResponse getByName(@PathVariable(name = "name") String name){
        return getAccountByNameService.getByName(name);
    }


}
