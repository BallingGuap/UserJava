package kz.itstep.customauth.service;

import kz.itstep.customauth.Exceptions.UserException;
import kz.itstep.customauth.model.User;
import kz.itstep.customauth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private UserRepository _userRepository;
    private final Integer SHIFT = 6;
    public void saveUser(User user) throws  UserException{
        var generatedToken = generateToken(user.getLogin());
        if(user == null || user.getLogin() == null) {
         throw new UserException("User or his login is null");
        }
        if(_userRepository.findUserByLoginAndPassword(user.getLogin(),generatedToken)
                                                     .isPresent()){
            throw new UserException("System already have this user: " + user.getLogin() );
        }
        user.setLogin(generatedToken);
        _userRepository.save(user);
    }

    public String generateToken(String login){
        return shepherLogin(login);
    }

    public boolean isValid(String login){
        return _isValid(login);
    }

    private boolean _isValid(String login){
        return IntStream.range(0, login.length() - 1)
                .allMatch(i -> login.charAt(i + 1) - login.charAt(i) == SHIFT);
    }

    private static String fromChars(List<Character> list){
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    private String shepherLogin(String login){
        return fromChars(
                        login.chars().
                        mapToObj(ch -> ((char) ch) ).
                        toList());
    }

}
