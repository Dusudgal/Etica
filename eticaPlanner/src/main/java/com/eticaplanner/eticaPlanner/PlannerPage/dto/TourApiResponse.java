package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourApiResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        public static class Body {
            private Items items;
            private int totalCount;

            @Getter
            @Setter
            public static class Items{
                private List<TourApiDTO> item;
            }
        }
    }
}

