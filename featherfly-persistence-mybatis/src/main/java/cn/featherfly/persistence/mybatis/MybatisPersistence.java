
package cn.featherfly.persistence.mybatis;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * <p>
 * MybatisPersistence
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */
public class MybatisPersistence {

    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 设置sqlSessionTemplate
     * @param sqlSessionTemplate sqlSessionTemplate
     */
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    public static void main(String[] args) {
        SqlSessionTemplate sqlSessionTemplate;        
    }
}
