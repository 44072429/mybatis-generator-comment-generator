package com.ys;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
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

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        interfaze.addJavaDocLine( "import org.apache.ibatis.annotations.Mapper;" );
        interfaze.addJavaDocLine( "" );
        interfaze.addAnnotation( "@Mapper" );

        return true;
    }
}
