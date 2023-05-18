package kz.itstep.customauth.controller;

import kz.itstep.customauth.model.User;
import kz.itstep.customauth.repo.UserRepository;
import kz.itstep.customauth.service.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {
    private AuthorizationService _authorizationService;
    private UserRepository _userRepository;
    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user){
        if(user.getLogin()==null){
            return new ResponseEntity<>("no user was found", HttpStatus.NOT_FOUND);
        }
        else {
            user.setPassword(
                    _authorizationService.generateToken(user.getLogin())
            );
            _userRepository.save(user);
            return new ResponseEntity<>("your token is: " + user.getPassword(), HttpStatus.OK);
        }
    }

}
