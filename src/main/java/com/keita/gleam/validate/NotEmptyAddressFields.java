package com.keita.gleam.validate;

import com.keita.gleam.model.Address;

import java.util.List;

public class NotEmptyAddressFields {

    public static boolean isAddressValid(List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getStreet().trim().isEmpty() ||
                    address.getCity().trim().isEmpty() ||
                    address.getState().trim().isEmpty()) {
                return false;
            }
            if (address.getZip() == 0 || String.valueOf(address.getZip()).length() != 5) {
                return false;
            }
        }
        return true;
    }
}
