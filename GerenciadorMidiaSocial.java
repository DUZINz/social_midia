package com.exercicio.social;

import com.exercicio.social.adapters.SocialPlatformAdapter;
import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.models.Estatisticas;
import com.exercicio.social.models.Publicacao;
import com.exercicio.social.adapters.BaseAdapter;
import com.exercicio.social.strategies.PostingStrategy;
import com.exercicio.social.strategies.ImmediateStrategy;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class GerenciadorMidiaSocial {
    private final SocialMediaFactory factory;
    private final Map<String, SocialPlatformAdapter> adapters = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public GerenciadorMidiaSocial(SocialMediaFactory factory) {
        this.factory = factory;
    }

    public void registerPlatform(String platformKey) {
        SocialPlatformAdapter adapter = factory.create(platformKey);
        if (adapter != null) {
            adapters.put(platformKey, adapter);
            adapter.authenticate();
        }
    }

    public UnifiedResponse<Publicacao> publish(String platformKey, Conteudo conteudo, PostingStrategy strategy) {
        SocialPlatformAdapter adapter = adapters.get(platformKey);
        if (adapter == null) return new UnifiedResponse<>(false, "Platform not registered: " + platformKey, null);
        final CompletableFuture<UnifiedResponse<Publicacao>> future = new CompletableFuture<>();
        strategy.execute(adapter, conteudo, LocalDateTime.now(), scheduler, future::complete);
        try { return future.get(); } catch (Exception e) { return new UnifiedResponse<>(false, "Execution error: " + e.getMessage(), null); }
    }

    public UnifiedResponse<Publicacao> schedule(String platformKey, Conteudo conteudo, LocalDateTime when, PostingStrategy strategy) {
        SocialPlatformAdapter adapter = adapters.get(platformKey);
        if (adapter == null) return new UnifiedResponse<>(false, "Platform not registered: " + platformKey, null);
        final CompletableFuture<UnifiedResponse<Publicacao>> future = new CompletableFuture<>();
        strategy.execute(adapter, conteudo, when, scheduler, future::complete);
        try { return future.get(); } catch (Exception e) { return new UnifiedResponse<>(false, "Execution error: " + e.getMessage(), null); }
    }

    public void publishToAll(Conteudo conteudo) {
        PostingStrategy immediate = new ImmediateStrategy();
        adapters.values().forEach(a -> scheduler.submit(() -> {
            UnifiedResponse<Publicacao> r = a.publishNow(conteudo);
            System.out.println("[publishToAll] " + a.platformName() + " -> " + r.getMessage());
        }));
    }

    public void scheduleToAll(Conteudo conteudo, LocalDateTime when) {
        adapters.values().forEach(a -> {
            // por padrão usamos ScheduledStrategy por plataforma
            PostingStrategy strategy = factory.getDefaultStrategy();
            strategy.execute(a, conteudo, when, scheduler, r -> System.out.println("[scheduleToAll] " + a.platformName() + " -> " + r.getMessage()));
        });
    }

    public UnifiedResponse<Estatisticas> getStats(String platformKey) {
        SocialPlatformAdapter adapter = adapters.get(platformKey);
        if (adapter == null) return new UnifiedResponse<>(false, "Platform not registered: " + platformKey, null);
        try {
            String anyId = adapter instanceof BaseAdapter ? ((BaseAdapter) adapter).published.keySet().stream().findFirst().orElse(null) : null;
            if (anyId == null) return new UnifiedResponse<>(false, "No publications found", null);
            return adapter.getStatistics(anyId);
        } catch (Exception e) {
            return new UnifiedResponse<>(false, "Error retrieving stats: " + e.getMessage(), null);
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public Set<String> listPlatforms() {
        return adapters.keySet();
    }
}
