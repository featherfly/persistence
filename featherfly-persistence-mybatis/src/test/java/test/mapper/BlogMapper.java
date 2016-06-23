package test.mapper;

/**
 * <p>
 * Snippet
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import test.model.Blog;

@Mapper
public interface BlogMapper {

    public Blog selectBlog(int id);

    public void insertBlog(Blog blog);

    public void updateBlog(Blog blog);

    public void deleteBlog(int id);

    public List<Blog> selectAll();

}
