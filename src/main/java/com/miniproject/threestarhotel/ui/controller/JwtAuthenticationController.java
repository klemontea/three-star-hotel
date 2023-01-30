//package com.miniproject.threestarhotel.ui.controller;
//
//import com.miniproject.threestarhotel.security.JwtTokenUtil;
//import com.miniproject.threestarhotel.service.JwtTokenBlacklistService;
//import com.miniproject.threestarhotel.service.JwtUserDetailsService;
//import com.miniproject.threestarhotel.ui.model.request.JwtRequest;
//import com.miniproject.threestarhotel.ui.model.response.JwtResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin
//public class JwtAuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private JwtUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtTokenBlacklistService jwtBlacklist;
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(
//            @RequestBody JwtRequest request) throws Exception {
//
//        String email = request.getEmail();
//        String password = request.getPassword();
//
//        authenticate(email, password);
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticate(String email, String password) throws Exception {
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(email, password));
//
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//
//    @RequestMapping(value = "/signout", method = RequestMethod.GET)
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
//
//        if (token != null) {
//
//            String extractToken = token.substring(7);
//
//            // add this token to blacklist to prevent further using
//            jwtBlacklist.addJwtTokenBlacklist(extractToken);
//
//            return ResponseEntity.ok(new JwtResponse(token));
//
//        } else {
//
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//    }
//}
