package com.ys;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;

/**
 * Created by DELL on 2018/5/25.
 */
public class MybatisGeneratorUtil {
    static ColumnOverride getColumnOverride(IntrospectedTable table, String columnName) {
        TableConfiguration configuration = table.getTableConfiguration();
        if (configuration != null) {
            List<ColumnOverride> columnOverrides = configuration.getColumnOverrides();
            for (ColumnOverride co : columnOverrides) {
                if (columnName.equalsIgnoreCase( co.getColumnName() )) {
                    return co;
                }
            }
        }

        return null;
    }

    static String getColumnOverrideColumnName(IntrospectedTable table, String columnName) {
        ColumnOverride columnOverride = getColumnOverride(table,columnName);
        if(columnOverride != null && columnOverride.getColumnName().length() > 0) {
            return columnOverride.getColumnName();
        }
        return null;
    }
}
