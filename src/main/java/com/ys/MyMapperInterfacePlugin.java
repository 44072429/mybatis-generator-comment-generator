package com.ys;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * Created by DELL on 2017/12/13.
 */
public class MyMapperInterfacePlugin extends PluginAdapter {

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

        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModel;" );
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModelProperty;" );

        topLevelClass.addJavaDocLine( "" );
        topLevelClass.addAnnotation( "@ApiModel" );

        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        // 当某个表有多个主键时候 会生成一个 TabUserKey的一个专门表示主键的类

        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModel;" );
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModelProperty;" );

        topLevelClass.addAnnotation( "@ApiModel" );
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

        field.addAnnotation( "@ApiModelProperty(value=\"" + introspectedColumn.getRemarks() + "\")" );

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        interfaze.addJavaDocLine( "import org.apache.ibatis.annotations.Mapper;" );
        //interfaze.addJavaDocLine( "import org.apache.ibatis.annotations.Param;" );
        interfaze.addJavaDocLine( "" );
        interfaze.addAnnotation( "@Mapper" );

        return true;
    }
}
