
package test.model;

/**
 * <p>
 * Blog
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */
public class Blog {
    private Integer id;
    
    private String title;
    
    private String content;
    
    private Integer owner;

    /**
     * 返回id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 返回title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 返回content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 返回owner
     * @return owner
     */
    public Integer getOwner() {
        return owner;
    }

    /**
     * 设置owner
     * @param owner owner
     */
    public void setOwner(Integer owner) {
        this.owner = owner;
    }
    
    
}
