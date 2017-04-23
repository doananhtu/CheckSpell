package myapp.anhtu.com.checkspell.entity;

/**
 * Created by anhtu on 4/23/2017.
 */

public class Report {
    private String content;
    private String description;

    public Report(String content, String description) {
        this.content = content;
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
