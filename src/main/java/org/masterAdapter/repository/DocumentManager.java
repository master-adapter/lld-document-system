package org.masterAdapter.repository;

import lombok.NonNull;
import org.masterAdapter.exception.InvalidAccessException;
import org.masterAdapter.exception.NotFoundException;
import org.masterAdapter.model.Document;
import org.masterAdapter.model.enums.DocumentAccessType;
import org.masterAdapter.model.enums.UserAccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentManager {
    private HashMap<String, Document> allDocuments;

    public DocumentManager() {
        this.allDocuments = new HashMap<>();
    }

    public String createDocument(@NonNull final String name, @NonNull final String userId){
        Document document = new Document(userId, name);
        allDocuments.put(document.getDocId(),document);
        return document.getDocId();
    }

    public Document getDocument(@NonNull final String docId){
        if(!allDocuments.containsKey(docId)){
            throw new NotFoundException("Document not present");
        }
        return allDocuments.get(docId);
    }

    public List<Document> getAllDocuments(){
        if(allDocuments.isEmpty()){
            return new ArrayList<>();
        }
        List<Document> allDoc = new ArrayList<>();
        allDocuments.forEach((k,v) -> allDoc.add(v));
        return allDoc;
    }

    public Integer editDoc(@NonNull final String docId,@NonNull final String userId, @NonNull final String newContent){
       performBasicValidations(docId);
        Document doc = allDocuments.get(docId);
        checkIfUserCanPerformEditAction(userId, doc);

        int currV = doc.getCurrVersion().get();
        doc.getHistoricalDoc().put(currV, doc.getCurrContent());
        doc.getCurrVersion().compareAndSet(currV, currV+1);
        doc.setCurrContent(newContent);
        return doc.getCurrVersion().get();
    }

    public void modifyDocLevelAccess(@NonNull final  String docId, @NonNull final DocumentAccessType documentAccessType){
        performBasicValidations(docId);
        Document doc = allDocuments.get(docId);
        doc.setDocAccessType(documentAccessType);
    }

    public void modifyUserLevelAccess(@NonNull final String docId, @NonNull final String ownerId,
                                      @NonNull final String otherUserId,
                                      @NonNull final UserAccessType userAccessType){
        performBasicValidations(docId);
        Document doc = allDocuments.get(docId);
        checkIfUserCanPerformEditAction(ownerId, doc);
        doc.getUserAccessType().put(otherUserId, userAccessType);
    }

    public List<String> getAllDocVersionContent(@NonNull final String docId, @NonNull final String userId){
        performBasicValidations(docId);
        List<String> allVersionContent = new ArrayList<>();
        Document currDoc = allDocuments.get(docId);
        if(!currDoc.getUserAccessType().containsKey(userId)){
            throw new InvalidAccessException("user doesn't have permission to view");
        }
        currDoc.getHistoricalDoc().forEach((k,v) ->{
            allVersionContent.add(v);
        });
        return allVersionContent;
    }



    private boolean isUserCanPerformEditorAction(@NonNull final String userId, @NonNull final Document document){
        if( !document.getUserAccessType().containsKey(userId)){
            return false;
        }
        UserAccessType currAccessLevel = document.getUserAccessType().get(userId);
        if(currAccessLevel == UserAccessType.EDITOR || currAccessLevel == UserAccessType.OWNER){
            return true;
        }
        else return false;
    }

    public void checkIfUserCanPerformEditAction(@NonNull final String userId, @NonNull final Document document){
        if(!isUserCanPerformEditorAction(userId, document)){
            throw new InvalidAccessException("Users doesn't have access to perform edit");
        }
    }

    private void performBasicValidations(@NonNull final String docId){
        if(!allDocuments.containsKey(docId)){
            throw new NotFoundException("Doc not present");
        }
    }



}
