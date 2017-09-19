package lowbrain.library.config;

import lowbrain.library.fn;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class YamlConfig extends YamlConfiguration {
    private JavaPlugin plugin;
    private String path;

    public YamlConfig(String path, JavaPlugin plugin) {
        this(path, plugin, true);
    }

    public YamlConfig(String path, JavaPlugin plugin, boolean load) {
        super(); // call YamlConfiguration constructor

        if (fn.StringIsNullOrEmpty(path) || plugin == null)
            throw new NullArgumentException("require path and plugin arguments !");

        this.path = path;
        this.plugin = plugin;

        if (load)
            load();
    }

    public FileConfiguration load() {
        return this.reload();
    }

    public FileConfiguration reload() {
        return this.reload(false);
    }

    public FileConfiguration reload(boolean replace) {
        File file = new File(plugin.getDataFolder(), path);

        if (!file.exists() && !save(replace))
            return null;

        try {
            this.load(file);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load ressource : " + path);
            return null;
        }

        return this;
    }

    public boolean save() {
        return save(false);
    }

    public boolean save(boolean replace) {
        File file = new File(plugin.getDataFolder(), path);

        if (!file.exists() && replace) {
            try {
                file.getParentFile().mkdir();
                plugin.saveResource(path, true);
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to save ressource : " + path);
                return false;
            }
        }
        return true;
    }
}