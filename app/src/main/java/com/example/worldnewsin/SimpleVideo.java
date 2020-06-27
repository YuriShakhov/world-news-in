package com.example.worldnewsin;

import java.text.ParseException;
import java.util.Date;

import com.example.worldnewsin.utils.DateManager;

public class SimpleVideo {
    private Id id;
    private Snippet snippet;

    private final int TITLE_MAX_LENGTH = 90;

    public String getVideoId() {
        return this.id.videoId;
    }

    public String getTitle() {
        String title = this.snippet.title;
        if (title.length() < TITLE_MAX_LENGTH) {
            return title;
        } else {
            return title.substring(0, TITLE_MAX_LENGTH);
        }
    }

    public String getPublishedAt() {
        try {
            return DateManager.formatDate(this.snippet.publishedAt);
        } catch (ParseException e) {
            return this.snippet.publishedAt;
        }
    }

    private class Id {
        private String kind;
        private String videoId;
    }

    private class Snippet {
        private String publishedAt;
        private String title;
    }
}
