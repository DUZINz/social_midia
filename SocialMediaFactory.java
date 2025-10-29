package com.exercicio.social;

import com.exercicio.social.adapters.*;
import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.strategies.PostingStrategy;
import com.exercicio.social.strategies.ScheduledStrategy;

public class SocialMediaFactory {
    private final EnvConfig config;
    private final PostingStrategy defaultStrategy = new ScheduledStrategy();

    public SocialMediaFactory(EnvConfig config) {
        this.config = config;
    }

    public SocialPlatformAdapter create(String key) {
        switch (key.toLowerCase()) {
            case "twitter": return new TwitterAdapter(config);
            case "instagram": return new InstagramAdapter(config);
            case "linkedin": return new LinkedInAdapter(config);
            case "tiktok": return new TikTokAdapter(config);
            default: return null;
        }
    }

    public PostingStrategy getDefaultStrategy() {
        return defaultStrategy;
    }
}
