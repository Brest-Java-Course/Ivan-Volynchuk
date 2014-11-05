package com.epam.brest.courses.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserImpl.class, name = "userimpl") })
public interface User{


    public Long getUserId();

    public void setUserId(Long userId);

    public String getLogin();

    public void setLogin(String login);

    public String getUserName();

    public void setUserName(String userName);
}
