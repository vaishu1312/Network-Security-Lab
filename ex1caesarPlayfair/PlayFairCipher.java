import java.util.Scanner;

public class PlayFairCipher {

    String keyword = new String();
    String plainText = new String();
    char key_mat[][] = new char[5][5];
    String cipherText = new String();

    public void validateKey() {
        boolean isKeyValid = false;
        if (keyword.contains("j")) {
            isKeyValid = true;
            keyword = keyword.replace('j', 'i');
        }
        // remove duplicates
        String str = new String();
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            if (str.indexOf(c) < 0)
                str += c;
        }
        if (isKeyValid || !keyword.equals(str)) {
            keyword = str;
            System.out.println("Modified key is ------ " + keyword);
        }
        // generating matrix entries as a string
        boolean flag = true;
        char current;
        char drop_char = 'j';
        for (int i = 0; i < 26; i++) {
            current = (char) (i + 97);
            if (current == drop_char)
                continue;
            for (int j = 0; j < keyword.length(); j++) {
                if (current == keyword.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                keyword = keyword + current;
            flag = true;
        }
        // System.out.println("key is " + keyword);
    }

    public void printKeyMatrix() {
        System.out.println("\nThe key matrix is\n");
        int idx = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                key_mat[i][j] = keyword.charAt(idx);
                System.out.print(key_mat[i][j] + " ");
                idx++;
            }
            System.out.println();
        }
    }

    public void modifyPlainText() {
        if (plainText.contains("j")) {
            plainText = plainText.replace('j', 'i');
            System.out.println("After replacing j with i in the plain text ---- " + plainText);
        }
        StringBuffer newString = new StringBuffer(plainText);

        for (int i = 0; i < newString.length() - 1; i++) {
            // check for reptition in a pair
            if (i % 2 == 0) {
                if (newString.charAt(i) == newString.charAt(i + 1)) {
                    if (newString.charAt(i) != 'z')
                        newString.insert(i + 1, "z");
                    else
                        newString.insert(i + 1, "q");
                }

            }
        }
        if (newString.length() % 2 != 0) {
            if (newString.charAt(newString.length() - 1) != 'z')
                newString.append("z");
            else
                newString.append("q");
        }

        if (!plainText.equals(newString.toString())) {
            plainText = newString.toString();
            System.out.println("Modified plain text is ---- " + plainText);
        }
    }

    public int[] getDimensions(char letter) {
        int[] dimen = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (key_mat[i][j] == letter) {
                    dimen[0] = i;
                    dimen[1] = j;
                    break;
                }
            }
        }
        return dimen;
    }

    public void encryptMessage() {
        for (int i = 0; i < plainText.length(); i = i + 2) {
            int p1[] = new int[2];
            int p2[] = new int[2];
            p1 = getDimensions(plainText.charAt(i));
            p2 = getDimensions(plainText.charAt(i + 1));
            if (p1[0] == p2[0]) {
                int c1 = (p1[1] + 1) % 5;
                int c2 = (p2[1] + 1) % 5;
                cipherText = cipherText + key_mat[p1[0]][c1] + key_mat[p1[0]][c2];
            } else if (p1[1] == p2[1]) {
                int r1 = (p1[0] + 1) % 5;
                int r2 = (p2[0] + 1) % 5;
                cipherText = cipherText + key_mat[r1][p1[1]] + key_mat[r2][p1[1]];
            } else {
                cipherText = cipherText + key_mat[p1[0]][p2[1]] + key_mat[p2[0]][p1[1]];
            }
        }
    }

    public void decryptMessage() {
        plainText = "";
        for (int i = 0; i < cipherText.length(); i = i + 2) {
            int p1[] = new int[2];
            int p2[] = new int[2];
            p1 = getDimensions(cipherText.charAt(i));
            p2 = getDimensions(cipherText.charAt(i + 1));
            if (p1[0] == p2[0]) {
                int c1 = (p1[1] - 1 + 5) % 5;
                int c2 = (p2[1] - 1 + 5) % 5;
                plainText = plainText + key_mat[p1[0]][c1] + key_mat[p1[0]][c2];
            } else if (p1[1] == p2[1]) {
                int r1 = (p1[0] - 1 + 5) % 5;
                int r2 = (p2[0] - 1 + 5) % 5;
                plainText = plainText + key_mat[r1][p1[1]] + key_mat[r2][p1[1]];
            } else {
                plainText = plainText + key_mat[p1[0]][p2[1]] + key_mat[p2[0]][p1[1]];
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlayFairCipher pfc = new PlayFairCipher();

        System.out.println("\nENCRYPTION");
        System.out.print("\nEnter plain text: ");
        pfc.plainText = sc.next();
        pfc.modifyPlainText();
        System.out.print("\nEnter the key: ");
        pfc.keyword = sc.next();
        pfc.validateKey();
        pfc.printKeyMatrix();
        pfc.encryptMessage();
        System.out.println("\nCipher text is: " + pfc.cipherText);
        
        System.out.println("\nDECRYPTION");
        System.out.print("\nEnter cipher text: ");
        pfc.cipherText = sc.next();
        System.out.print("\nEnter the key: ");
        pfc.keyword = sc.next();
        pfc.validateKey();
        pfc.printKeyMatrix();
        pfc.decryptMessage();
        System.out.println("\nPlain text is: " + pfc.plainText);
        sc.close();
    }
}