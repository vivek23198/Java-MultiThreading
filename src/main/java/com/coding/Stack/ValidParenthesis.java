package com.coding.Stack;

import java.util.Stack;

public class ValidParenthesis {

    private static boolean isValidParentheses(String str) {
        char[] ch = str.toCharArray();
        Stack<Character> st = new Stack<>();

        for(int i=0; i<ch.length; i++){
            if(ch[i] == '(' || ch[i] == '{' || ch[i] == '[') {
                st.push(ch[i]);
            } else if(ch[i] == ')' || ch[i] == '}' || ch[i] == ']') {
                if(st.isEmpty()) return false;
                char poppedElem = st.pop();

                if((ch[i] == '}' && poppedElem != '{') || (ch[i] == ']' && poppedElem != '[') ||
                        (ch[i] == ')' && poppedElem != '(')) {
                    return false;
                }
            }
        }

        return st.isEmpty();
    }

    public static void main(String[] args) {
        String str = "()[]{}";
        boolean isValidParenthesis = isValidParentheses(str);

        if(isValidParenthesis){
            System.out.println("Parenthesis are Valid");
        } else {
            System.out.println("Parenthesis are not Valid");
        }
    }
}
