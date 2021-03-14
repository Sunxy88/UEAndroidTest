package xin.xisx.contactmanagementfragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Parcelable {

    private String surname;
    private String name;
    private String birthday;
    private String cityOfBirthy;
    private String department;
    private Set<String> telNumbers;

    public User() {
    }

    public User(String surname, String name, String birthday, String cityOfBirthy, String department, Set<String> telNumbers) {
        this.surname = surname;
        this.name = name;
        this.birthday = birthday;
        this.cityOfBirthy = cityOfBirthy;
        this.department = department;
        this.telNumbers = telNumbers;
    }

    protected User(Parcel in) {
        this(in.readString(),in.readString(),in.readString(),in.readString(),in.readString(),new HashSet<>());
        List<String> tels = new ArrayList<>();
        in.readStringList(tels);
        this.telNumbers.addAll(tels);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            User user = new User(in);
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(surname);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(cityOfBirthy);
        dest.writeString(department);
        dest.writeStringList(new ArrayList<>(telNumbers));
    }

    @Override
    public String toString() {
        return "User{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cityOfBirthy='" + cityOfBirthy + '\'' +
                ", department='" + department + '\'' +
                ", telNumbers=" + telNumbers +
                '}';
    }
}
