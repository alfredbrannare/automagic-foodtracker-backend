package com.automagic.foodtracker.service.auth;

import com.automagic.foodtracker.entity.User;
import com.automagic.foodtracker.repository.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerId = null;
        if (oAuth2User.getAttribute("sub") != null) {
            providerId = oAuth2User.getAttribute("sub");
        } else if (oAuth2User.getAttribute("id") != null) {
            providerId = oAuth2User.getAttribute("id");
        }

        String username = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getRegistrationId();

        User existing = userRepository.findByProviderAndProviderId(provider, providerId).orElse(null);

        if (existing == null) {
            User newUser = new User();
            newUser.setProvider(provider);
            newUser.setProviderId(providerId);
            newUser.setUsername(username);
            newUser.setEmail(email);

            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}
