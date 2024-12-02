package mod.remaker.activity.projectwizard.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.sketchware.databinding.WizardStepBinding;

public abstract class WizardStep {
    public abstract String getTitle();
    public abstract String getSubtitle();

    protected IWizardLayout parentLayout;
    protected View stepView;

    public View getStepView() {
        return stepView;
    }

    public void setStepView(View stepView) {
        this.stepView = stepView;
    }

    public final View onCreateView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        WizardStepBinding binding = WizardStepBinding.inflate(inflater);

        getContentView(inflater, binding.container);
        configureStep(binding);

        return binding.getRoot();
    }

    // Make sure that method is called after `setParentLayout(IWizardLayout)`.
    private void configureStep(WizardStepBinding binding) {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (getParentActivity() != null) {
                getParentActivity().onBackPressed();
            }
        });
        binding.title.setText(getTitle());
        binding.message.setText(getSubtitle());
    }

    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    public void setParentStep(WizardStep step) {
        setParentLayout(step.parentLayout);
        stepView = onCreateView(parentLayout.getView().getContext());
    }

    public void setParentLayout(IWizardLayout layout) {
        if (parentLayout != layout) {
            parentLayout = layout;
            if (stepView != null) {
                ViewGroup parent = (ViewGroup) stepView.getParent();
                if (parent != null) {
                    try {
                        parent.removeViewInLayout(stepView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (parentLayout != null && parentLayout.getView().getContext() != stepView.getContext()) {
                    stepView = null;
                }
            }
        }
    }

    public boolean presentStep(WizardStep step) {
        return allowPresentStep() && parentLayout != null && parentLayout.presentStep(step);
    }

    protected boolean allowPresentStep() {
        return true;
    }

    public boolean allowCloseStep() {
        return true;
    }

    public boolean onBackPressed() {
        return true;
    }

    public Activity getParentActivity() {
        if (parentLayout != null) {
            return parentLayout.getParentActivity();
        }
        return null;
    }

    public Context getContext() {
        return getParentActivity();
    }
}
