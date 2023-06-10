package io.taraxacum.libs.plugin.task;

import org.bukkit.plugin.Plugin;

/**
 * @see TaskTicker
 * @author Final_ROOT
 * @version 2
 * @param <T> Target object type
 */
public interface AbstractTask<T> {

    Plugin getPlugin();

    int getPriority();

    boolean isSync();
}
