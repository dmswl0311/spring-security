package com.leavelive.demo.oauth;

import com.leavelive.demo.auth.PrincipalDetails;
import com.leavelive.demo.domain.Role;
import com.leavelive.demo.domain.User;
import com.leavelive.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    UserRepository repo;
    
    //구글로부터 받은 userRequest 데이터에 대한 후처리가 되는 메소드
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("userRequest :: "+userRequest.getClientRegistration()); //registrationId로 어떤 OAuth롤 로그인했는지 알 수 있음
//        System.out.println("userRequest :: "+userRequest.getAccessToken());

        OAuth2User oAuth2User=super.loadUser(userRequest);
//        System.out.println("회원 정보 :: "+oAuth2User.getAttributes()); // 회원 프로필 정보
        // 회원 가입 진행
        String provider=userRequest.getClientRegistration().getRegistrationId(); //google
        System.out.println("provider 정보 :: "+provider);
        String providerId=null,email=null,username=null,img_url=null;
        String role="ROLE_USER";
        String nickname="부끄러운 어피치"; // 랜덤 닉네임으로 수정할 예정
        if(provider.equals("google")){
            providerId=oAuth2User.getAttribute("sub");
            email=oAuth2User.getAttribute("email");
            username=provider+"_"+providerId;
            img_url=oAuth2User.getAttribute("picture");
        }else if(provider.equals("kakao")){
            System.out.println("kakao 로그인");
            Map<String,Object> response=oAuth2User.getAttributes();
            Map<String,Object> bridge=(Map<String,Object>)response.get("kakao_account");
            Map<String,Object> hash=(Map<String,Object>)bridge.get("profile");
            providerId=String.valueOf(response.get("id"));
            username=provider+"_"+providerId;
            email=String.valueOf(bridge.get("email"));
            img_url=String.valueOf(hash.get("thumbnail_image_url"));
        }
        User userEntity=repo.findByUsername(username);
        if(userEntity==null){
//            회원가입
            System.out.println("최초 소셜 로그인");
            User user=new User();
            user.setImg_url(img_url);
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(Role.valueOf(role));
            user.setNickname(nickname);
            repo.save(user);
            return new PrincipalDetails(user,oAuth2User.getAttributes());
        }else{
            System.out.println("소셜 로그인을 한 적이 있습니다"); // 회원 프로필 정보
            return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
        }

    }
}
