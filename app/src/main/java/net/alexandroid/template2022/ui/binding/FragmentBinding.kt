package net.alexandroid.template2022.ui.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentBinding<T : ViewBinding>(private val bindFunction: (View) -> T) :
    ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private var binding: T? = null
    private var isObserving = false

    // Should be called between onCreateView and onDestroyView
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        observeFragmentOnDestroy(thisRef)
        if (binding != null) return binding as T
        return bindFunction(thisRef.requireView()).also { binding = it }
    }

    private fun observeFragmentOnDestroy(fragment: Fragment) {
        if (!isObserving) {
            isObserving = true
            fragment.viewLifecycleOwner.lifecycle.addObserver(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding = null
        isObserving = false
        owner.lifecycle.removeObserver(this)
    }
}