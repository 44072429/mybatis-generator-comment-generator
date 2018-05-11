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


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        topLevelClass.addFileCommentLine( "Base 1" );

        topLevelClass.addJavaDocLine( "Base 2" );
        topLevelClass.addJavaDocLine( "Base 3" );
        topLevelClass.addAnnotation( "Base 4" );
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {

        topLevelClass.addFileCommentLine( "Field 1" );

        topLevelClass.addJavaDocLine( "Field 2" );
        topLevelClass.addJavaDocLine( "Field 3" );
        topLevelClass.addAnnotation( "Field 4" );
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
