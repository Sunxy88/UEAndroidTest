package xin.xisx.contactmanagementfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {

    private static final String ARG_PARAM = "info";
    protected String info;

    public static DisplayFragment newInstance(String param) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState) ;
        if (getArguments()!= null) {
            info = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvDisplayName = (TextView)view.findViewById (R.id.fragment_display_tv) ;

        if ((tvDisplayName != null) && (info != null)) {
            tvDisplayName.setText(info);
        }
    }
}