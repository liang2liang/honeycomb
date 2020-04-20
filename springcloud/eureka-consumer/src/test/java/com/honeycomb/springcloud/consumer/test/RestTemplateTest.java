package com.honeycomb.springcloud.consumer.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class RestTemplateTest {

    private RestTemplate restTemplate;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGet() {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/user?name={1}", String.class, "honeycomb");

        Map<String, String> param = new HashMap<>(1);
        param.put("name", "honeycomb");
        responseEntity = restTemplate.getForEntity("http://localhost:8080/user?name={name}", String.class, param);

        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/user?name={name}")
                .build()
                .expand("honeycomb")
                .encode()
                .toUri();
        responseEntity = restTemplate.getForEntity(uri, String.class);

        //当不需要关注请求相应除body外的其他内容时，就可以使用getForObject

        String result = restTemplate.getForObject("http://localhost:8080/user?name={1}", String.class, "honeycomb");
        result = restTemplate.getForObject("http://localhost:8080/user?name={name}", String.class, param);
        result = restTemplate.getForObject(uri, String.class);
    }
}
