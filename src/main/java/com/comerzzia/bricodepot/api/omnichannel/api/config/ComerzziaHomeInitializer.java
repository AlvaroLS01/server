package com.comerzzia.bricodepot.api.omnichannel.api.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ComerzziaHomeInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComerzziaHomeInitializer.class);
    private static final String CONFIG_FILENAME = "comerzzia.xml";
    private static final int SEARCH_DEPTH = 6;

    private ComerzziaHomeInitializer() {
    }

    public static void ensureHomeConfigured() {
        Path configuredHome = resolveConfiguredHome();
        if (configuredHome != null) {
            if (Files.exists(configuredHome.resolve(CONFIG_FILENAME))) {
                System.setProperty("COMERZZIA_HOME", configuredHome.toString());
                LOGGER.debug("Using existing COMERZZIA_HOME at {}", configuredHome);
                return;
            }
            LOGGER.warn("Configured COMERZZIA_HOME {} does not contain {}", configuredHome, CONFIG_FILENAME);
        }

        Path repository = resolveMavenRepository();
        if (repository == null || !Files.isDirectory(repository)) {
            LOGGER.warn("Unable to resolve the local Maven repository to locate {}", CONFIG_FILENAME);
            return;
        }

        Path configFile = locateConfig(repository.resolve(Paths.get("com", "comerzzia")));
        if (configFile == null) {
            LOGGER.warn("Could not find {} under {}", CONFIG_FILENAME, repository);
            return;
        }

        Path home = configFile.getParent();
        System.setProperty("COMERZZIA_HOME", home.toString());
        LOGGER.info("Configured COMERZZIA_HOME to {}", home);
    }

    private static Path resolveConfiguredHome() {
        String property = System.getProperty("COMERZZIA_HOME");
        if (property == null || property.trim().isEmpty()) {
            property = System.getenv("COMERZZIA_HOME");
        }
        if (property == null || property.trim().isEmpty()) {
            return null;
        }
        return Paths.get(property.trim());
    }

    private static Path resolveMavenRepository() {
        String repoProperty = System.getProperty("maven.repo.local");
        if (repoProperty != null && !repoProperty.trim().isEmpty()) {
            return Paths.get(repoProperty.trim());
        }
        String userHome = System.getProperty("user.home");
        if (userHome == null || userHome.trim().isEmpty()) {
            return null;
        }
        return Paths.get(userHome.trim(), ".m2", "repository");
    }

    private static Path locateConfig(Path base) {
        if (base == null || !Files.isDirectory(base)) {
            return null;
        }
        try (Stream<Path> stream = Files.walk(base, SEARCH_DEPTH)) {
            Optional<Path> config = stream
                    .filter(path -> CONFIG_FILENAME.equals(path.getFileName().toString()))
                    .findFirst();
            return config.orElse(null);
        }
        catch (IOException exception) {
            LOGGER.warn("Error while searching for {} under {}: {}", CONFIG_FILENAME, base, exception.getMessage());
            return null;
        }
    }
}
