package com.ys;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.rules.ConditionalModelRules;

import java.util.List;

/**
 * Created by DELL on 2017/12/13.
 */
public class PluginAdapterForHibernate extends PluginAdapter {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized( introspectedTable );

        introspectedTable.setRules( new MyRules(introspectedTable) );

    }

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
//        interfaze.addImportedType( new FullyQualifiedJavaType("org.springframework.data.domain.Sort" ));
        interfaze.addImportedType( new FullyQualifiedJavaType("java.util.List" ));
        interfaze.addImportedType( new FullyQualifiedJavaType("java.util.Collection" ));

        interfaze.addAnnotation( "@Repository" );

        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String entityKeyName = introspectedTable.getTableConfiguration().getDomainObjectName() + "Key";

        if(introspectedTable.getPrimaryKeyColumns().size() > 1) {
            // Has Key Class
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

        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        boolean hasDateColumn = false;
        // 生成findByXXX方法
        for(IntrospectedColumn column : allColumns) {
            String property = column.getJavaProperty();

            if(!property.equals( "id" )) {
                // 不生成findById,因为最新的hibernate本身带这个函数，会导致编译失败
                String methodName = "findBy" + property.substring( 0,1 ).toUpperCase() + property.substring( 1 );
                Method method = new Method( methodName );
                method.addParameter( new Parameter( column.getFullyQualifiedJavaType(), column.getJavaProperty()) );
//            method.addParameter( new Parameter( new FullyQualifiedJavaType("Sort"), column.getJavaProperty() + "Sort") );

                method.addJavaDocLine( "/**" );
                method.addJavaDocLine( " * 数据库字段" + column.getActualColumnName() + ",属性名称" + property);
                method.addJavaDocLine( " * @param " +property + " " + column.getRemarks() );
                method.addJavaDocLine( " */" );

                method.setReturnType( new FullyQualifiedJavaType( "List<" +entityName +">" ) );

                interfaze.addMethod( method );

                if(column.getFullyQualifiedJavaType().getShortName().equals("Date"  )) {
                    hasDateColumn = true;
                }
            }
        }

        // 生成findByXXXIn方法
        for(IntrospectedColumn column : allColumns) {
            String property = column.getJavaProperty();

            String methodName = "findBy" + property.substring( 0,1 ).toUpperCase() + property.substring( 1 ) + "In";
            Method method = new Method( methodName );
            method.addParameter( new Parameter( new FullyQualifiedJavaType( "Collection<" +column.getFullyQualifiedJavaType().getShortName() +">" ), column.getJavaProperty() + "s") );

            method.addJavaDocLine( "/**" );
            method.addJavaDocLine( " * 数据库字段" + column.getActualColumnName() + ",属性名称" + property);
            method.addJavaDocLine( " * @param " +property + "s " + column.getRemarks() );
            method.addJavaDocLine( " */" );

            method.setReturnType( new FullyQualifiedJavaType( "List<" +entityName +">" ) );

            interfaze.addMethod( method );

            if(column.getFullyQualifiedJavaType().getShortName().equals("Date"  )) {
                hasDateColumn = true;
            }
        }

        if(hasDateColumn) {
            interfaze.addImportedType( new FullyQualifiedJavaType("java.util.Date" ));
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
        topLevelClass.addJavaDocLine( "import org.hibernate.annotations.GenericGenerator;" );
        topLevelClass.addJavaDocLine( "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;" );

        topLevelClass.addJavaDocLine( "" );
        topLevelClass.addAnnotation( "@Entity" );
        topLevelClass.addAnnotation( "@ApiModel" );
        topLevelClass.addAnnotation( "@JsonIgnoreProperties(value = { \"hibernateLazyInitializer\"})" );
        topLevelClass.addAnnotation( "@Table(name=\"" +introspectedTable.getTableConfiguration().getTableName() + "\")" );

        if(introspectedTable.getPrimaryKeyColumns().size() > 1) {
            topLevelClass.addAnnotation( "@IdClass(" + introspectedTable.getTableConfiguration().getDomainObjectName() + "Key.class)" );
        }

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

        if(primaryKeyColumns.size() == 0) {
            // 一个表，没设置主键，默认认为名字为id的字段为主键
            if("id".equals( introspectedColumn.getActualColumnName()) ) {
                field.addAnnotation( "@Id" );
            }
        }

        if(introspectedColumn.isAutoIncrement()) {
            field.addAnnotation( "@GeneratedValue(strategy = GenerationType.IDENTITY)" );
        }
        else {
            // 如果不是递增Id，且备注是uuid的话，则使用uuid自动生成
            if(introspectedColumn.getRemarks().equals("uuid")) {
                field.addAnnotation( "@GenericGenerator(name=\"idGenerator\", strategy=\"org.hibernate.id.UUIDGenerator\")" );
                field.addAnnotation( "@GeneratedValue(generator=\"idGenerator\")" );
            }
        }

        String columnName = MybatisGeneratorUtil.getColumnOverrideColumnName( introspectedTable, introspectedColumn.getActualColumnName());
        if(columnName == null) {
            columnName = introspectedColumn.getActualColumnName();
        }

        field.addAnnotation( "@Column(name=\"" + columnName + "\")" );
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
        topLevelClass.addAnnotation( "@MappedSuperclass" );

//        if(introspectedTable.getNonPrimaryKeyColumns().size() == 0) {
//            topLevelClass.addAnnotation( "@Entity" );
//            topLevelClass.addAnnotation( "@ApiModel" );
//            topLevelClass.addAnnotation( "@Table(name=\"" +introspectedTable.getTableConfiguration().getTableName() + "\")" );
//            topLevelClass.addAnnotation( "@IdClass(" + introspectedTable.getTableConfiguration().getDomainObjectName() + "Key.class)" );
//        }

        return true;
    }

    class MyRules extends ConditionalModelRules {

        /**
         * Instantiates a new conditional model rules.
         *
         * @param introspectedTable the introspected table
         */
        public MyRules(IntrospectedTable introspectedTable) {
            super( introspectedTable );
        }

        @Override
        public boolean generateBaseRecordClass() {
//            return introspectedTable.hasBaseColumns()
//                    || introspectedTable.getPrimaryKeyColumns().size() == 1
//                    || blobsAreInBaseRecord();

            return true;
        }
    }
}
