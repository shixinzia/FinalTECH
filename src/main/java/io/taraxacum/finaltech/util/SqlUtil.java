package io.taraxacum.finaltech.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class SqlUtil {

    /**
     * It may be updated or deprecated in the future.
     * I wish it to be deprecated.
     * @return null if it's not safe.
     */
    @Nullable
    public static String getSafeSql(@Nonnull String sql) {
        sql = sql.replace("\\", "\\\\");
        sql = sql.replace("'", "\\'");
        sql = sql.replace("-", "\\-");
        sql = sql.replace("\"", "\\\"");
        return sql;
    }
}
