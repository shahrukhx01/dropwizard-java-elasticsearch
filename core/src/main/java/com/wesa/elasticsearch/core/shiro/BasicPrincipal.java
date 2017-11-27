package com.wesa.elasticsearch.core.shiro;

import com.wesa.elasticsearch.core.model.User;

public class BasicPrincipal {

    private User user;

    public BasicPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
