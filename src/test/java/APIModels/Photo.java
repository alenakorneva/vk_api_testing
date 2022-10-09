package APIModels;

import lombok.Data;

@Data
public class Photo {

    private PhotoResponse response;

    @Data
    public static class PhotoResponse {

        private String server;
        private String photo;
        private String hash;
    }
}
