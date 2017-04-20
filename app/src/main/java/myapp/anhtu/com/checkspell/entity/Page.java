package myapp.anhtu.com.checkspell.entity;

import java.io.Serializable;

/**
 * Created by anhtu on 3/25/2017.
 */

public class Page implements Serializable {
    private String content;
    private Integer pageNumber;

    public Page(String content, Integer pageNumber) {

        this.content = content;
        this.pageNumber = pageNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
