package xin.xisx.contactmanagementfragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayGivennameFragment extends Fragment {

    public static final String ARG_PARAM = "givenname";

    protected String givenname;

    private View rootView;

    public static DisplayGivennameFragment newInstance(String param) {
        DisplayGivennameFragment fragment = new DisplayGivennameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState) ;
        if (getArguments()!= null)
            givenname = getArguments().getString(ARG_PARAM) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_display_givenname, container, false);
        return rootView ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvDisplayName = (TextView)rootView.findViewById (R.id.fragment_display_givenname_tv) ;

        if ((tvDisplayName != null) && (givenname != null)) {
            tvDisplayName.setText(givenname);
        }
    }


}