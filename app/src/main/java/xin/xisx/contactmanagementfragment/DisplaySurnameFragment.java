package xin.xisx.contactmanagementfragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplaySurnameFragment extends Fragment {

    public static final String ARG_PARAM = "surname";

    protected String surname;

    private View rootView;

    public static DisplaySurnameFragment newInstance(String param) {
        DisplaySurnameFragment fragment = new DisplaySurnameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState) ;
        if (getArguments()!= null)
            surname = getArguments().getString(ARG_PARAM) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_display_surname, container, false);
        return rootView ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvDisplayName = (TextView)rootView.findViewById (R.id.fragment_display_surname_tv) ;

        if ((tvDisplayName != null) && (surname != null)) {
            tvDisplayName.setText(surname);
        }
    }

}