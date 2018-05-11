package com.ys;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * Created by DELL on 2017/12/13.
 */
public class PluginAdapterForHibernate extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }


    /**
     * Model文件被生成时候调用
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        topLevelClass.addJavaDocLine( "import javax.persistence.*;" );
//        topLevelClass.addJavaDocLine( "import org.apache.ibatis.annotations.Mapper;" );
        topLevelClass.addJavaDocLine( "" );
        topLevelClass.addAnnotation( "@Entity" );
        topLevelClass.addAnnotation( "@Table(name=\"" +introspectedTable.getTableConfiguration().getTableName() + "\")" );
        return true;
    }

    /**
     * 数据库每个字段被生成的时候会调用
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn column : primaryKeyColumns) {
            if(column.getActualColumnName().equals( introspectedColumn.getActualColumnName()) ) {
                field.addAnnotation( "@Id" );
            }
        }

        if(introspectedColumn.isAutoIncrement()) {
            field.addAnnotation( "@GeneratedValue(strategy = GenerationType.AUTO)" );
        }

        field.addAnnotation( "@Column(name=\"" + introspectedColumn.getActualColumnName() + "\")" );
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        topLevelClass.addFileCommentLine( "modelPrimaryKeyClassGenerated 1" );

        topLevelClass.addJavaDocLine( "modelPrimaryKeyClassGenerated 2" );
        topLevelClass.addJavaDocLine( "modelPrimaryKeyClassGenerated 3" );
        topLevelClass.addAnnotation( "modelPrimaryKeyClassGenerated 4" );

        return true;
    }
}
