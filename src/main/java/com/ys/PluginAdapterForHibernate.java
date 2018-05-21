package com.ys;

import org.mybatis.generator.api.*;
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
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        return false;
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
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
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

        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String entityKeyName = introspectedTable.getTableConfiguration().getDomainObjectName() + "Key";

        if(introspectedTable.getPrimaryKeyColumns().size() > 1) {

            if(introspectedTable.getNonPrimaryKeyColumns().size() == 0) {
                entityName = entityKeyName;
            }
        }
        else {
            if(introspectedTable.getPrimaryKeyColumns().size() == 1) {

                IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get( 0 );
                entityKeyName = introspectedColumn.getFullyQualifiedJavaType().getShortName();
            }
            else {
                entityKeyName = "Integer";
            }
        }

        interfaze.addSuperInterface( new FullyQualifiedJavaType("JpaRepository<" + entityName + ","+ entityKeyName +">"));

        // 生成方法
        // e.g. List<PointEntity> findByDeviceIdIn(Collection<Integer> deviceIds);
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        for(IntrospectedColumn column : allColumns) {
            String methodName = "findBy" + column.getActualColumnName();
            Method method = new Method( methodName );
            method.addParameter( new Parameter( column.getFullyQualifiedJavaType(), column.getActualColumnName()) );
            interfaze.addMethod( method );
        }

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
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModel;" );
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModelProperty;" );

        topLevelClass.addJavaDocLine( "" );
        topLevelClass.addAnnotation( "@Entity" );
        topLevelClass.addAnnotation( "@ApiModel" );
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
        field.addAnnotation( "@ApiModelProperty(value=\"" + introspectedColumn.getRemarks() + "\")" );

        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        // 当某个表有多个主键时候 会生成一个 TabUserKey的一个专门表示主键的类

        topLevelClass.addJavaDocLine( "import javax.persistence.*;" );
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModel;" );
        topLevelClass.addJavaDocLine( "import io.swagger.annotations.ApiModelProperty;" );

        return true;
    }
}
