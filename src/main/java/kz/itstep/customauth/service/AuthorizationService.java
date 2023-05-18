package kz.itstep.customauth.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {
    private final Integer SHIFT = 6;    
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
