package com.driver;

public class CurrentAccount extends BankAccount{
    String tradeLicenseId; //consists of Uppercase English characters only

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception
        super(name, balance, 5000);
        this.tradeLicenseId = tradeLicenseId;
        if (balance < 5000) {
            throw new Exception("Insufficient Balance");
        }
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception
        if (!isNumberValid(tradeLicenseId)) {
            String rearrangeId = arrangeString(tradeLicenseId);
            if (rearrangeId == "") {
                throw new Exception("Valid License can not be generated");
            }
            else {
                this.tradeLicenseId = tradeLicenseId;
            }
        }
    }

    public char getCountChar(int[] count) {
        int max = 0;
        char ch = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] > max) {
                max = count[i];
                ch = (char)((int)'A' + i);
            }
        }
        return ch;
    }

    public String arrangeString(String s) {
        int n = s.length();

        // step 1: store frequency of each char
        int[] freq = new int[26];
        for(int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            freq[ch - 'a']++;
        }

        // step 2: find char with max frequency
        int max = 0, letter = 0;
        for(int i = 0; i < 26; i++) {
            if(freq[i] > max) {
                max = freq[i];
                letter = i;
            }
        }
        if(max > (n + 1) / 2) return "";    // edge case

        // step 3: put max char at the even position
        char[] res = new char[n];
        int index = 0;
        while(freq[letter] > 0) {
            res[index] = (char) (letter + 'a');
            index += 2;
            freq[letter]--;
        }

        // step 4: put rest of the elements
        for(int i = 0; i < 26; i++) {
            while(freq[i] > 0) {
                if(index >= res.length) {
                    index = 1;
                }
                res[index] = (char)(i + 'a');
                index += 2;
                freq[i]--;
            }
        }

        return String.valueOf(res);
    }

    public boolean isNumberValid(String licenseId) {
        for (int i = 0; i < licenseId.length() - 1; i++) {
            if (licenseId.charAt(i) == licenseId.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public String getTradeLicenseId() {
        return tradeLicenseId;
    }
}
