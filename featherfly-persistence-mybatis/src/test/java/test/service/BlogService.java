package test.service;

/**
 * <p>
 * Snippet
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import test.mapper.BlogMapper;
import test.model.Blog;

@Service
public class BlogService {

    @Resource
    private BlogMapper blogMapper;

    public void deleteBlog(int id) {
        blogMapper.deleteBlog(id);
    }

    public Blog find(int id) {
        return blogMapper.selectBlog(id);
    }

    public List<Blog> find() {
        return blogMapper.selectAll();
    }

    public void insertBlog(Blog blog) {
        blogMapper.insertBlog(blog);
    }

    public void updateBlog(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    public BlogMapper getBlogMapper() {
           return blogMapper;
        }

    @Resource
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

}
