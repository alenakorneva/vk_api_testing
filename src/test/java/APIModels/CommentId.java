package APIModels;

import lombok.Data;

@Data
public class CommentId {
    private CommentResponse response;

    @Data
    public static class CommentResponse {
        private int comment_id;
        private Object parents_stack;
    }
}
