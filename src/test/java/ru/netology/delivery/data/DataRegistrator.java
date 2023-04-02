package ru.netology.delivery.data;

import lombok.*;

@Value
public class DataRegistrator {
    private String login;
    private String password;
    private String status;
}