package xin.xisx.contactsimple;

public interface IEdit {

    void onSurnameConfirmed(String surname);

    void onGivennameConfirmed(String givenname);

    void onClickBirthday(String surname, String givenname, String original);

    void onBirthdayConfirmed(String birthday);
}
