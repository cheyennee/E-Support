package com.example.e_support.domain;

import java.util.ArrayList;

public class GovListView {
    public String more;
    public ArrayList<NewsListData> data;
    public class NewsListData{
        public int id;
        public String image;
        public String pubdate;
        public String title;
        public String url;
    }
}
