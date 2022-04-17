package com.leavelive.demo.controller;

import com.leavelive.demo.domain.Role;
import com.leavelive.demo.domain.User;
import com.leavelive.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @Autowired
    UserRepository repo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(){
        return "index.html";
    }
//    스프링 시큐리티가 해당 주소를 낚아챔
//    securityConfig 파일 생성 후 작동 안함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm.html";
    }
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm.html";
    }
    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole(Role.valueOf("ROLE_USER")); //Role 부여
        String rawPwd=bCryptPasswordEncoder.encode(user.getPassword()); //비밀번호 암호화
        user.setPassword(rawPwd);
        repo.save(user);
        return "redirect:/loginForm";
    }
    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "info";
    }

    // 보통 Pre를 많이 사용함
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "data";
    }
}
