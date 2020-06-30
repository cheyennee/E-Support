package com.example.e_support.domain;

import java.util.ArrayList;

public class PhotoListView {
    public String more;
    public ArrayList<PhotoListItem> data;
    public class PhotoListItem{
        public int id;
        public String image;
        public String title;
    }
}
