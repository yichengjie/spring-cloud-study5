package com.yicj.study.feign.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component
public class FeignGetInterceptor implements RequestInterceptor {
    @Autowired
    private ObjectMapper objectMapper ;
    @Override
    public void apply(RequestTemplate template) {
        if ("GET".equalsIgnoreCase(template.method()) && template.requestBody() != null){
            Request.Body body = template.requestBody();
            if (body.length() ==0){
                return;
            }
            try {
                JsonNode jsonNode = objectMapper.readTree(body.asBytes());
                // 这里必须设置为空，否则报错
                template.body(Request.Body.empty()) ;
                Map<String, Collection<String>> queries = buildQuery(jsonNode);
                template.queries(queries) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // 暂时仅支持平铺得对象属性传递，不支持对象嵌套对象以及数组传递
    private Map<String, Collection<String>> buildQuery(JsonNode jsonNode) {
        Map<String, Collection<String>> queries = new HashMap<>() ;
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        while (iterator.hasNext()){
            Map.Entry<String, JsonNode> next = iterator.next();
            String name = next.getKey();
            JsonNode value = next.getValue();
            queries.put(name, Collections.singletonList(value.asText())) ;
        }
        return queries ;
    }
}
