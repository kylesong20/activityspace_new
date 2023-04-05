package com.kyle.security.filter;

import com.kyle.security.entity.BUser;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import com.kyle.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("=================" + req.getRequestURI());
        logger.info("=================" + req.getHeader("X-token"));

//        if(req.getRequestURI().indexOf("admin") == -1 &&  req.getRequestURI().indexOf("activity") == -1 && req.getRequestURI().indexOf("ucenter") == -1) {
//
//            logger.info("=================ok");
////            chain.doFilter(req, res);
////            return;
//        }else {
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String token = req.getHeader("X-token");
            if (token != null && !"".equals(token.trim())) {
                authentication = getAuthentication(token);
            }
//            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, R.error().code(50014).message("Token失效，请重新登录"));
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(res, R.error().code(50014).message("Token失效，请重新登录"));
        }
//        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // token置于header里
//        String token = request.getHeader("X-token");
//        System.out.println(request.toString());
//        if (token != null && !"".equals(token.trim())) {

        String role = tokenManager.getUserRoleToken(token);
        String userName = tokenManager.getUserFromToken(token);
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (!StringUtils.isEmpty(role) && role.equals("ROLE_ADMIN")) {
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            authorities = new ArrayList<>();
            SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(role_admin);
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }
        }
        if (!StringUtils.isEmpty(role) && role.equals("ROLE_USER")) {
            BUser bUser = (BUser) redisTemplate.opsForValue().get(userName);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            authorities.add(authority);
        }
        if (!StringUtils.isEmpty(userName)) {
            return new UsernamePasswordAuthenticationToken(userName, token, authorities);
        }
        return null;


//        }
//        return null;
    }
}
