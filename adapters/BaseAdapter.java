package com.exercicio.social.adapters;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.models.Publicacao;
import com.exercicio.social.models.Estatisticas;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseAdapter implements SocialPlatformAdapter {
    protected final EnvConfig config;
    protected boolean authed = false;
    public final Map<String, Publicacao> published = new ConcurrentHashMap<>();
    protected final Random rng = new Random();

    protected BaseAdapter(EnvConfig config) {
        this.config = config;
    }

    @Override
    public UnifiedResponse<Boolean> authenticate() {
        authed = true; // simulação
        return new UnifiedResponse<>(true, "Authenticated to " + platformName(), true);
    }

    @Override
    public UnifiedResponse<Publicacao> publishNow(Conteudo conteudo) {
        if (!authed) authenticate();
        String id = platformName() + "-" + UUID.randomUUID();
        Publicacao p = new Publicacao(id, platformName(), conteudo, LocalDateTime.now());
        published.put(id, p);
        return new UnifiedResponse<>(true, "Published on " + platformName(), p);
    }

    @Override
    public UnifiedResponse<Publicacao> schedulePublish(Conteudo conteudo, LocalDateTime when) {
        if (!authed) authenticate();
        String id = platformName() + "-sched-" + UUID.randomUUID();
        Publicacao p = new Publicacao(id, platformName(), conteudo, when);
        published.put(id, p);
        return new UnifiedResponse<>(true, "Scheduled on " + platformName(), p, Map.of("scheduledFor", when.toString()));
    }

    @Override
    public UnifiedResponse<Estatisticas> getStatistics(String publicationId) {
        if (!published.containsKey(publicationId)) {
            return new UnifiedResponse<>(false, "Publication not found", null);
        }
        Estatisticas s = new Estatisticas(rng.nextInt(1000), rng.nextInt(300), rng.nextInt(200));
        return new UnifiedResponse<>(true, "Stats retrieved", s);
    }

    @Override
    public UnifiedResponse<Boolean> deletePublication(String publicationId) {
        if (published.remove(publicationId) != null) {
            return new UnifiedResponse<>(true, "Deleted", true);
        }
        return new UnifiedResponse<>(false, "Not found", false);
    }
}
