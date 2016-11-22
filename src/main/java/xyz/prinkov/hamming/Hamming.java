package xyz.prinkov.hamming;

/**
 * Created by akaroot on 20.11.16.
 */
public class Hamming {
    int lastError;
    int lastCorrect;

    public int getLastCorrect() {
        return lastCorrect;
    }

    public int getLastError() {
        return lastError;
    }

    public String code(String val) {
        StringBuffer buf = new StringBuffer(val);
        buf.insert(0, '*');
        for(int i = 1; i < buf.length(); i = i*2) {
            buf.insert(i, "_");
        }
        int len = 0;
        int count = 0;

        for(int j = 1; j < buf.length(); j *= 2) {
            count = 0;
            for (int i = j; i < buf.length(); i += ((j) * 2)) {
                if (i + j < buf.length())
                    len = i + j;
                else len = buf.length();
                for (int k = i; k < len; k++)
                    if(buf.charAt(k) == '1') count++;

            }
            buf.replace(j, j+1, Integer.toString(count%2));
        }
        buf.delete(0, 1);
        return buf.toString();
    }

    public String findCode(String val) {
        StringBuffer buf = new StringBuffer();
        val = "*" + val;
        int count = 0;
        for(int i = 1; i < val.length(); i++) {
            if(Math.pow(2, count) == i) {
                count++;
                buf.append(val.charAt(i));
            }
        }
        return buf.toString();
    }

    public String deleteCode(String val) {
        StringBuffer buf = new StringBuffer();
        val = "*" + val;
        int count = 0;
        for(int i = 1; i < val.length(); i++) {
            if(Math.pow(2, count) == i) {
                count++;
                continue;
            }
            buf.append(val.charAt(i));
        }
        return buf.toString();
    }

    public boolean check(String val) {

        return (findCode(val).compareTo(findCode(code(deleteCode(val)))) == 0);
    }

    public String correctString(String val) throws Exception {
        if(check(val))
            return val;

        int a = 0;
        a = (Integer.parseInt(new StringBuilder(findCode(code(deleteCode(val)))).reverse().toString(), 2));
        int b = 0;
        b = (Integer.parseInt(new StringBuilder(findCode(val)).reverse().toString(),2));
        int t = a^b;
        t--;
        System.out.println(t);
        if(val.charAt(t) == '1')
            val = val.substring(0, t) + '0' + val.substring(t+1, val.length());
        else
            val = val.substring(0, t) + '1' + val.substring(t+1, val.length());
        lastCorrect = t;
        return val;
    }

    public String randomError (String value) throws Exception {
        int v = 1;

        v = (int)(Math.random() * value.length() - 1);
        char ch = '1';
        if(value.charAt(v) == '1')
            ch = '0';
        lastError = v;
        return value.substring(0, v) +
                ch +
                value.substring(v+1, value.length());
    }
}
