package net.quimmilho.mineproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
    id = "mineproxy",
    name = "MineProxy",
    version = BuildConstants.VERSION,
    description = "A plugin",
    url = "quimmilho.net",
    authors = {"QuimMilho"}
)

public class Core {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }

}
