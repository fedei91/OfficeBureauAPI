package com.example.officebureauapi.services;

import com.example.officebureauapi.entities.User;

public interface UserService {
    User register(User user);
    User getUserByEmail(String email);
    void save(User user);
    User update(String id);
    User delete(String id);
}
