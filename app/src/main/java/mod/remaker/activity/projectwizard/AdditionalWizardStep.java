package mod.remaker.activity.projectwizard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a.a.a.xB;

import mod.remaker.activity.projectwizard.model.WizardStep;

import pro.sketchware.R;
import pro.sketchware.databinding.WizardStepAdditionalBinding;

public class AdditionalWizardStep extends WizardStep {
    @Override
    public String getTitle() {
        return "Configure your project";
    }

    @Override
    public String getSubtitle() {
        return "Choose what you need.";
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        WizardStepAdditionalBinding binding = WizardStepAdditionalBinding.inflate(inflater, container, true);
        return binding.getRoot();
    }
}
