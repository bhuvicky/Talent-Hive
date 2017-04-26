package com.bhuvanesh.talenthive.search.model;

import com.bhuvanesh.talenthive.dance.model.Dance;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.List;

public class AllTalents {

    public static final int TALENT_TYPE_PHOTO = 1;
    public static final int TALENT_TYPE_STORY = TALENT_TYPE_PHOTO + 1;
    public static final int TALENT_TYPE_DANCE = TALENT_TYPE_STORY + 1;
    public static final int TALENT_TYPE_SINGING = TALENT_TYPE_DANCE + 1;

    public List<StoryFeedResponse> storyFeedList;
    public List<PhotoFeedResponse> photoFeedList;
    public List<Dance> danceList;
}
