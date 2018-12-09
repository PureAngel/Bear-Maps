import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2.
 */
public class Trie {
    HashMap<Long, String> idToname = new HashMap<>();

    private HashMap<Character, TrieNode> rootDic;
    private LinkedList<Long> autoCompletor;
    private HashMap<Long, String> locationId;


    public Trie() {
        this.idToname = new HashMap<>();
        this.rootDic = new HashMap<>();
        this.autoCompletor = new LinkedList<>();
        this.locationId = new HashMap<>();
    }

    public Trie(HashMap<Long, String> idToname, HashMap<Character, TrieNode> rootDic,
                LinkedList<Long> autoCompletor) {
        // this.idToname = idToname;
        this.idToname = new HashMap<>(idToname);
        this.rootDic = rootDic;
        this.autoCompletor = autoCompletor;
    }

    public HashMap<Long, String> getIdToname() {
        return idToname;
    }

    public void setIdToname(HashMap<Long, String> idToname) {
        this.idToname = idToname;
    }

    public HashMap<Character, TrieNode> getRootDic() {
        return rootDic;
    }

    public void setRootDic(HashMap<Character, TrieNode> rootDic) {
        this.rootDic = rootDic;
    }

    public LinkedList<Long> getAutoCompletor() {
        return autoCompletor;
    }

    public void setAutoCompletor(LinkedList<Long> autoCompletor) {
        this.autoCompletor = autoCompletor;
    }

    public HashMap<Long, String> getLocationId() {
        return locationId;
    }

    public void setLocationId(HashMap<Long, String> locationId) {
        this.locationId = locationId;
    }

    public void clear() {
        autoCompletor.clear();
    }

    public void addDefinition(String word, long id) {
        word = GraphDB.cleanString(word);
        TrieNode root = new TrieNode();
        root.setNodeLetters(rootDic);
        addDefinitionHelper(word, id, root);
    }

    private void addDefinitionHelper(String word, Long id, TrieNode p) {
        if (word.length() == 0) {
            p.setEnd(true);
            p.setId(id);
        } else {
            if (!p.getNodeLetters().containsKey(word.charAt(0))) {
                p.getNodeLetters().put(word.charAt(0), new TrieNode());
            }
            addDefinitionHelper(word.substring(1), id, p.getNodeLetters().get(word.charAt(0)));
        }
    }

    public void lookupDefinition(String word) {
        word = GraphDB.cleanString(word);
        TrieNode root = new TrieNode();
        root.setNodeLetters(rootDic);
        lookupDefinitionHelper(word, root);
    }

    private void lookupDefinitionHelper(String word, TrieNode pointer) {
        if (word.length() == 0) {
            autoCompleteCollection(pointer);
        } else {
            if (pointer.getNodeLetters().containsKey(word.charAt(0))) {
                lookupDefinitionHelper(word.substring(1),
                        pointer.getNodeLetters().get(word.charAt(0)));
            }
        }
    }

    private void autoCompleteCollection(TrieNode node) {
        if (node.isEnd()) {
            autoCompletor.add(node.getId());
        }
        for (Map.Entry<Character, TrieNode> entry : node.getNodeLetters().entrySet()) {
            autoCompleteCollection(entry.getValue());
        }
    }


}
