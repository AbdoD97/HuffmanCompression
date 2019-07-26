/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author Kondos
 */
public class JavaApplication3 {

    static Map<String, String> HuffCodes = new HashMap<>();
    static ArrayList<Node> list;
    static StringBuilder decodedString = new StringBuilder();
    static Map<Integer, Integer> map = new HashMap<>();
    static int comp_count = 0;

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter 1 for text compression 2 for text decompression: ");
        int n = reader.nextInt();

        if (n == 1) {
            long start = System.currentTimeMillis()/1000;
            reader = new Scanner(System.in);
            System.out.println("Enter exact path of file: ");
            String s = reader.nextLine();
            reader.close();
            File f = new File(s);

            String fileContent = readFile(s);

            //String a = new String();
            char b;
            int ascii;

            for (int i = 0; i < fileContent.length(); i++) {
                b = fileContent.charAt(i);
                ascii = (int) b;
                //value= map.get(ascii);
                if (map.containsKey(ascii)) {
                    map.put(ascii, map.get(ascii) + 1);
                } else {
                    map.put(ascii, 1);
                }
            }
            list = new ArrayList<Node>();

            for (Map.Entry me : map.entrySet()) {
                list.add(new Node((int) me.getKey(), (int) me.getValue()));
            }
            Collections.sort(list);

            //  list.remove(0);  //deleteeeeeeee
            ArrayList<Node> list2 = (ArrayList<Node>) list.clone();

            // System.out.println(list.toString());
            while (list2.size() != 1) {
                Node l = list2.get(0);
                Node r = list2.get(1);
                // System.out.println(l.toString() +"\n" +  r.toString());
                Node newNode = new Node(129, r.getFrequency() + l.getFrequency());
                // System.out.println(r.getFrequency() + l.getFrequency());
                newNode.setLeft(l);
                newNode.setRight(r);
                list2.add(newNode);
                list.add(newNode);
                Collections.sort(list2);
                //list.add(newNode);

                list2.remove(0);
                list2.remove(0);
                // System.out.println(list2.toString());
            }

            Collections.sort(list);
            Conquer(list.get(list.size() - 1), "");
            // System.out.println(HuffCodes.toString());
            // System.out.println(list.toString());
            compressText(f.getName(), fileContent);
            compressRate(f.getName());
            System.out.println("time of compression is " +((System.currentTimeMillis()/1000)-start) + " second");
        }
        if (n == 2) {
             long start = System.currentTimeMillis()/1000;
            reader = new Scanner(System.in);
            System.out.println("Enter exact path of file: ");
            String s = reader.nextLine();
            reader.close();
            File f = new File(s);

            decompressText(f.getName());
             System.out.println("time of compression is " +((System.currentTimeMillis()/1000)-start) + " second");

        }

    }

    static void Conquer(Node N, String a) {

        if (N.left != null) {
            Conquer(N.left, a.concat("0"));
        }
        if (N.right != null) {
            Conquer(N.right, a.concat("1"));
        }
        if (N.ascii != 129) {
            //System.out.println(Character.toString ((char) N.ascii) + " " + a);
            HuffCodes.put(Character.toString((char) N.ascii), a);
        }
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } finally {
            br.close();
        }
    }

    static void compressText(String fileName, String fileContent) throws FileNotFoundException, IOException {
        char c;
        // byte b1[] = new byte[256];

        StringBuilder sb1 = new StringBuilder(""); // add "\n"
        // sb1.append(fileName + "\n");
        for (int i = 0; i < fileContent.length(); i++) {
            c = fileContent.charAt(i);
            //   System.out.println(HuffCodes.get(Character.toString(c)));
            sb1.append(HuffCodes.get(Character.toString(c)));
        }

        FileOutputStream fileOut
                = new FileOutputStream("output");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(list);
        out.close();
        File file = new File("output");
        FileWriter fr = new FileWriter(file, true);
         String[] a = splitToNChar(sb1.toString(), 8);
        fr.write("\n" + fileName + "\n" + a[a.length-1].length()+ "\n");
       // System.out.println(a[a.length-1].length());
  //     System.out.println(sb1);
        // System.out.println(map.toString());
        // System.out.println(HuffCodes.toString());
        fr.close();
       
        fileOut
                = new FileOutputStream("output", true);
        for (int i = 0; i < a.length; i++) {
            byte b1[] = new byte[256];
            int val = Integer.parseInt(a[i], 2);

            byte b = (byte) val;
            fileOut.write(b);

        }
        fileOut.close();
        //   fileOut.write(header);

        // StringBuilder b = new StringBuilder ("");
        //1
//          System.out.println(new Character((char) Integer.parseInt(a[2], 2)));
        /*for (int i = 0; i < a.length; i++) {
            fr.write(new Character((char) Integer.parseInt(a[i], 2)));
            
        }

      
        // System.out.println(sb1.toString());
         fr.close();*/
 /*  byte[] b = sb1.toString().getBytes(Charset.forName("UTF-8"));
           fileOut     = new FileOutputStream("output" , true);
         fileOut.write(b);
            fileOut.close();
         */
        // System.out.println(list);
        //  out.writeObject("\n" +fileName + "\n" );
        // out.writeObject(sb1.toString());
        //  out.writeObject(sb1.toString());
        //int start =0 , end = 7;
        //  String a = "00000011111";
        //  System.out.println(sb1.toString());
        //   DataOutputStream.writeByte();
        //   System.out.println("start to write");
        /* for (int i = 0; i < sb1.length(); i = i + 7) {
            if ((i + 6) < sb1.length()) {
            
               // sb1.toString().substring(i,i+6); 
                //System.out.println(sb1.toString().substring(i,i+7));
             fr.write(  new Character((char)Integer.parseInt(sb1.toString().substring(i,i+7),2)).toString() ); //sb1.toString().substring(i,i+7)
                System.out.println("a");
            
            
            } else {
              //  System.out.println(sb1.toString().substring(i, sb1.length()));
              fr.write(   new Character((char)Integer.parseInt(sb1.toString().substring(i, sb1.length()),2)).toString()   ); //sb1.toString().substring(i, sb1.length())
            }
        }
fr.close();*/
 /* BufferedWriter output = new BufferedWriter(
                new FileWriter("output", true));
        output.write(sb1.toString());
        output.close();
         */
        //   System.out.println(sb1);
    }

    static void decompressText(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        list = (ArrayList<Node>) in.readObject();

        int singleCharInt;
        StringBuilder sb1 = new StringBuilder("");
                StringBuilder codeSize = new StringBuilder("");

        StringBuilder originalName = new StringBuilder("");
        int flag = 0;
      
        while ((singleCharInt = fileIn.read()) != -1) {

            //System.out.print(singleCharInt);
           
            if (flag == 1) {
                originalName.append((char) singleCharInt);
            } else if (flag == 2) {
                codeSize.append((char) singleCharInt);
            } else if (flag > 2) {

                
                    sb1.append(String.format("%8s", Integer.toBinaryString(singleCharInt)).replace(' ', '0'));
                
            }
             if ((char) singleCharInt == '\n') {
                flag++;
            }
        }
       // System.out.println(sb1);
       //System.out.println(codeSize);
        //        System.out.println(originalName);
        
int sizeLast= Integer.parseInt(codeSize.toString().replace("\n", ""));
if (sizeLast != 8)
      //  System.out.println(sizeLast);
{    String pendingFix =  sb1.substring(sb1.length()-8 ,sb1.length() );
        while(sizeLast !=8)
        {
           pendingFix = pendingFix.replaceFirst("0", ""); 
                sizeLast++;
                }
       // System.out.println("fixed"+ pendingFix);
        //sb1.replace(flag, flag, pendingFix)
sb1.replace(sb1.length()-8 ,sb1.length(), pendingFix);}
        in.close();
        fileIn.close();
       // System.out.println(sb1);
        
        Node temp = new Node(list.get(list.size() - 1).ascii, list.get(list.size() - 1).frequency);
        temp.setLeft(list.get(list.size() - 1).left);
        temp.setRight(list.get(list.size() - 1).right);
       // StringBuilder a = new StringBuilder("");       
        for (int i = 0; i < sb1.length(); i++) {
            if (temp.ascii == 129 && sb1.charAt(i) == '0') {
                temp = temp.left;
                //     System.out.print("l");
            } else if (temp.ascii == 129 && sb1.charAt(i) == '1') {
                temp = temp.right;
                //  System.out.print("r");
            } else {

                decodedString.append((char) temp.ascii);
                //   System.out.println((char)temp.ascii);
                temp = new Node(list.get(list.size() - 1).ascii, list.get(list.size() - 1).frequency);
                temp.setLeft(list.get(list.size() - 1).left);
                temp.setRight(list.get(list.size() - 1).right);
                i--;
                // i++;
            }
        }
        decodedString.append((char) temp.ascii);
        //System.out.println(decodedString);
                BufferedWriter writer = new BufferedWriter(new FileWriter(originalName.toString().replace("\n", ""), true));
            writer.write(decodedString.toString());
writer.close();
        

    }

    static void compressRate(String fileName) {
        File a = new File("output");
        File b = new File(fileName);

        System.out.println("compression rate is " + ((double) a.length() / b.length()) * 100 + "%");
    }

    static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

}
