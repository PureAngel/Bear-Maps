import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/2.
 */
public class TrieNode {
    private Long id;
    private boolean isEnd;
    private HashMap<Character, TrieNode> nodeLetters;

    public TrieNode() {
        id = null;
        isEnd = false;
        nodeLetters = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public HashMap<Character, TrieNode> getNodeLetters() {
        return nodeLetters;
    }

    public void setNodeLetters(HashMap<Character, TrieNode> nodeLetters) {
        this.nodeLetters = nodeLetters;
    }

}
