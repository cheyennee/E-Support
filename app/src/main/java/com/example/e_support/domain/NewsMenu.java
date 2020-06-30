package com.example.e_support.domain;

import java.util.ArrayList;

public class NewsMenu {
    public int retcode;
    public ArrayList<NewsListData> data;

    public class NewsListData{
        public int id;
        public String title;
        public ArrayList<NewsTabData> children;
    }
    public class NewsTabData{
        public int id;
        public String title;
        public String url;
    }
}
