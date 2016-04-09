package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

    private static final String PATH = "/Users/keith/Courses/sdp/2016/SDP2016/SML/src/";
    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private String line = "";
    private Labels labels; // The labels of the program being translated
    private ArrayList<Instruction> program; // The program to be created
    private String fileName; // source file of SML code

    public Translator(String fileName) {
        this.fileName = PATH + fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"
    public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

        try (Scanner sc = new Scanner(new File(fileName))) {
            // Scanner attached to the file chosen by the user
            labels = lab;
            labels.reset();
            program = prog;
            program.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next line into line
            while (line != null) {
                // Store the label in label
                String label = scan();

                if (label.length() > 0) {
                    Instruction ins = getInstruction(label);
                    if (ins != null) {
                        labels.addLabel(label);
                        program.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.out.println("File: IO error " + ioE.getMessage());
            System.exit(-1);
            return false;
        }
        return true;
    }

    // line should consist of an MML instruction, with its label already
    // removed. Translate line into an instruction with label label
    // and return the instruction
    public Instruction getInstruction(String label) {
        int s1 = -1; // Possible operands of the instruction
        int s2 = -1;
        int r;
        Class myInstruction = Instruction.class;
        Class test = myInstruction.asSubclass(myInstruction);
        Package smlPackage = myInstruction.getPackage();
        System.out.println("Package Name is: " + smlPackage);
        smlPackage.getClass();


        //Class superclass = myInstruction.getSuperclass();

        //class myRegister = Registers.class;

        //Set<Class<? extends Instruction>> allClasses =
                //new ("sml").getSubTypesOf(Instruction.class);


        if (line.equals(""))
            return null;

        String ins = scan();
        String output = ins.substring(0, 1).toUpperCase() + ins.substring(1);
        /*if (myInstruction.getName().toLowerCase().startsWith(ins)) {
            Class x =
        }*/
        try {
            Constructor[] ctors = Class.forName("sml."+output+"Instruction").getConstructors();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String remains = line;
                r = scanInt();
                String string1 = scan();

        /*Test conditions for instantiating Instruction classes with reflexion
        Instead of the switch, the ins string is used as the prefix to an Instruction subclass,
        which is called with Class.forName. The arguments are determined by parseint attempts.
        If none of the conditions are met, the default (String.class, String.class) arguments are used.
         */
        if (string1.length() != 0) {



                    try {
                        s1 = Integer.parseInt(string1);
                    } catch (NumberFormatException e) {
                        try {
                            return (Instruction) Class.forName("sml." + output + "Instruction").getConstructor(String.class, int.class, String.class).newInstance(label, r, string1);
                        } catch (InstantiationException x) {
                            e.printStackTrace();
                        } catch (IllegalAccessException y) {
                            e.printStackTrace();
                        } catch (InvocationTargetException z) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException w) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException v) {
                            e.printStackTrace();
                        }
                    }
        }
                if (s1 != Integer.MAX_VALUE && s1 > -1) {
                    String string2 = scan();
                    if (string2.length() != 0) {
                        try {
                            s2 = Integer.parseInt(string2);
                        } catch (NumberFormatException e) {
                            try {
                                return (Instruction) Class.forName("sml." + output + "Instruction").getConstructor(String.class, int.class, String.class).newInstance(label, r, string2);
                            } catch (InstantiationException x) {
                                e.printStackTrace();
                            } catch (IllegalAccessException y) {
                                e.printStackTrace();
                            } catch (InvocationTargetException z) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException w) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException v) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (s2 != Integer.MAX_VALUE && s2 > -1) {
                        try {
                            return (Instruction) Class.forName("sml." + output + "Instruction").getConstructor(String.class, int.class, int.class, int.class).newInstance(label, r, s1, s2);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            return (Instruction) Class.forName("sml." + output + "Instruction").getConstructor(String.class, int.class, int.class).newInstance(label, r, s1);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        return (Instruction) Class.forName("sml." + output + "Instruction").getConstructor(String.class, String.class).newInstance(label, remains);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }


                //return new AddInstruction(label, r, s1, s2);
            /*case "lin":
                r = scanInt();
                s1 = scanInt();
                return new LinInstruction(label, r, s1);
            case "mul":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new MulInstruction(label, r, s1, s2);
            case "sub":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new SubInstruction(label, r, s1, s2);
            case "div":
                r = scanInt();
                s1 = scanInt();
                s2 = scanInt();
                return new DivInstruction(label, r, s1, s2);
            case "out":
                s1 = scanInt();
                return new OutInstruction(label, s1);
            case "bnz":
                s1 = scanInt();
                ins = scan();
                return new BnzInstruction(label, s1, ins);
        }*/

        // You will have to write code here for the other instructions.

        return null;
    }

    /*
     * Return the first word of line and remove it from line. If there is no
     * word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0)
            return "";

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is
    // any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}