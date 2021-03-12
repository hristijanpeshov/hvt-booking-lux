package com.hvt.booking_lux.web.requestHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RequestHelper {

    public static String relativeUrl = "http://localhost:9090";

    public static HttpEntity<MultiValueMap<String, Object>> createRequestMap(Object... objects)
    {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        for(int i=0;i<objects.length;i+=2)
        {
            map.add(objects[i].toString(),objects[i+1].toString());
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        return request;
    }

}
