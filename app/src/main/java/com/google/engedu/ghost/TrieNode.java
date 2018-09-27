package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<Character, TrieNode> child = children;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            TrieNode trieNode;
            if (child.containsKey(ch)) {
                trieNode = child.get(ch);
            } else {
                trieNode = new TrieNode();
                child.put(ch, trieNode);
            }
            child = trieNode.children;
            //set leaf node
            if (i == s.length() - 1) {
                trieNode.isWord = true;
            }
        }
    }

    public boolean isWord(String s) {
        TrieNode temp = searchWord(s);
        return temp != null && temp.isWord;
    }

    public TrieNode searchWord(String s) {
        HashMap<Character, TrieNode> temp_child = children;
        TrieNode trieNode = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (temp_child.containsKey(c)) {
                trieNode = temp_child.get(c);
                temp_child = trieNode.children;
            } else {
                return null;
            }
        }
        return trieNode;
    }

    public String getAnyWordStartingWith(String s) {
        String result = s;
        if (s.equals("")) {
            HashMap<Character, TrieNode> temp_child = this.children;
            TrieNode trieNode = new TrieNode();
            trieNode.children = temp_child;

            while(!trieNode.isWord){
                temp_child = trieNode.children;
                int size = temp_child.keySet().size();
                int random = new Random().nextInt(size);
                char next = (char) temp_child.keySet().toArray()[random];
                result += next;
                trieNode = temp_child.get(next);
            }
        }
        else {
            TrieNode trieNode = searchWord(s);

            HashMap<Character, TrieNode> temp_child;
            if (trieNode == null) {
                return null;
            } else {
                while (!trieNode.isWord) {
                    temp_child = trieNode.children;

                    int size = temp_child.keySet().size();
                    int random = new Random().nextInt(size);
                    //keySet.toArray will convert set of keys into array
                    char next = (char) temp_child.keySet().toArray()[random];
                    result += next;
                    trieNode = temp_child.get(next);
                }
            }
        }
        return result;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
