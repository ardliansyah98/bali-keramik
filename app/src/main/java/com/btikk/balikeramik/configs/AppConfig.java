package com.btikk.balikeramik.configs;

public class AppConfig {
    public String BaseUrl(String url) {
        String urlresult = "http://192.168.1.8/balikeramik/" + url;
        return urlresult;
    }

    public String KategoriUrl() {
        return BaseUrl("mobile_api/Kategori.php");
    }

    public String EventsUrl() {
        return BaseUrl("mobile_api/Events.php");
    }

    public String PelayananUrl() {
        return BaseUrl("mobile_api/Pelayanan.php");
    }

    public String KeramikUrl() {
        return BaseUrl("mobile_api/Keramik.php");
    }

    public String PerajinUrl() {
        return BaseUrl("mobile_api/Perajin.php");
    }

    public String KeramikPerajinUrl() {
        return BaseUrl("mobile_api/KeramikPerajin.php");
    }

    public String GambarKeramikUrl() {
        return BaseUrl("mobile_api/GambarKeramik.php");
    }

    public String AuthUrl() {
        return BaseUrl("mobile_api/Auth.php");
    }

    public String PerajinDetailsUrl() {
        return BaseUrl("mobile_api/PerajinDetails.php");
    }

    public String KeramikAddUrl() {
        return BaseUrl("mobile_api/KeramikAdd.php");
    }

    public String SubKategoriUrl() {
        return BaseUrl("mobile_api/SubKategori.php");
    }

    public String DeleteKeramikUrl() {
        return BaseUrl("mobile_api/KeramikDelete.php");
    }

    public String keramikFiltered() {
        return BaseUrl("mobile_api/KeramikSearch.php");
    }

    public String InsertGambarKeramik() {
        return BaseUrl("mobile_api/GambarKeramikInsert.php");
    }

    public String DeleteGambarKeramik() {
        return BaseUrl("mobile_api/GambarKeramikDelete.php");
    }

    public String ResetPassword() {
        return BaseUrl("mobile_api/ResetPassword.php");
    }

    public String PerajinCRUDUrl() {
        return BaseUrl("mobile_api/PerajinCRUD.php");
    }

    public String LelangUrl() {
        return BaseUrl("mobile_api/Lelang.php");
    }

    public String GambarLelangurl() {
        return BaseUrl("mobile_api/GambarLelang.php");
    }
}
