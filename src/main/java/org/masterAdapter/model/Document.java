package org.masterAdapter.model;



import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.masterAdapter.model.enums.DocumentAccessType;
import org.masterAdapter.model.enums.UserAccessType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@ToString
public class Document {

      private final String name;
      private final String docId;
      @Setter private String currContent;
      private final AtomicInteger currVersion;
      private final String userId;
      @Setter private Map<String, UserAccessType> userAccessType;
      @Setter private DocumentAccessType docAccessType;
      private final Map<Integer, String> historicalDoc; //todo : make this immutable when calling get method;

    public Document(@NonNull final  String userId, @NonNull final String name ) {
        this.name = name;
        this.currContent = "";
        this.docId = String.valueOf(UUID.randomUUID());
        this.userId = userId;
        this.currVersion = new AtomicInteger(0);
        historicalDoc = new HashMap<>();
        this.docAccessType = DocumentAccessType.PRIVATE;
        this.userAccessType = new HashMap<>();
        userAccessType.put(userId, UserAccessType.OWNER);
    }

    public synchronized void addContent(@NonNull final String updatedContent){
        currContent = updatedContent;
       // currVersion.compareAndSet(currVersion, this.currVersion + 1);
    }


}
