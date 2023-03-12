//package com.kyle.activity.security;
//
//import com.kyle.ucenter.service.AuditService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@Primary
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private AuditService auditService;
//
////    @Autowired
////    private UserService userService;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//////        Audit user = auditService.findOneUserByName(username);
////        Audit user = auditService.getOne(new QueryWrapper<Audit>().eq("num", username));
//////        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
////        if(user == null){
////            return null;
////        }
////        log.info("user=={}",user);
////        List<GrantedAuthority> authority = new ArrayList<>();
////        authority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
////        return new org.springframework.security.core.userdetails.User(
//////                user.getUsername(),
////                user.getNum(),
////                user.getPassword(),
////                authority
////                );
//        return null;
//    }
//}
