package APIModels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SavePhoto {

    private ArrayList<SavePhotoResponse> response;

    @Data
    public static class SavePhotoResponse {
        private int album_id;
        private int date;
        public int id;
        public int owner_id;
        public String access_key;
        public ArrayList<SizeOfUploadedPhoto> sizes;
        public String text;
        public boolean has_tags;

        @Data
        public static class SizeOfUploadedPhoto{
            public int height;
            public String url;
            public String type;
            public int width;
        }
    }
}
