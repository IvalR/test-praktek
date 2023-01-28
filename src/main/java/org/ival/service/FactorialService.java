package org.ival.service;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class FactorialService {

    @Transactional
    public Response factorial(Integer n){

        Map<Integer,Integer> fac = new HashMap<>();
        int result = 1;
        for (int i = 0 ; i <= n ; i ++){
            result = result * n;
            if (i == 0){
              result = 1;
            }
            fac.put(n, result);

        }

        return Response.ok().build();
    }
}
