package com.giteam.android.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giteam.android.R
import com.giteam.android.databinding.ActivityMainBinding
import com.giteam.android.ui.adapter.BaseViewItem
import com.giteam.android.ui.adapter.MainActivityItemTypeFactory
import com.giteam.android.ui.adapter.VisitableRecyclerAdapter
import com.giteam.android.ui.base.BaseActivity
import com.giteam.android.ui.viewholders.ErrorStateItem
import com.giteam.android.utils.showSnackBar
import com.giteam.android.utils.viewModelOf
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val usersAdapter: VisitableRecyclerAdapter = VisitableRecyclerAdapter(
        MainActivityItemTypeFactory(),
        ::onItemClicked
    )

    private var isPermittedToFetchAgain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setupViewModel()
        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        menu?.findItem(R.id.menu_mode)?.let {
            it.setOnMenuItemClickListener {
                val mode = if( AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(mode)

                return@setOnMenuItemClickListener true
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun onItemClicked(viewItem: BaseViewItem, view: View) {
        when(viewItem){
            is ErrorStateItem -> {
                mViewModel.loadMoreUsers()
                isPermittedToFetchAgain = false
            }
        }
    }

    private fun setupViews(){
        with(mViewBinding){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                adapter = usersAdapter

                addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if(isPermittedToFetchAgain.not() && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            isPermittedToFetchAgain = true
                        }

                        if (!recyclerView.canScrollVertically(1)
                            && newState == RecyclerView.SCROLL_STATE_IDLE && isPermittedToFetchAgain) {
                            mViewModel.loadMoreUsers()
                            isPermittedToFetchAgain = false
                        }
                    }
                })
            }

            editSearch.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    btnClear.visibility = if(s.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
                    mViewModel.fetchUsersWithName(s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })

            btnClear.setOnClickListener {
                editSearch.setText("")
            }
        }
    }

    private fun setupViewModel(){
        mViewModel.usersLiveData.observe(this, Observer {
            usersAdapter.submitList(it.data)

            when(it){
                is State.Loading -> {

                }
                is State.Success -> {

                }
                is State.Error -> {
                    it.messageResId?.let { id ->
                        showSnackBar(mViewBinding.constraint, getString(id))
                    }
                }
                is State.Idle -> {

                }
            }
        })
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModel() = viewModelOf<MainViewModel>(mViewModelProvider)
}
