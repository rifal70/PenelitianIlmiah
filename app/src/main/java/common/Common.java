package common;

import model.User;

/**
 * Created by mohamed on 11/21/17.
 */

public class Common {
    public static User currentUser;
    /*this method convert status to String placed , On my way ,shipped*/
    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Sedang di proses";
        else if (status.equals("1"))
            return "Dibatalkan (jarak Pengiriman diluar jangkauan)";
        else if (status.equals("2"))
            return "Dibatalkan (Stok habis)";
        else if (status.equals("3"))
            return "Dibatalkan (Permintaan pembeli)";
        else if (status.equals("4"))
            return "(Biaya Ongkir Rp.3500)";
        else if (status.equals("5"))
            return "(Biaya Ongkir Rp.6000)";
        else if (status.equals("6"))
            return "(Biaya Ongkir Rp.9000)";
        else if (status.equals("7"))
            return "(Sedang di kirim";
        else if (status.equals("8"))
            return "Sudah tiba";
        else if (status.equals("9"))
            return "Transaksi Selesai";
        else
            return "";
    }
}
