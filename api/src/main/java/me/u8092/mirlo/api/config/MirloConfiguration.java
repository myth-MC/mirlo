package me.u8092.mirlo.api.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.ConfigurationSection;

import java.io.File;
import java.util.Objects;

@Getter
public final class MirloConfiguration {
    static final LoggerWrapper LOGGER = new LoggerWrapper() {
        @Override
        public void info(String message, Object... args) {
            Mirlo.get().getLogger().info("[config] " + message, args);
        }

        @Override
        public void warn(String message, Object... args) {
            Mirlo.get().getLogger().warn("[config] " + message, args);
        }

        @Override
        public void error(String message, Object... args) {
            Mirlo.get().getLogger().error("[config] " + message, args);
        }
    };
    private final SimpleYamlConfig settingsConfig, channelsConfig, variablesConfig;
    private final Settings settings = new Settings();
    private final Channels channels = new Channels();
    private final Variables variables = new Variables();

    public MirloConfiguration(final @NotNull File pluginFolder) {
        this.settingsConfig = new SimpleYamlConfig(new File(pluginFolder, "settings.yml"));
        this.channelsConfig = new SimpleYamlConfig(new File(pluginFolder, "channels.yml"));
        this.variablesConfig = new SimpleYamlConfig(new File(pluginFolder, "variables.yml"));
    }

    public void load() {
        try {
            settingsConfig.load(Objects.requireNonNull(Mirlo.class.getResource("/settings.yml")));
            channelsConfig.load(Objects.requireNonNull(Mirlo.class.getResource("/channels.yml")));
            variablesConfig.load(Objects.requireNonNull(Mirlo.class.getResource("/variables.yml")));
        } catch (Exception e) {
            throw new IllegalStateException("Error while loading configuration", e);
        }

        loadValues();
    }

    public void loadValues() {
        settings.debug = settingsConfig.getBoolean("debug");
        channels.section = channelsConfig.getConfigurationSection("channels");
        variables.section = variablesConfig.getConfigurationSection("variables");

        // Initialize global variables
        Mirlo.get().getVariableManager().clear(); // This makes reloads VERY unstable
        Mirlo.get().getVariableManager().initialize("global", "global");

        // Initialize channels
        Mirlo.get().getChannelManager().initialize(); // Will cause exceptions when reloading
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Settings {
        private boolean debug;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Channels {
        private ConfigurationSection section;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Variables {
        private ConfigurationSection section;
    }
}