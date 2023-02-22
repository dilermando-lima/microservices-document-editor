package contentaddevent.service;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.contentaddevent.AddEventContract;

@RestController
public class AddEventService implements AddEventContract {

    @Override
    public ResponseAddEvent addEvent(RequestAddEvent request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEvent'");
    }
    
}
