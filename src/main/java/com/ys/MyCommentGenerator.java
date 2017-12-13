package com.ys;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * Created by DELL on 2017/12/12.
 */
public class MyCommentGenerator extends DefaultCommentGenerator {
    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat( "yyyy-MM-dd" )).format( new Date() );
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (compilationUnit.isJavaInterface()) {
            // Mapper文件
            compilationUnit.addFileCommentLine( "// 这是Mapper接口文件" );
        } else {
            compilationUnit.addFileCommentLine( "// 这是Model类文件或Example类文件" );
        }
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll( properties );
        suppressDate = isTrue( properties.getProperty( PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE ) );
        suppressAllComments = isTrue( properties.getProperty( PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS ) );
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     *
     * @param javaElement the java element
     */
    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine( " *" );
        StringBuilder sb = new StringBuilder();
        sb.append( " * " );
        sb.append( MergeConstants.NEW_ELEMENT_TAG );
        if (markAsDoNotDelete) {
            sb.append( " do_not_delete_during_merge" );
        }
        String s = getDateString();
        if (s != null) {
            sb.append( ' ' );
            sb.append( s );
        }
        javaElement.addJavaDocLine( sb.toString() );
    }

    /**
     * 这里不返回日期，防止每次生成代码，即使内容没变，由于日期变化导致需要重新提交svn
     *
     * @return null
     */
    @Override
    protected String getDateString() {
        suppressDate = true;
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     * Example类里的内部类会调用此处生成类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine( "/**" );
        innerClass.addJavaDocLine( " * generated by addClassComment" );
        sb.append( " * " );
        sb.append( introspectedTable.getFullyQualifiedTable() );
        sb.append( " " );
        sb.append( getDateString() );
        innerClass.addJavaDocLine( sb.toString() );
        innerClass.addJavaDocLine( " */" );
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine( "/**" );
        innerEnum.addJavaDocLine( " * generated by addEnumComment" );
        //      addJavadocTag(innerEnum, false);
        sb.append( " * " );
        sb.append( introspectedTable.getFullyQualifiedTable() );
        innerEnum.addJavaDocLine( sb.toString() );
        innerEnum.addJavaDocLine( " */" );
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine( "/**" );
        field.addJavaDocLine( " * generated by addFieldComment" );
        sb.append( " * " );
        sb.append( introspectedColumn.getRemarks() );
        field.addJavaDocLine( sb.toString() );

        //      addJavadocTag(field, false);

        field.addJavaDocLine( " */" );
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine( "/**" );
        field.addJavaDocLine( " * generated by addFieldComment" );
        sb.append( " * " );
        sb.append( introspectedTable.getFullyQualifiedTable() );
        field.addJavaDocLine( sb.toString() );
        field.addJavaDocLine( " */" );
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        //      method.addJavaDocLine("/**");
        //      addJavadocTag(method, false);
        //      method.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine( "/**" );
        method.addJavaDocLine( " * generated by addGetterComment" );

        StringBuilder sb = new StringBuilder();
        sb.append( " * " );
        sb.append( introspectedColumn.getRemarks() );
        method.addJavaDocLine( sb.toString() );

        sb.setLength( 0 );
        sb.append( " * @return " );
        sb.append( introspectedColumn.getActualColumnName() );
        sb.append( " " );
        sb.append( introspectedColumn.getRemarks() );
        method.addJavaDocLine( sb.toString() );

        //      addJavadocTag(method, false);

        method.addJavaDocLine( " */" );
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }


        method.addJavaDocLine( "/**" );
        method.addJavaDocLine( " * generated by addSetterComment" );
        StringBuilder sb = new StringBuilder();
        sb.append( " * " );
        sb.append( introspectedColumn.getRemarks() );
        method.addJavaDocLine( sb.toString() );

        Parameter parm = method.getParameters().get( 0 );
        sb.setLength( 0 );
        sb.append( " * @param " );
        sb.append( parm.getName() );
        sb.append( " " );
        sb.append( introspectedColumn.getRemarks() );
        method.addJavaDocLine( sb.toString() );

        //      addJavadocTag(method, false);

        method.addJavaDocLine( " */" );
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine( "/**" );
        innerClass.addJavaDocLine( " * generated by addClassComment" );
        sb.append( " * " );
        sb.append( introspectedTable.getFullyQualifiedTable() );
        innerClass.addJavaDocLine( sb.toString() );

        sb.setLength( 0 );
        sb.append( " * @author " );
        sb.append( systemPro.getProperty( "user.name" ) );
        sb.append( " " );
        sb.append( currentDateStr );

        //      addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine( " */" );
    }
}
