package org.masterAdapter;

import org.masterAdapter.model.enums.UserAccessType;
import org.masterAdapter.repository.DocumentManager;
import org.masterAdapter.repository.UserManager;
import org.masterAdapter.service.DocumentService;
import org.masterAdapter.service.UserService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        UserManager userManager = new UserManager();
        DocumentManager documentManager = new DocumentManager();
        UserService userService = new UserService(userManager, documentManager);
        DocumentService documentService = new DocumentService(userManager, documentManager);

        userService.registerUser("u1", "UN1");
        userService.registerUser("u2", "UN2");
        userService.registerUser("u3", "UN3");

        System.out.println(userService.getAllUsers());

        String d1 = documentService.createDoc("d1", "u1");
        String d2 = documentService.createDoc("d2","u3");
        System.out.println(documentService.getAllDoc());


        userService.giveAccessToOthers("u1", d1,"u2", UserAccessType.EDITOR);
        userService.giveAccessToOthers("u1", d1,"u3", UserAccessType.READER);
        userService.giveAccessToOthers("u3",d2,"u2",UserAccessType.EDITOR);
        System.out.println(documentService.getAllDoc());



        documentService.modifyDoc(d1,"my name is so n so","u1");
        documentService.modifyDoc(d2,"my name is so23xfga","u2");
        //System.out.println(documentService.getAllDocVersion(d1,"u1"));
        System.out.println(documentService.getAllDoc());


        documentService.modifyDoc(d1,"tests is this","u2");
        documentService.modifyDoc(d2,"tests is this","u1");
        documentService.modifyDoc(d1,"tests is thiswewe","u3");
        System.out.println(documentService.getAllDoc());




       String c1 = documentService.getOlderDocVersion(d1, "u2", 1);
       String c2 = documentService.getOlderDocVersion(d2,"u3",1);

        documentService.modifyDoc(d1,c1+"afasdfasdfasd","u2");
        documentService.modifyDoc(d2,c2+"afasdfasdfasd","u3");
        System.out.println(documentService.getAllDoc());




    }
}