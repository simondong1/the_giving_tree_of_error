import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    private static String sExpression(HashMap<Character, char[]> map, char root, ArrayList<Character> chars) throws InterruptedException{
        if (chars.contains(root)) {
            throw new InterruptedException("cycles!");
        }
        String sexpression = "";
        if (root == ' '){
            return sexpression;
        }
        chars.add(root);
        char[] val = map.get(root);
        if (val[1] > val[2]){
            sexpression = "(" + root + sExpression(map,val[2],chars) + sExpression(map,val[1],chars) + ")";
        }
        else{
            sexpression = "(" + root + sExpression(map,val[1],chars) + sExpression(map,val[2],chars) + ")";
        }
        return sexpression;
    }
    
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        HashMap<Character, char[]> map = new HashMap<>();
        boolean E2 = false;
        boolean E3 = false;
        boolean E4 = false;
        boolean E5 = false;
        
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String input = scanner.next();
            
            //good format
            if (input.matches("[(][A-Z][,][A-Z][)]")){
                char[] cArray = input.toCharArray();
                char parent = cArray[1];
                char child = cArray[3];
                
                //both not in map
                if (!map.containsKey(parent) && !map.containsKey(child)){
                    char[] parentArray = {' ', child, ' '};
                    char[] childArray = {parent, ' ', ' '};
                    map.put(child,childArray);
                    map.put(parent,parentArray);
                }
                else if (!map.containsKey(parent)){
                    char[] parentArray = {' ', child, ' '};
                    map.put(parent,parentArray);
                    
                    char[] childArray = map.get(child);
                    if (childArray[0] == ' '){
                        childArray[0] = parent;
                    }
                    //more than 1 parent?
                    else{
                        
                    }
                    map.put(child,childArray);
                }
                else if (!map.containsKey(child)){
                    char[] childArray = {parent, ' ', ' '};
                    map.put(child,childArray);
                    
                    char[] parentArray = map.get(parent);
                    //nothing in it yet
                    if (parentArray[1] == ' ' && parentArray[2] == ' '){
                        parentArray[1] = child;
                    }
                    else if (parentArray[2] == ' '){
                        parentArray[2] = child;
                    }
                    //more than 2 children
                    else{
                        E3 = true;
                    }
                    map.put(parent,parentArray);
                }
                else if (map.containsKey(parent) && map.containsKey(child)){
                    char[] parentArray = map.get(parent);
                    //check if child exist
                    if (parentArray[1] == child || parentArray[2] == child){
                        E2 = true;
                    }
                    //nothing in it yet
                    else if (parentArray[1] == ' ' && parentArray[2] == ' '){
                        parentArray[1] = child;
                    }
                    else if (parentArray[2] == ' '){
                        parentArray[2] = child;
                    }
                    //more than 2 children
                    else{
                        E3 = true;
                    }
                    char[] childArray = map.get(child);
                    
                    if (childArray[0] == parent){
                        System.out.println(parent);
                        E2 = true;
                    }
                    //nothing in it yet
                    else if (childArray[0] == ' '){
                        childArray[0] = parent;
                    }
                    //more than 1 parent
                    else{

                    }
                }
            }
            else{
                System.out.println("E1");
                return;
            }
        }
        
        if (E2) {
            System.out.println("E2");
            return;
        }
        else if (E3) {
            System.out.println("E3");
            return;
        }
        
        //check for multiple roots
        int root = 0;
        char r = ' ';
        
        for (Map.Entry<Character, char[]> entry : map.entrySet()) {
            char[] c = entry.getValue();
            
            if (c[0] == ' ') {
                root = root +1;
                r = entry.getKey();
            }
        }

        if (root > 1){
            System.out.println("E4");
            return;
        }
        else if (root < 1){
            System.out.println("E5");
            return;
        }
        
        //good tree!, do s expression
        ArrayList<Character> chars = new ArrayList<Character>();
        try{
            System.out.println(sExpression(map,r,chars));
        } catch (InterruptedException e){
            //cycles
            System.out.println("E5");
        }
        
        scanner.close();

    }
}
