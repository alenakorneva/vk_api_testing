package APIModels;

import lombok.Data;

@Data
public class Reaction {

   private LikeResponse response;

   @Data
   public static class LikeResponse {

      private int liked;
      private int copied;
   }
}
