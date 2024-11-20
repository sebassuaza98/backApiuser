package api_user.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import api_user.auth.repository.UserRepository;
import api_user.user.model.UserModel;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository iUserRepository;
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        UserModel userModel = this.iUserRepository.findByUserId(userid);
        if(userModel == null) {
            throw  new UsernameNotFoundException(userid);
        }
        return new User(userModel.getUserId(), userModel.getPassword(), new ArrayList<>());
    }
}
