package com.nure.ua.model.service.impl;

import com.nure.ua.exception.ServiceException;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.repository.UserRepository;
import com.nure.ua.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nure.ua.util.constant.UtilConstants.USER_ALREADY_EXISTS_EXCEPTION;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User selectUserByPhone(String userPhone) {
        return userRepository.selectUserByPhone(userPhone);
    }

    @Override
    public User selectUserByNickname(String userNickname) {
        return userRepository.selectUserByNickname(userNickname);
    }

    @Override
    public void insertEntity(User user) throws ServiceException {
        if (userRepository.selectUserByPhone(user.getPhone()) != null) {
            throw new ServiceException(USER_ALREADY_EXISTS_EXCEPTION);
        }
        userRepository.save(user);
    }
}
