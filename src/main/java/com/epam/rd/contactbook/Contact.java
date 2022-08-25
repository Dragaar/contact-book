package com.epam.rd.contactbook;

import java.util.Arrays;

public class Contact {

    String contactName;
    NameContactInfo nameContactInfo = new NameContactInfo();

    ContactInfo phoneNumber;

    ContactInfo[] result = new ContactInfo[1]; int resultIndex = 0;

    ContactInfo[] emails = new ContactInfo[1];   int emailsIndex = 0;

    //ArrayList<Social> socials = new ArrayList<>();
    ContactInfo[] socials = new ContactInfo[1]; ; int socialsIndex = 0;

    private class NameContactInfo implements ContactInfo{

        @Override
        public String getTitle() {return "Name";}

        private void setName(String name){contactName = name;}
        @Override
        public String getValue() {return contactName;}
    }

    public static class Email implements ContactInfo{
        String email;
        String title="Email";


        @Override
        public String getTitle() {return title;}

        private void setValue(String email){this.email = email;}
        @Override
        public String getValue() {return email;}
    }

    public static class Social implements ContactInfo{
        String title;
        String id;
        private void setValue(String title, String id){this.title = title; this.id = id;}
        @Override
        public String getTitle() {return title;}
        @Override
        public String getValue() {return id;}
    }

    public Contact(String contactName) {
        nameContactInfo.setName(contactName);
        addToArray(nameContactInfo);

    }

    public void rename(String newName) {
        if(ifWrongInput(newName)) return;
        nameContactInfo.setName(newName);
    }

    public Email addEmail(String localPart, String domain) {
        if(ifWrongInput(localPart) || ifWrongInput(domain)) return null;
        if(emails.length >= 3) return null; //обмеження на кількість скриньок

        Email email = new Email();
        email.setValue(localPart+"@"+domain);

        addToEmail(email);

        return email;
    }

    public Email addEpamEmail(String firstname, String lastname) {
        if(ifWrongInput(firstname) || ifWrongInput(lastname)) return null;
        if(emails.length >= 3) return null; //обмеження на кількість скриньок
        Email email = new Email() {
            {setTitle("Epam Email");}
            public void setTitle(String title){this.title=title;}
        };

        //Викликати такий метод іззовні можна тільки одразу
        // new YourClass(){newMethod(){}}.newMethod();
       // unicalEmail.setTitle("Epam Email");
        email.setValue(firstname+"_" + lastname+"@epam.com");

        addToEmail(email);
        return email;

    }

    public ContactInfo addPhoneNumber(int code, String inputNumber) {
        if(ifWrongInput(code) || ifWrongInput(inputNumber)) return null;
        if(phoneNumber!= null) return null;
        phoneNumber = new ContactInfo() {
            String number = "+"+code+" "+inputNumber;
            @Override
            public String getTitle() {return "Tel";}
            @Override
            public String getValue() {return number;}
        };
        addToArray(phoneNumber);
        return phoneNumber;
    }

    public Social addTwitter(String twitterId) {
        if(ifWrongInput(twitterId)) return null;
        if(socials.length >= 5) return null; //обмеження на кількість соц мереж

        Social social = new Social();
        social.setValue("Twitter", twitterId);

        addToSocial(social);
        return social;
    }

    public Social addInstagram(String instagramId) {
        if(ifWrongInput(instagramId)) return null;
        if(socials.length >= 5) return null; //обмеження на кількість соц мереж

        Social social = new Social();
        social.setValue("Instagram", instagramId);

        addToSocial(social);
        return social;
    }

    public Social addSocialMedia(String title, String id) {
        if(ifWrongInput(title) || ifWrongInput(id)) return null;
        if(socials.length >= 5) return null; //обмеження на кількість соц мереж

        Social social = new Social();
        social.setValue(title, id);

        addToSocial(social);
        return social;
    }

    public ContactInfo[] getInfo() {

        ContactInfo[] temp = null;
        if(result[0] != null && emails[0] != null && socials[0] != null) {
                temp = Arrays.copyOf(result, result.length + emails.length + socials.length);

                System.arraycopy(emails, 0, temp, result.length, emails.length);
                System.arraycopy(socials, 0, temp, (result.length + emails.length), socials.length);
        } else if(emails[0] == null && result[0] != null && socials[0] != null)
            {
                temp = Arrays.copyOf(result, result.length + socials.length);

                System.arraycopy(socials, 0, temp, result.length, socials.length);
            }
        else if(emails[0] != null && result[0] != null && socials[0] == null)
            {
                temp = Arrays.copyOf(result, result.length + emails.length);

                System.arraycopy(emails, 0, temp, result.length, emails.length);
            }
        else if(emails[0] == null && result[0] != null && socials[0] == null)
            {
                temp = Arrays.copyOf(result, result.length);
            }
        /*System.out.println("result = " + toString(result));
        System.out.println("emails = " +toString(emails));
        System.out.println("socials = " +toString(socials));*/
       return temp;
    }

    public  void addToArray(ContactInfo contactInfo){
        if(contactInfo == null) return;

            if(resultIndex == 0){ result[0] = contactInfo; resultIndex++;}
            else{
                ContactInfo[] temp = Arrays.copyOf(result, result.length + 1);
                temp[temp.length - 1] = contactInfo;

                result = Arrays.copyOf(temp, temp.length);
            }
    }
    public  void addToEmail(ContactInfo contactInfo){
        if(contactInfo == null) return;

        if(emailsIndex == 0){ emails[0] = contactInfo; emailsIndex++;}
        else{
            ContactInfo[] temp = Arrays.copyOf(emails, emails.length + 1);
            temp[temp.length - 1] = contactInfo;

            emails = Arrays.copyOf(temp, temp.length);
        }
    }
    public  void addToSocial(ContactInfo contactInfo){
        if(contactInfo == null) return;

        if(socialsIndex == 0){ socials[0] = contactInfo; socialsIndex++;}
        else{
            ContactInfo[] temp = Arrays.copyOf(socials, socials.length + 1);
            temp[temp.length - 1] = contactInfo;

            socials = Arrays.copyOf(temp, temp.length);
        }
    }


    public boolean ifWrongInput(int input){
            if (input == 0) return true;
            else return false;
    }
    public boolean ifWrongInput(String input){
        if (input == null || input == "") return true;
        else return false;
    }
    public boolean ifWrongInput(ContactInfo... input){
        for(ContactInfo e : input) {
            if (e == null) return true;
            else return false;
        }
        return false;
    }

    private static String toString(ContactInfo... info) {
        StringBuffer sb = new StringBuffer();
        for(ContactInfo e : info) {
            if(e == null) sb.append("Null");
            else sb.append(" " + e.getTitle() + ": " + e.getValue());
        }
        return sb.toString();
    }


}
