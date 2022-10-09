package APIModels;

import lombok.Data;

@Data
public class UploadServer {

   private UploadServerResponse response;

   @Data
   public static class UploadServerResponse {

      private String album_id;
      private String upload_url;
      private String user_id;
   }
}
