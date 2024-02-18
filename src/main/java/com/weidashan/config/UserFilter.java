package com.weidashan.config;

//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;

//@Component
// implements GlobalFilter, Ordered
public class UserFilter{
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        /*ServerHttpRequest request = exchange.getRequest();
//        if(request.getMethod().name().equals("OPTIONS")) {
//            return chain.filter(exchange);
//        }
//        System.out.println(request.getMethod().name());
//        HttpHeaders headers = request.getHeaders();
//        List<String> list = headers.get("token");
//        if(list.size() > 0) {
//            String token = list.get(0);
//            JWTVerifier jwt = JWT.require(Algorithm.HMAC256("wangyu")).build();
//            DecodedJWT verify = jwt.verify(token);
//            String username = verify.getClaim("username").asString();
//            System.out.println("当前用户是:" + username);
//            List<String> backurls = verify.getClaim("backurls").asList(String.class);
//            System.out.println(backurls);
//            // 从request拿到当前请求地址
//            System.out.println(request.getURI().getPath());
//        }*/
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
}
