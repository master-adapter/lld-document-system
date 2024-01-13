package org.masterAdapter.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class User {
    private String name;
    private String userId;

    public User(@NonNull final  String name, @NonNull final String userId) {
        this.name = name;
        this.userId = userId;
    }

}
