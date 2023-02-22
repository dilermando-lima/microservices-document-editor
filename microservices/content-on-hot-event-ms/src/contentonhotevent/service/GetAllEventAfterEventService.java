package contentonhotevent.service;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import apicontracts.contentonhotevent.GetAllEventAfterEventContract;

@RestController
public class GetAllEventAfterEventService implements GetAllEventAfterEventContract{

    @Override
    public List<ItemGetAllEventAfter> getAllEventAfter(String idVersion, String idEvent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEventAfter'");
    }
    
}
