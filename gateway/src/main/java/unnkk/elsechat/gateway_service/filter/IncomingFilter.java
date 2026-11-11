package unnkk.elsechat.gateway_service.filter;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import unnkk.elsechat.gateway_service.util.JwtUtil;

@Component
@RequiredArgsConstructor
public class IncomingFilter implements GlobalFilter, Ordered {
    private JwtUtil jwtUtil;

    @Override
    @NullMarked
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(exchange.getRequest().getURI().getPath().startsWith("/api/auth")) return chain.filter(exchange);
        String auth = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(auth == null || !auth.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().setComplete();
        }

        auth = auth.substring(7);
        if(!jwtUtil.validateToken(auth)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().setComplete();
        }

        String username = jwtUtil.extractUsername(auth);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-username", username)
                .headers(headers -> headers.remove("Authorization"))
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
