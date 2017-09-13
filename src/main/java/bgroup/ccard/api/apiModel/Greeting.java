package bgroup.ccard.api.apiModel;

/**
 * Created by VSB on 11.09.2017.
 * ccardApi
 */
public class Greeting {
    private  long id;
    private  String content;
    private String testString;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
        this.testString = "test1";
    }

    public Greeting() {
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
