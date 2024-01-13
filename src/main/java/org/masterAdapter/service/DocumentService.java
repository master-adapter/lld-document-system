package org.masterAdapter.service;

import lombok.NonNull;
import org.masterAdapter.exception.InvalidAccessException;
import org.masterAdapter.exception.NotFoundException;
import org.masterAdapter.model.Document;
import org.masterAdapter.repository.DocumentManager;
import org.masterAdapter.repository.UserManager;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {
    private final UserManager userManager;
    private final DocumentManager documentManager;

    public DocumentService(@NonNull final UserManager userManager, @NonNull final DocumentManager documentManager) {
        this.userManager = userManager;
        this.documentManager = documentManager;
    }
    public String createDoc(@NonNull final String name, @NonNull final String userId){
        try {
            return documentManager.createDocument(name, userId);
        } catch (RuntimeException ecp){
            return "FAILED";
        }
    }

    public String modifyDoc(@NonNull final String docId, @NonNull final String newContent,
                             @NonNull final String userId){
        try {
            int version = documentManager.editDoc(docId, userId, newContent);
            return String.valueOf(version);
        } catch (NotFoundException | InvalidAccessException notFoundException){
            System.out.println("Not able to retrive the doc version due to :"+ notFoundException);
            return null;
        }
    }
    public List<String> getAllDocVersion(@NonNull final  String docId, @NonNull final String userId){
        try{
            return documentManager.getAllDocVersionContent(docId, userId);
        } catch (NotFoundException | InvalidAccessException notFoundException){
            System.out.println("Not able to retrive the doc version due to :"+ notFoundException);
            return new ArrayList<>();
        }
    }

    public String getOlderDocVersion(@NonNull final String docId, @NonNull final String userId, @NonNull final Integer version){
        try{
            return documentManager.getAllDocVersionContent(docId, userId).get(version-1);
        } catch (NotFoundException | InvalidAccessException notFoundException){
            System.out.println("Not able to retrive the doc version due to :"+ notFoundException);
            return null;
        }
    }

    public List<Document> getAllDoc(){
        return documentManager.getAllDocuments();
    }

}
