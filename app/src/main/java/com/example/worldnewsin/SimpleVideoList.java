package com.example.worldnewsin;

import java.io.Serializable;
import java.util.List;

public class SimpleVideoList implements Serializable {
    public List<SimpleVideo> items;

    public List<SimpleVideo> getItems() {
        return items;
    }

    public int getSize() {
        return items == null ? 0 : items.size();
    }

    public SimpleVideo getVideo (int position) {
        return items.get(position);
    }
}
