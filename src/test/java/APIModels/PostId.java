package APIModels;

import lombok.Data;

@Data
public class PostId {

    private PostIdResponse response;

    @Data
    public static class PostIdResponse {
        private int post_id;
    }
}
