package com.cooltechworks.creditcarddesign.pager;

public interface IActionListener {
    void onActionComplete(CreditCardFragment fragment);

    void onEdit(CreditCardFragment fragment, String edit);

}