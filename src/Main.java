import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import javafx.util.Pair;

import java.util.*;

public class Main {
    static ArrayList<Pair<Character, Character>> lang = new ArrayList<>();
    static ArrayList<String> alphabet = new ArrayList<>();
    static String positions = "abcdefghigklmnopqrstuvwxyz";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String name;
        List<String> names = new ArrayList<>();
        for(int i = 0; i < n; i++){
            name = scanner.nextLine();
            names.add(name);
        }
        if(makeAlphabet(names, 0)) {
           // alphabet.forEach(System.out::println);
            makeStructure();
            //lang.forEach(System.out::println);
            makePositions();
        }
        else {
            System.out.println("Impossible");
        }
    }

    private static boolean makeAlphabet(List<String> names, int index){
        List<String> temp = new ArrayList<>();
        String letters = "";
        int k = -1;
        boolean flag = true;
        for(int i = 0; i < names.size(); i++){
            if(names.get(i).length() > index){
                letters += names.get(i).charAt(index);
                temp.add(names.get(i));
                k = i + 1;
                break;
            }
        }
        if(k == -1){
            k = names.size();
        }
        for(int i = k; i < names.size(); i++){
            if(names.get(i).length() > index){
                if(names.get(i).charAt(index) == names.get(i - 1).charAt(index)){
                    temp.add(names.get(i));
                }
                else{
                    flag = false;
                    letters += names.get(i).charAt(index);
                    if(temp.size() != 1)
                        if(!makeAlphabet(temp, index + 1))
                            return false;
                    //приколы с добавлением
                    temp.clear();
                    temp.add(names.get(i));
                }
            }
            else{
                return false;
            }
        }
        if(flag && temp.size() > 1)
            if(!makeAlphabet(temp, index + 1))
                return false;
        if(letters.length() > 1){
            if(!alphabet.contains(letters))
                alphabet.add(letters);
        }
        return true;
    }

    private static void makeStructure(){
        Pair<Character, Character> pair;
        for (String s : alphabet) {
            for (int j = 0; j < s.length() - 1; j++) {
                if (!checkAlphabet(s.charAt(j), s.charAt(j + 1))) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                pair = new Pair<>(s.charAt(j), s.charAt(j + 1));
                // System.out.println(pair);
                if (!lang.contains(pair)) {
                    lang.add(pair);
                }
            }
        }
    }

    private static void makePositions(){
        for (Pair<Character, Character> pair : lang) {
            if (positions.indexOf(pair.getKey()) > positions.indexOf((pair.getValue()))) {
                positions = positions.replace(pair.getKey(), pair.getValue());
                positions = positions.replaceFirst(pair.getValue().toString(), pair.getKey().toString());
                System.out.println(positions);
            }
        }
        if(checkPositions())
            System.out.println(positions);
        else{
            makePositions();
        }
    }

    private static boolean checkAlphabet(char a, char b){
        for (String s : alphabet) {
            if (s.indexOf(a) > -1 && s.indexOf(b) > -1) {
                if (s.indexOf(a) > s.indexOf(b)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkPositions(){
        for (Pair<Character, Character> pair : lang) {
            if (positions.indexOf(pair.getKey()) > positions.indexOf((pair.getValue()))) {
                return false;
            }
        }
        return true;
    }
}
