
package test;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import test.model.Blog;
import test.service.BlogService;
import cn.featherfly.common.lang.RandomUtils;

/**
 * <p>
 * Test
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */
//@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app.xml" })
//@ActiveProfiles("dev")
@TransactionConfiguration(defaultRollback = false)
public class BlogTest extends AbstractTransactionalTestNGSpringContextTests{
    
    @Resource
    BlogService blogService;
    
    @Test
    public void testAdd() {
        Blog blog = new Blog();
        blog.setTitle("title_" + RandomUtils.getRandomString(10));
        blog.setContent("content_" + RandomUtils.getRandomString(10));
        blog.setOwner(RandomUtils.getRandomInt(10));
        blogService.insertBlog(blog);
    }
    @Test
    public void testSelect() {
        Blog blog = blogService.find(1);
        System.out.println(blog.getTitle());
        System.out.println(blog.getContent());
    }
}
