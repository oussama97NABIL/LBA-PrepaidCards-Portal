package com.LBA.tools.misc;

public class EStatementEntry {

    String account_title;
    String currency;
    String book_date;
    String reference;
    String trx_type;
    String narration;
    String value_date;
    String debit;
    String credit;
    String closing_balance;

    public EStatementEntry() {
        // TODO Auto-generated constructor stub
    }

    public EStatementEntry(String account_title, String currency, String book_date, String reference, String trx_type, String narration, String value_date, String debit, String credit,String closing_balance) {
        this.account_title = account_title;
        this.currency= currency;
        this.book_date= book_date;
        this.reference= reference;
        this.trx_type= trx_type;
        this.narration= narration;
        this.value_date= value_date;
        this.debit= debit;
        this.credit= credit;
        this.closing_balance= closing_balance;


    }

    public String getAccount_title() {
        return account_title;
    }
    public void setAccountTitle(String account_title) {
        this.account_title = account_title;
    }

    public String getcurrency() {
        return currency;
    }
    public void setcurrency(String currency) {
        this.currency = currency;
    }

    public String getbook_date() {
        return book_date;
    }
    public void setbook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getreference() {
        return reference;
    }
    public void setreference(String reference) {
        this.reference = reference;
    }

    public String gettrx_type() {
        return trx_type;
    }
    public void settrx_type(String trx_type) {
        this.trx_type = trx_type;
    }

    public String getnarration() {
        return narration;
    }
    public void setnarration(String narration) {
        this.narration = narration;
    }

    public String getvalue_date() {
        return value_date;
    }
    public void setvalue_date(String value_date) {
        this.value_date = value_date;
    }

    public String getdebit() {
        return debit;
    }
    public void setdebit(String debit) {
        this.debit = debit;
    }

    public String getcredit() {
        return credit;
    }
    public void setcredit(String credit) {
        this.credit = credit;
    }

    public String getclosing_balance() {
        return closing_balance;
    }
    public void setclosing_balance(String closing_balance) {
        this.closing_balance = closing_balance;
    }


}
