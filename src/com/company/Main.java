package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static TrieNode root;

    static void insert(String word, int occurence) {
        int letter;
        TrieNode current = root;

        for (int i=0 ; i<word.length() ; i++) {
            letter = word.charAt(i) - 97;
            if (current.children[letter] == null)
                current.children[letter] = new TrieNode();

            current = current.children[letter];
        }
        current.isEndOfWord = true;
        current.occurences.add(occurence);
    }

    static ArrayList<Integer> search(String word) {
        int letter;
        TrieNode current = root;

        for (int i=0 ; i<word.length() ; i++){
            letter = word.charAt(i) - 97;
            if (current.children[letter] == null)
                return null;
            current = current.children[letter];
        }

        if(current != null && current.isEndOfWord)
            return current.occurences;
        return null;
    }


    public static void main(String args[]) throws Exception {
        Scanner fileScanner = new Scanner(new File("test.txt"));
        String text = fileScanner.nextLine();
        text = text.replaceAll("[^a-zA-Z]+", " ");
        text = text.toLowerCase();
        fileScanner.close();
        String keys[] = text.split(" ");
        root = new TrieNode();
        int currentPlace = 1;

        for (int i=0 ; i<keys.length ; i++) {
            insert(keys[i], currentPlace);
            currentPlace += keys[i].length();
        }

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("Enter Word To Search (Enter -1 to Exit) : ");
            String word = scanner.next();
            word = word.toLowerCase();
            if(word.equals("-1"))
                break;
            System.out.println(word + " : " + search(word));
        }

    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord;
    ArrayList<Integer> occurences;
    TrieNode(){
        isEndOfWord = false;
        occurences = new ArrayList<>();
        for (int i=0 ; i<26 ; i++)
            children[i] = null;
    }
}

class State{
    map: Cell[][];
    minimumFood: int;
    minimumMoney: int;
    x: int;
    y: int;
    food: int;
    money: int;
    haveKey: boolean;
}

class Cell{
    type: CellType;
    isFirstTime: boolean; //for Treasue & Bridge
    foodGift: int; //for Treasure
    moneyGift: int; //for Treasure
    power: int; //for Thief & Animal
    containsKey: boolean; //for Key
    isCastle: boolean; //for Castle
}

enum CellType{ Normal, Key, Castle, Thief, Animal, Treasure, Swamp, Bridge}

successor(x, y, money, food, haveKey): return a set of action-state pairs or null{
    if(money < minimumMoney or food < minimumFood)
        return null;
    goalTest(x, y, money, food, haveKey);
    pairs = [];
    if(map[x][y].type == Bridge and map[x][y].isFirstTime)
        map[x][y].isFirstTime = false;
    if(map[x][y].type == Key and map[x][y].isFirstTime){
        map[x][y].isFirstTime = false;
        pairs.append(takeKey(),(x,y,money,food,1));
    }
    if(map[x][y].type == Treasure and map[x][y].isFirstTime){
        map[x][y].isFirstTime = false;
        pairs.append(get(M, map[x][y].moneyGift),(x,y,money+map[x][y].moneyGift,food,haveKey));
        pairs.append(get(F, map[x][y].foodGift),(x,y,money,food+map[x][y].foodGift,haveKey));
    }
    if(map[x][y].type == Animal)
        pairs.append(lose(F, map[x][y].power),(x,y,money,food-map[x][y].power,haveKey));
    if(map[x][y].type == Thief)
        pairs.append(lose(M, map[x][y].power),(x,y,money-map[x][y].power,food,haveKey));
    if(map[x-1][y] is valid)
        pairs.append(go(U),(x-1,y,money,food,haveKey));
    if(map[x+1][y] is valid)
        pairs.append(go(D),(x+1,y,money,food,haveKey));
    if(map[x][y+1] is valid)
        pairs.append(go(R),(x,y+1,money,food,haveKey));
    if(map[x][y-1] is valid)
        pairs.append(go(L),(x,y-1,money,food,haveKey));

    return pairs;
}

goalTest(x, y, money, food, haveKey): return boolean{
    return map[x][y].type == Castle and haveKey ;
}

go(direction);  //direction: U,D,R,L (up, down, right, left)
get(supply, amount); //supply: M,F (money, food) | amount: integer
lose(supply, amount); //supply: M,F (money, food) | amount: integer
takeKey();

isValid(i, j){
    if(map[i][j].type == Bridge and !map[i][j].isFirstTime)
        return false;
    if(map[i][j].type == Swamp)
        return false;
    if(i<0 or j<0 or i>mapHeight or j>mapWidth)
        return false;
    return true;
}

    if(isValid(x-1, y))
        pairs.append(go(U),(x-1,y,money,food,haveKey));
    if(isValid(x+1, y))
        pairs.append(go(D),(x+1,y,money,food,haveKey));
    if(isValid(x, y+1))
        pairs.append(go(R),(x,y+1,money,food,haveKey));
    if(isValid(x, y-1))
        pairs.append(go(L),(x,y-1,money,food,haveKey));



