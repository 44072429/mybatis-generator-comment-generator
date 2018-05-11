package com.ys;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * Created by DELL on 2017/12/13.
 */
public class PluginAdapterForHibernate extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        return true;
    }
    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                               IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method,
                                               TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
            Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        interfaze.addImportedType( new FullyQualifiedJavaType("org.springframework.stereotype.Repository" ));
        interfaze.addImportedType( new FullyQualifiedJavaType("org.springframework.data.jpa.repository.JpaRepository" ));
        interfaze.addAnnotation( "@Repository" );
        interfaze.addSuperInterface( new FullyQualifiedJavaType("JpaRepository<" + introspectedTable.getTableConfiguration().getTableName() + ",Integer"+ ">"));

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

        // private static final long serialVersionUID = 1L;

        // 以下代码不需要调用，因为本来生成的model就带serialVersionUID
//        Field serialVersionUID = new Field(  );
//        serialVersionUID.setVisibility( JavaVisibility.PRIVATE );
//        serialVersionUID.setStatic( true );
//        serialVersionUID.setFinal( true );
//        serialVersionUID.setType( new FullyQualifiedJavaType( "long" ) );
//        serialVersionUID.setName( "serialVersionUID" );
//        serialVersionUID.setInitializationString( "1L" );
//        topLevelClass.addField( serialVersionUID );

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
