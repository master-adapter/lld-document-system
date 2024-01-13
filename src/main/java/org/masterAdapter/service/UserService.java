package org.masterAdapter.service;

import lombok.NonNull;
import org.masterAdapter.exception.DuplicateUserException;
import org.masterAdapter.exception.InvalidAccessException;
import org.masterAdapter.exception.NotFoundException;
import org.masterAdapter.model.User;
import org.masterAdapter.model.enums.UserAccessType;
import org.masterAdapter.repository.DocumentManager;
import org.masterAdapter.repository.UserManager;

import java.util.List;

public class UserService {
    private final UserManager userManager;
    private final DocumentManager documentManager;

    public UserService(@NonNull final UserManager userManager, @NonNull final DocumentManager documentManager) {
        this.userManager = userManager;
        this.documentManager = documentManager;
    }


    public String registerUser(@NonNull final String userId, @NonNull final String userName) {
        try {
            return userManager.createUser(userName, userId);
        } catch (DuplicateUserException ecp) {
            System.out.println("Not able to register user:" + ecp);
            return "FAILED_DUE_TO_DUPLICATE_ENTRY";
        }
    }

    public String giveAccessToOthers(@NonNull final String ownerId, @NonNull final String docId,
                                     @NonNull final String otherUserId, @NonNull final UserAccessType userAccessType) {
        try {
            documentManager.modifyUserLevelAccess(docId, ownerId, otherUserId, userAccessType);
            return "SUCCESS";
        } catch (NotFoundException ecp) {
            System.out.println("cant peform action due to:" + ecp);
            return "FAILED_DUE_TO_NO_DOC_PRESENT";
        } catch (InvalidAccessException invalidAccessException) {
            System.out.println("cant peform action due to:" + invalidAccessException);
            return "FAILED_DUE_TO_INVALID_ACCESS";
        }
    }

    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }
}
