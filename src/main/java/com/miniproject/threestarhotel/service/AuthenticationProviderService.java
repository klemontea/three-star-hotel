//package com.miniproject.threestarhotel.service;
//
//import com.miniproject.threestarhotel.entity.OtpEntity;
//import com.miniproject.threestarhotel.repository.OtpRepos;
//import com.miniproject.threestarhotel.security.CustomUserDetails;
//import com.miniproject.threestarhotel.security.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@Transactional
//public class AuthenticationProviderService implements AuthenticationProvider {
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private Utils utils;
//
//    @Autowired
//    private OtpRepos otpRepos;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws
//            AuthenticationException {
//
//        String email = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        CustomUserDetails user = userService.loadUserByUsername(email);
//        renewOtp(user);
//
//        return checkPassword(user, password, bCryptPasswordEncoder);
//    }
//
//    private void renewOtp(CustomUserDetails u) {
//
//        String code = utils.generateUserId();
//        Optional<OtpEntity> userOtp = otpRepos.findOtpByEmail(u.getUsername());
//
//        if (userOtp.isPresent()) {
//            OtpEntity otp = userOtp.get();
//            otp.setCode(code);
//
//        } else {
//            OtpEntity otp = new OtpEntity();
//            otp.setEmail(u.getUsername());
//            otp.setCode(code);
//            otpRepos.save(otp);
//        }
//
//    }
//
//    private Authentication checkPassword(CustomUserDetails user
//            , String rawPassword, PasswordEncoder encoder) {
//
//        if (encoder.matches(rawPassword, user.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getAuthorities());
//
//        } else {
//            throw new BadCredentialsException("Bad credentials");
//
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
//    }
//}
