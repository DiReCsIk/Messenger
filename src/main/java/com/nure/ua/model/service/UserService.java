package com.nure.ua.model.service;

import com.nure.ua.exception.ServiceException;
import com.nure.ua.model.entity.User;

public interface UserService {
    User selectUserByPhone(String userPhone);

    User selectUserByNickname(String userNickname);

    void insertEntity(User user) throws ServiceException;
}
