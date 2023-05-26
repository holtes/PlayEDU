package ru.mirea.playedu.view.fragment;

import static ru.mirea.playedu.Constants.LESS;
import static ru.mirea.playedu.Constants.MORE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.FragmentCommunityBinding;
import ru.mirea.playedu.databinding.FragmentProfileBinding;
import ru.mirea.playedu.model.User;
import ru.mirea.playedu.view.adapter.LeaderboardAdapter;
import ru.mirea.playedu.view.dialog.DeleteTaskDialog;
import ru.mirea.playedu.view.dialog.ProfileItemDialog;
import ru.mirea.playedu.viewmodel.CommunityViewModel;

public class CommunityFragment extends Fragment {

    private CommunityViewModel viewModel;
    private FragmentCommunityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        binding.setViewModel(viewModel);

        binding.setKillsHdr(getString(R.string.kills_count));
        binding.setPlayEduCompletedHdr(getString(R.string.play_edu_compl_count));
        binding.setTasksCompletedHdr(getString(R.string.tasks_compl_count));

        viewModel.getUsersCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setUsersList(viewModel.getUsersByCoins());
            }
        });

        binding.showMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getUsersCount().getValue() == LESS) {
                    viewModel.setUsersCount(MORE);
                    binding.showMoreBtn.setImageResource(R.drawable.ic_add);
                    binding.showNoreTxt.setText(R.string.show_more_title);
                }
                else {
                    viewModel.setUsersCount(LESS);
                    binding.showMoreBtn.setImageResource(R.drawable.ic_lessen);
                    binding.showNoreTxt.setText(R.string.show_less_title);
                }
            }
        });




        return binding.getRoot();
    }

    // RecyclerView для таблицы лидеров
    private void setUsersList(ArrayList<User> users) {
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(users, new LeaderboardAdapter.UserItemListener() {
            @Override
            public void onClick(User user) {
                ProfileItemDialog dialog = new ProfileItemDialog(user);
                dialog.show(getParentFragmentManager(), "Profile item dialog");
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.leadersList.setLayoutManager(linearLayoutManager);
        binding.leadersList.setAdapter(leaderboardAdapter);
    }
}